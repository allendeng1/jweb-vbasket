package com.jweb.mq.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jweb.common.exception.MqException;
import com.jweb.common.util.DataUtil;
import com.jweb.common.util.DateTimeUtil;
import com.jweb.dao.MqMessageDao;
import com.jweb.dao.MqMessageLogDao;
import com.jweb.dao.base.PageResult;
import com.jweb.dao.constant.DatabaseConstant;
import com.jweb.dao.constant.DatabaseConstant.MqMsgChannel;
import com.jweb.dao.constant.DatabaseConstant.MqMsgExcuteMethod;
import com.jweb.dao.constant.DatabaseConstant.MqMsgType;
import com.jweb.dao.dto.MqQueueCountDto;
import com.jweb.dao.entity.MqMessage;
import com.jweb.dao.entity.MqMessageLog;
import com.jweb.mq.message.MessageQueue;
import com.jweb.mq.message.MessageRouter;
import com.jweb.mq.message.MessageTopic;
import com.jweb.mq.message.MessageType;
/**
 * 
 * @author 邓超
 *
 * 2022/6/4 12:24
 */
@Service
public class MqServiceImpl extends DataUtil implements MqService {
	
	@Autowired
	private MqMessageDao mqMessageDao;
	@Autowired
	private MqMessageLogDao mqMessageLogDao;
	@Autowired
	private MessageRouter messageRouter; 

	@Override
	public MqMessage getById(long id) {
		return mqMessageDao.selectById(id);
	}

	@Transactional
	@Override
	public MqMessage createMqMessage(MqMsgChannel channel, MessageQueue queue, MessageType type, String content) throws MqException{
		long startTime = DateTimeUtil.nowTime();
		MqMessage msg = new MqMessage();
		msg.setChannel(channel.getValue());
		msg.setBizType(type.name());
		msg.setContent(content);
		msg.setCtdate(DateTimeUtil.nowTime());
		msg.setMddate(DateTimeUtil.nowTime());
		msg.setStatus(DatabaseConstant.MqMsgStatus.UN_PUBLISH.getValue());
		msg.setIsDelete(false);
		msg.setQueueName(queue.name());
		mqMessageDao.insert(msg);
		long endTime = DateTimeUtil.nowTime();
		
		createLog(msg.getId(), MqMsgType.CREATED, MqMsgExcuteMethod.AUTO, "", startTime, endTime, "success");
		
		return msg;
	}
	@Transactional
	@Override
	public MqMessage createMqMessage(MqMsgChannel channel, MessageTopic queue, String content) throws MqException{
		long startTime = DateTimeUtil.nowTime();
		MqMessage msg = new MqMessage();
		msg.setChannel(channel.getValue());
		msg.setContent(content);
		msg.setCtdate(DateTimeUtil.nowTime());
		msg.setMddate(DateTimeUtil.nowTime());
		msg.setStatus(DatabaseConstant.MqMsgStatus.UN_PUBLISH.getValue());
		msg.setIsDelete(false);
		msg.setTopicName(queue.name());
		msg.setQueueName(queue.name());
		mqMessageDao.insert(msg);
		long endTime = DateTimeUtil.nowTime();
		
		createLog(msg.getId(), MqMsgType.CREATED, MqMsgExcuteMethod.AUTO, "", startTime, endTime, "success");
		
		return msg;
	}
	@Transactional
	@Override
	public void publishMqMessage(long id, MqMsgExcuteMethod method, long startTime, long endTime, String result) throws MqException{
		MqMessage mqMessage = mqMessageDao.selectById(id);
		if(isNull(mqMessage)) {
			MqException.messageNotExist();
		}
		if(mqMessage.getStatus() == DatabaseConstant.MqMsgStatus.PUBLISHED.getValue()) {
			MqException.messageStatusError();
		}
		if(result.equalsIgnoreCase("success")){
			MqMessage msg = new MqMessage();
			msg.setId(id);
			msg.setStatus(DatabaseConstant.MqMsgStatus.PUBLISHED.getValue());
			msg.setPublishTime(DateTimeUtil.nowTime());
			msg.setMddate(DateTimeUtil.nowTime());
			mqMessageDao.updateById(msg);
		}
		
		createLog(id, MqMsgType.PUBLISHED, method, "", startTime, endTime, result);
	}
	@Transactional
	@Override
	public void consumeMqMessage(long id, MqMsgExcuteMethod method, String excuteHandler, long startTime, long endTime,  String result) throws MqException{
	
		if(result.equalsIgnoreCase("skip")){
			createLog(id, MqMsgType.SKIPED, method, excuteHandler, startTime, endTime, result);
		}else{
			MqMessage mqMessage = mqMessageDao.selectById(id);
			if(isNull(mqMessage)) {
				MqException.messageNotExist();
			}
			MqMessage msg = new MqMessage();
			msg.setId(id);
			msg.setMddate(DateTimeUtil.nowTime());
			if(result.equalsIgnoreCase("success")){
				msg.setStatus(DatabaseConstant.MqMsgStatus.CONSUMED_SUCCESS.getValue());
			}else{
				msg.setStatus(DatabaseConstant.MqMsgStatus.CONSUMED_FAIL.getValue());
			}
			mqMessageDao.updateById(msg);
			
			createLog(id, MqMsgType.CONSUMED, method, excuteHandler, startTime, endTime, result);
		}
	}
	private void createLog(long messageId, MqMsgType type, MqMsgExcuteMethod method, String excuteHandler, Long startTime, Long endTime, String result)throws MqException{
		MqMessageLog mml = new MqMessageLog();
		mml.setMessageId(messageId);
		mml.setExcuteHandler(excuteHandler);
		mml.setExcuteMethod(method.getValue());
		mml.setType(type.getValue());
		mml.setStartTime(startTime);
		mml.setEndTime(endTime);
		mml.setResultDesc(result);
		mml.setCtdate(DateTimeUtil.nowTime());
		mml.setMddate(DateTimeUtil.nowTime());
		mqMessageLogDao.insert(mml);
	}

