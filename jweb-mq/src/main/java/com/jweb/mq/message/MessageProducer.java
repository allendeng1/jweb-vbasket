package com.jweb.mq.message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jweb.common.exception.MqException;
import com.jweb.common.util.DateTimeUtil;
import com.jweb.dao.constant.DatabaseConstant.MqMsgChannel;
import com.jweb.dao.constant.DatabaseConstant.MqMsgExcuteMethod;
import com.jweb.dao.entity.MqMessage;
import com.jweb.mq.service.MqService;

import lombok.extern.slf4j.Slf4j;
/**
 * 
 * @author 邓超
 *
 * 2022/6/4 12:24
 */
@Slf4j
@Component
public abstract class MessageProducer {
	
	@Autowired
	private MqService mqService;
	
	
	public boolean publishMessage(MessageQueue queue, MessageType type, String content) throws MqException{

		log.info("Sent type {} message {} to {} queue {}", type.getName(), content, getChannel().getValue(), queue.getName());
		
		if(queue.isIndexDocQueue()) {//搜索引擎索引同步队列，同一索引类型去重
			boolean isExist = mqService.getIndexDocSyncMessageIsExist(content);
			if(isExist) {
				log.info("Index doc sync content[{}] is exist", content);
				return true;
			}
		}
		
		MqMessage message = mqService.createMqMessage(getChannel(), queue, type, content);
		long startTime = DateTimeUtil.nowTime();
		String messageBody = "{\"id\":"+message.getId()+",\"mt\":\""+type.getName()+"\"}";
		String result = "success";
		try {
			sentToMqServer(queue, messageBody);
		} catch (Exception e) {
			log.error("", e);
			result = e.getMessage();
		}
		long endTime = DateTimeUtil.nowTime();
		mqService.publishMqMessage(message.getId(), MqMsgExcuteMethod.AUTO, startTime, endTime, result);
		return result.equals("success");
	}
	public boolean publishMessage(long msgId, MessageQueue queue, MessageType type) throws MqException{

		log.info("Sent type {} message {} to {} queue {}", type.getName(), msgId, getChannel().getValue(), queue.getName());
		
		long startTime = DateTimeUtil.nowTime();
		String messageBody = "{\"id\":"+msgId+",\"mt\":\""+type.getName()+"\"}";
		String result = "success";
		try {
			sentToMqServer(queue, messageBody);
		} catch (Exception e) {
			log.error("", e);
			result = e.getMessage();
		}
		long endTime = DateTimeUtil.nowTime();
		mqService.publishMqMessage(msgId, MqMsgExcuteMethod.MANUAL, startTime, endTime, result);
		return result.equals("success");
	}
	public boolean publishMessage(MessageTopic topic,  String content) throws MqException{
		
		log.info("Sent topic message {} to {} queue {}", content, getChannel().getValue(), topic.getName());
		
		MqMessage message = mqService.createMqMessage(getChannel(), topic, content);
		long startTime = DateTimeUtil.nowTime();
		String messageBody = "{\"id\":"+message.getId()+"}";
		String result = "success";
		try {
			sentToMqServer(topic, messageBody);
		} catch (Exception e) {
			log.error("", e);
			result = e.getMessage();
		}
		long endTime = DateTimeUtil.nowTime();
		mqService.publishMqMessage(message.getId(), MqMsgExcuteMethod.AUTO, startTime, endTime, result);
		return result.equals("success");
	}
	public boolean publishMessage(long msgId, MessageTopic topic) throws MqException{
		
		log.info("Sent topic message {} to {} queue {}", msgId, getChannel().getValue(), topic.getName());
		long startTime = DateTimeUtil.nowTime();
		String messageBody = "{\"id\":"+msgId+"}";
		String result = "success";
		try {
			sentToMqServer(topic, messageBody);
		} catch (Exception e) {
			log.error("", e);
			result = e.getMessage();
		}
		long endTime = DateTimeUtil.nowTime();
		mqService.publishMqMessage(msgId, MqMsgExcuteMethod.MANUAL, startTime, endTime, result);
		return result.equals("success");
	}
	protected abstract void sentToMqServer(MessageQueue queue, String message)throws Exception;
	
	protected abstract void sentToMqServer(MessageTopic topic, String message)throws Exception;
	
	protected abstract MqMsgChannel getChannel();
	

}
