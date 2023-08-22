package com.jweb.message.base;

import java.util.List;

import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.jweb.common.exception.MessageException;
import com.jweb.common.exception.MyException;
import com.jweb.common.util.DataUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * 消息提供者抽象类
 * @author 邓超
 *
 * 2022/08/26 下午2:58:13
 */
@Component
@Slf4j
public abstract class MessageProvider extends DataUtil{
	
	public boolean isAvailable = true;
	
	public MessageResult sentMessage(MessageBody body) throws MyException{
		log.info("{} sent message[{}] to {}", getChannel(), body.getContent(), body.getRecipient());
		if(isCheckRecipient()) {
			if(isNullOrTrimEmpty(body.getRecipient())) {
				MessageException.missingRecipient();
			}
		}
		String newContent = formatMessage(body.getContent());
		newContent = isNullOrTrimEmpty(newContent) ? body.getContent() : newContent;
		body.setContent(newContent);
		MessageResult result = sent(body);
		warning(result);
		log.info("{} sent message success {} {}", getChannel(), result.getStatus().name(), result.getResultData());
		return result;
	}
	
	public List<MessageResult> doCallback(JSONObject jsonParam) throws MyException{
		log.info("{} accept callback：{}", getChannel(), jsonParam);
		List<MessageResult> results = callback(jsonParam);
		log.info("{} accept callback success", getChannel());
		return results;
	}
	
	/**
	 * 发送渠道不可用警告
	 * @param result
	 */
	protected void warning(MessageResult result) {
		if(isAvailable || !isWarning()) {
			return;
		}
		String warning = "消息渠道["+getChannel()+"]不可用，原因："+result.getErrMsg();
		//todo
	}
	
	protected void setAvailable(boolean t) {
		if(this.isAvailable == t) {
			return;
		}
		this.isAvailable = t;
	}
	
	protected abstract MessageResult sent(MessageBody body);
	
	protected abstract List<MessageResult> callback(JSONObject jsonParam);
	
	/**
	 * 渠道不可用时是否发送警告
	 * @return
	 */
	protected abstract boolean isWarning();
	
	/**
	 * 是否校验发送消息要明确指定接收者
	 * @return
	 */
	protected abstract boolean isCheckRecipient();
	
	public abstract MessageChannel getChannel();
	/**
	 * 对消息内容格式化处理
	 * @param message
	 * @return
	 */
	protected abstract String formatMessage(String message);
}