	@Override
	public List<MqQueueCountDto> getMqQueueCount() throws MqException{
		return mqMessageDao.getMqQueueCount();
	}

	@Transactional
	@Override
	public void publishRetryMessage(String msgIds)throws MqException {
		if(isTrimEmpty(msgIds)){
			return;
		}
		String[] ids = msgIds.split(",");
		for(String id : ids){
			if(isTrimEmpty(id)){
				continue;
			}
			MqMessage message = mqMessageDao.selectById(Long.valueOf(id));
			if(isNull(message)){
				continue;
			}
			if(message.getIsDelete()){
				continue;
			}
			if(message.getStatus() != DatabaseConstant.MqMsgStatus.UN_PUBLISH.getValue() && message.getStatus() != DatabaseConstant.MqMsgStatus.CONSUMED_FAIL.getValue()){
				continue;
			}
			if(isNullOrTrimEmpty(message.getTopicName())){
				messageRouter.produceMessage(message.getId(), MessageQueue.valueOf(message.getQueueName()), MessageType.valueOf(message.getBizType()));
			}else{
				messageRouter.produceMessage(message.getId(), MessageTopic.valueOf(message.getTopicName()));
			}
		}
		
	}

	@Override
	public void deleteMessage(String msgIds) throws MqException{
		if(isTrimEmpty(msgIds)){
			return;
		}
		String[] ids = msgIds.split(",");
		for(String id : ids){
			if(isTrimEmpty(id)){
				continue;
			}
			MqMessage message = mqMessageDao.selectById(Long.valueOf(id));
			if(isNull(message)){
				continue;
			}
			if(message.getIsDelete()){
				continue;
			}
			if(message.getStatus() == DatabaseConstant.MqMsgStatus.CONSUMED_SUCCESS.getValue()){
				continue;
			}
			MqMessage newMessage = new MqMessage();
			newMessage.setId(message.getId());
			newMessage.setIsDelete(true);
			newMessage.setDeleteTime(DateTimeUtil.nowTime());
			newMessage.setMddate(DateTimeUtil.nowTime());
			mqMessageDao.updateById(newMessage);
		}
	}

	@Override
	public void queuePublishFailMonitor(MqMessage query) throws MqException{
		List<MqMessage> messages = mqMessageDao.selectByExample(query);
		if(isNull(messages) || messages.isEmpty()){
			return;
		}
		for(MqMessage message : messages){
			if(isNull(query.getQueueName())){
				continue;
			}
			messageRouter.produceMessage(message.getId(), MessageQueue.valueOf(message.getQueueName()), MessageType.valueOf(message.getBizType()));
		}
	}

	@Override
	public void autoClearMessage(long clearEndTime) throws MqException{
		MqMessage query = new MqMessage();
		int status = DatabaseConstant.MqMsgStatus.CONSUMED_SUCCESS.getValue();
		query.setCustomCondition("publish_time <= "+clearEndTime+" and (status = "+status+" or is_delete= true)");
		query.setOrderCondition("id asc");
		query.setOffset(0);
		query.setLimit(50);
		while(true){
			List<MqMessage> messages = mqMessageDao.selectByExample(query);
			if(isNull(messages) || messages.isEmpty()){
				break;
			}
			for(MqMessage message : messages){
				mqMessageDao.deleteById(message.getId());
				List<MqMessageLog> msglogs = getMqMessageLog(message.getId());
				if(isNull(msglogs) || msglogs.isEmpty()){
					continue;
				}
				for(MqMessageLog msglog : msglogs){
					mqMessageLogDao.deleteById(msglog.getId());
				}
			}
		}
		
	}

	@Override
	public List<MqMessageLog> getMqMessageLog(long messageId) throws MqException{
		MqMessageLog query = new MqMessageLog();
		query.setMessageId(messageId);
		query.setOrderCondition("id asc");
		return mqMessageLogDao.selectByExample(query);
	}

	@Override
	public PageResult<MqMessage> getMqMessageList(MqMessage query) throws MqException{
		return mqMessageDao.selectPageResultByExample(query);
	}

	@Override
	public boolean getIndexDocSyncMessageIsExist(String content) throws MqException {
		MqMessage query = new MqMessage();
		query.setQueueName(MessageQueue.INDEX_DOC_QUEUE.name());
		query.setContent(content);
		query.setCustomCondition("(status = "+DatabaseConstant.MqMsgStatus.UN_PUBLISH.getValue()+" or status = "+DatabaseConstant.MqMsgStatus.PUBLISHED.getValue()+")");
		query.setIsDelete(false);
		int count = mqMessageDao.selectCountByExample(query);
		return count > 0;
	}
	

}
