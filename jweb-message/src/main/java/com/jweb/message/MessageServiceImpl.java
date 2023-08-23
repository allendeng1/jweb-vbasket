package com.jweb.message;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.jweb.common.annotation.param.ParamRule;
import com.jweb.common.annotation.param.ParameterCheck;
import com.jweb.common.exception.MessageException;
import com.jweb.common.exception.MyException;
import com.jweb.common.util.DataUtil;
import com.jweb.common.util.DateTimeUtil;
import com.jweb.common.util.HttpUtil.HttpResult;
import com.jweb.common.util.RpcUtil;
import com.jweb.dao.MessageRecordDao;
import com.jweb.dao.MessageTemplateDao;
import com.jweb.dao.base.PageResult;
import com.jweb.dao.constant.DatabaseConstant;
import com.jweb.dao.entity.MessageRecord;
import com.jweb.dao.entity.MessageTemplate;
import com.jweb.message.base.MessageBody;
import com.jweb.message.base.MessageChannel;
import com.jweb.message.base.MessageProvider;
import com.jweb.message.base.MessageResult;
import com.jweb.message.base.MessageType;
import com.jweb.message.template.TemplateConvert;
import com.jweb.message.template.TemplateParam;
import com.jweb.message.template.TemplateVariable;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MessageServiceImpl extends DataUtil implements MessageService{
	
	@Autowired
	private MessageTemplateDao messageTemplateDao;
	@Autowired
	private MessageRecordDao messageRecordDao;
	@Autowired
	private TemplateConvert templateConvert;
	@Autowired
	private MessageProvider[] messageProvider;

	@Autowired
	private RpcUtil rpcUtil;
	
	private Map<String, MessageProvider> providerPool = null;
	
	private static final long DEFAULT_OPERATOR_ID = 0;

	@ParameterCheck({
		@ParamRule(name="type,param", rule=ParamRule.Rule.NOT_NULL)
	})
	@Override
	public void sentMessage(MessageType type, String recipient, TemplateParam param) 
			throws MyException {
		doSent(DEFAULT_OPERATOR_ID, null, type, recipient, param);
	}
	@ParameterCheck({
		@ParamRule(name="operatorId", rule=ParamRule.Rule.MORE_THAN, value = "0"),
		@ParamRule(name="type,param", rule=ParamRule.Rule.NOT_NULL)
	})
	@Override
	public void sentMessage(long operatorId, MessageType type, String recipient, TemplateParam param)
			throws MyException {
		doSent(operatorId, null, type, recipient, param);
	}
	@ParameterCheck({
		@ParamRule(name="timingTime", rule=ParamRule.Rule.MORE_THAN, value = "0"),
		@ParamRule(name="type,param", rule=ParamRule.Rule.NOT_NULL)
	})
	@Override
	public void sentMessage(MessageType type, String recipient, long timingTime, TemplateParam param)
			throws MyException {
		doSent(DEFAULT_OPERATOR_ID, timingTime, type, recipient, param);
	}
	@ParameterCheck({
		@ParamRule(name="operatorId,timingTime", rule=ParamRule.Rule.MORE_THAN, value = "0"),
		@ParamRule(name="type,param", rule=ParamRule.Rule.NOT_NULL)
	})
	@Override
	public void sentMessage(long operatorId, MessageType type, String recipient, long timingTime, TemplateParam param)
			throws MyException {
		doSent(operatorId, timingTime, type, recipient, param);
	}
	
	private void doSent(long operatorId, Long timingTime, MessageType type, String recipient, TemplateParam param)throws MyException{
		DatabaseConstant.MessageSentWay sentWay = DatabaseConstant.MessageSentWay.REAL_TIME;
		if(isNotNull(timingTime)){
			if(timingTime - DateTimeUtil.nowTime() < 300000){//定时时间须在5分钟之后
				MessageException.invalidTimingTime();
			}
			sentWay = DatabaseConstant.MessageSentWay.TIMING;
		}
		MessageTemplate query = new MessageTemplate();
		query.setCode(type.getCode());
		MessageTemplate template = messageTemplateDao.selectOneByExample(query);
		if(isNull(template)) {
			MessageException.templateNotExist();
		}
		if(isNullOrTrimEmpty(template.getContent())){
			log.info("{} template content is empty，ignore ！！！", type.getCode());
			return;
		}
	
		MessageProvider provider = providerPool.get(template.getSentChannel());
		if(isNull(provider)){
			MessageException.ChannelNotRegister();
		}
		
		MessageBody message = templateConvert.transform(template.getContent(), param);
		message.setRecipient(recipient);
		message.setTemplateId(template.getTemplateCode());
		
		MessageRecord mrecord = createMessageRecord(operatorId, sentWay, timingTime, template, message);
		
		if(sentWay.equals(DatabaseConstant.MessageSentWay.REAL_TIME)){
			message.setMsgId(mrecord.getId()+"");
			MessageResult result = provider.sentMessage(message);
			updateMessageRecordStatusForSent(mrecord.getId(), result);
		}
		if(sentWay.equals(DatabaseConstant.MessageSentWay.TIMING)){
			HttpResult httpResult = rpcUtil.callCreateTemporaryTask("MESSAGE_SENT", DateTimeUtil.timeToString(timingTime, DateTimeUtil.Format.YYYYMMDDHHMMSS_1), mrecord.getTimingTime()+"");
			if(!httpResult.isOk()) {
				MessageException.sentMessageFail();
			}
			JSONObject json = toJsonObject(httpResult.getResponse());
			if(!isEqual(json.getIntValue("bizcode"), 100)) {
				MessageException.sentMessageFail();
			}
		}
	}
	
	@Override
	public void callback(MessageChannel channel, JSONObject jsonParam) throws MyException {
		MessageProvider provider = providerPool.get(channel.name());
		if(isNull(provider)){
			MessageException.ChannelNotRegister();
		}
		List<MessageResult> results = provider.doCallback(jsonParam);
		for(MessageResult result : results) {
			MessageRecord query = new MessageRecord();
			query.setSentChannel(channel.name());
			query.setReference(result.getReference());
			MessageRecord mr = messageRecordDao.selectOneByExample(query);
			if(isNull(mr)) {
				MessageException.recordNotExist();
			}
			updateMessageRecordStatusForCallback(mr.getId(), result);
		}
	}

	private void updateMessageRecordStatusForCallback(long msgId, MessageResult result){
		MessageRecord mr = new MessageRecord();
		mr.setId(msgId);
		mr.setCallbackResult(result.getResultData());
		mr.setCallbackTime(DateTimeUtil.nowTime());
		mr.setStatus(result.getStatus().getValue());
		mr.setMddate(DateTimeUtil.nowTime());
		messageRecordDao.updateById(mr);
	}
	
	private void updateMessageRecordStatusForSent(long msgId, MessageResult result){
		MessageRecord mr = new MessageRecord();
		mr.setId(msgId);
		mr.setSentResult(result.getResultData());
		mr.setSentTime(DateTimeUtil.nowTime());
		mr.setReference(result.getReference());
		mr.setStatus(result.getStatus().getValue());
		mr.setMddate(DateTimeUtil.nowTime());
		messageRecordDao.updateById(mr);
	}
	
	private MessageRecord createMessageRecord(long operatorId, DatabaseConstant.MessageSentWay sentWay, Long timingTime, MessageTemplate template, MessageBody message) throws MyException{
		MessageRecord mr = new MessageRecord();
		mr.setCode(template.getCode());
		mr.setTemplateCode(template.getTemplateCode());
		mr.setContent(message.getContent());
		mr.setCtdate(DateTimeUtil.nowTime());
		mr.setIsDelete(false);
		mr.setMddate(DateTimeUtil.nowTime());
		mr.setReceiver(message.getRecipient() == null ? "" : message.getRecipient());
		mr.setSender(operatorId);
		mr.setSentChannel(template.getSentChannel());
		mr.setSentWay(sentWay.getValue());
		mr.setStatus(DatabaseConstant.MessageSentStatus.WAIT_SENT.getValue());
		mr.setIsRead(false);
		if(isNotNull(timingTime)) {
			mr.setTimingTime(Long.valueOf(DateTimeUtil.timeToString(timingTime, DateTimeUtil.Format.YYYYMMDDHHMMSS_4)));
		}
		messageRecordDao.insert(mr);
		return mr;
	}
	
	@PostConstruct
	private void registerProvider(){
		providerPool = new HashMap<String, MessageProvider>();
		if(isNullOrEmpty(messageProvider)){
			return;
		}
		for(MessageProvider provider : messageProvider){
			providerPool.put(provider.getChannel().name(), provider);
		}
	}
	@Override
	public void sentTimingMessage(long timingTime) throws MyException {
		MessageRecord query = new MessageRecord();
		query.setSentWay(DatabaseConstant.MessageSentWay.TIMING.getValue());
		query.setStatus(DatabaseConstant.MessageSentStatus.WAIT_SENT.getValue());
		query.setTimingTime(timingTime);
		query.setIsDelete(false);
		query.setOrderCondition("id asc");
		query.setLimit(20);

		while(true) {
			List<MessageRecord> records = messageRecordDao.selectByExample(query);
			if(isNullOrEmpty(records)) {
				break;
			}
			for(MessageRecord record : records) {
				MessageProvider provider = providerPool.get(record.getSentChannel());
				if(isNull(provider)){
					continue;
				}
				
				MessageBody message = new MessageBody();
				message.setRecipient(record.getReceiver());
				message.setMsgId(record.getId()+"");
				message.setContent(record.getContent());
				message.setTemplateId(record.getTemplateCode());
				MessageResult result = provider.sentMessage(message);
				updateMessageRecordStatusForSent(record.getId(), result);
			}
			records = null;
		}
	}
	@Override
	public PageResult<MessageTemplate> getMessageTemplateList(MessageTemplate query) throws MyException {
		return messageTemplateDao.selectPageResultByExample(query);
	}
	
	@ParameterCheck({
		@ParamRule(name="operatorId,saasId,id", rule=ParamRule.Rule.MORE_THAN, value = "0")
	})
	@Override
	public void updateMessageTemplate(long operatorId, long id, MessageChannel channel, String templateCode,
			String content) throws MyException {
		
		MessageTemplate template = messageTemplateDao.selectById(id);
		if(isNull(template)) {
			MessageException.templateNotExist();
		}
		
		if(isNull(channel) && isNullOrTrimEmpty(content) && isNullOrTrimEmpty(templateCode)) {
			return;
		}
		if(!isNullOrTrimEmpty(content)) {
			TemplateVariable.checkMessageVariable(content);
		}
		MessageTemplate newTemplate = new MessageTemplate();
		newTemplate.setId(id);
		newTemplate.setContent(content);
		newTemplate.setSentChannel(channel.name());
		newTemplate.setTemplateCode(templateCode);
		newTemplate.setMddate(DateTimeUtil.nowTime());
		messageTemplateDao.updateById(newTemplate);
	}
	@ParameterCheck({
		@ParamRule(name="operatorId,saasId", rule=ParamRule.Rule.MORE_THAN, value = "0"),
		@ParamRule(name="ids", rule=ParamRule.Rule.MATCH_OR_NULL, pattern = "([0-9]+,{1}[0-9]+)+"),
		@ParamRule(name="channel", rule=ParamRule.Rule.NOT_NULL)
	})
	@Override
	public void updateForToggleChannel(long operatorId, String ids, MessageChannel channel)
			throws MyException {
		MessageTemplate query = new MessageTemplate();
		if(!isNullOrTrimEmpty(ids)) {
			query.setCustomCondition("id in ("+ids+")");
		}
		query.setOrderCondition("id asc");
		query.setLimit(15);
		int page = 1;
		MessageTemplate newTemplate = new MessageTemplate();
		newTemplate.setSentChannel(channel.name());
		newTemplate.setMddate(DateTimeUtil.nowTime());
		while(true) {
			query.setOffset(page*15-15);
			List<MessageTemplate> templates = messageTemplateDao.selectByExample(query);
			if(isNullOrEmpty(templates)) {
				break;
			}
			for(MessageTemplate template : templates) {
				newTemplate.setId(template.getId());
				messageTemplateDao.updateById(newTemplate);
			}
			page++;
		}
		
	}
	@Override
	public PageResult<MessageRecord> getMessageRecordList(MessageRecord query) throws MyException {
		return messageRecordDao.selectPageResultByExample(query);
	}

}
