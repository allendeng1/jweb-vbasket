package com.jweb.mq.service;

import java.util.List;

import com.jweb.common.exception.MqException;
import com.jweb.dao.base.PageResult;
import com.jweb.dao.constant.DatabaseConstant.MqMsgChannel;
import com.jweb.dao.constant.DatabaseConstant.MqMsgExcuteMethod;
import com.jweb.dao.dto.MqQueueCountDto;
import com.jweb.dao.entity.MqMessage;
import com.jweb.dao.entity.MqMessageLog;
import com.jweb.mq.message.MessageQueue;
import com.jweb.mq.message.MessageTopic;
import com.jweb.mq.message.MessageType;
/**
 * 
 * @author 邓超
 *
 * 2022/6/4 12:24
 */
public interface MqMessageService {

	MqMessage getById(long id) throws MqException;
	
	MqMessage createMqMessage(MqMsgChannel channel, MessageQueue queue, MessageType type, String content)throws MqException;
	
	MqMessage createMqMessage(MqMsgChannel channel, MessageTopic queue, String content)throws MqException;
	
	void publishMqMessage(long id, MqMsgExcuteMethod method, long startTime, long endTime, String result)throws MqException;
	
	void consumeMqMessage(long id, MqMsgExcuteMethod method, String excuteHandler, long startTime, long endTime, String result)throws MqException;
	
	List<MqQueueCountDto> getMqQueueCount()throws MqException;
	
	void publishRetryMessage(String msgIds)throws MqException;
	
	void deleteMessage(String msgIds)throws MqException;
	
	void queuePublishFailMonitor(MqMessage query)throws MqException;
	
	void autoClearMessage(long clearEndTime)throws MqException;
	
	List<MqMessageLog> getMqMessageLog(long messageId)throws MqException;
	
	PageResult<MqMessage> getMqMessageList(MqMessage query)throws MqException;
	
	boolean getIndexDocSyncMessageIsExist(String content)throws MqException;
}
