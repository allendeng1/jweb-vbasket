package com.jweb.mq.message;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.jweb.common.exception.MqException;
import com.jweb.dao.constant.DatabaseConstant.MqMsgExcuteMethod;

import lombok.extern.slf4j.Slf4j;
/**
 * 
 * @author 邓超
 *
 * 2022/6/4 12:24
 */
@Slf4j
@Component
public class MessageRouter {
	
	@Autowired
	private MessageHandler[] messageHandlers;
	@Autowired
	private MessageProducer messageProducer;
	@Autowired
	private MessageQueueManager messageQueueManager;
	
	private Map<String, List<MessageHandler>> hanlders = new HashMap<String, List<MessageHandler>>();
	
	@PostConstruct
	private void init(){
		for(MessageHandler handler : messageHandlers){
			String name = handler.getType().getName();
			List<MessageHandler> handlers = hanlders.get(name);
			if(handlers == null){
				handlers = new ArrayList<MessageHandler>();
			}
			handlers.add(handler);
			hanlders.put(name, handlers);
		}
	}
	
	public void produceRouting(MessageType bizType, String content, String label) throws MqException{
		MessageQueue queue = null;
		if(label.equalsIgnoreCase(QueueLabel.VIP)){
			queue = MessageQueue.VIP_QUEUE;
		}else if(label.equalsIgnoreCase(QueueLabel.ORDER)){
			queue = MessageQueue.ORDER_QUEUE;
		}else if(label.equalsIgnoreCase(QueueLabel.INDEX_DOC)){
			queue = MessageQueue.INDEX_DOC_QUEUE;
		}else{
			queue = messageQueueManager.assignQueue();
		}
		messageProducer.publishMessage(queue, bizType, content);
	}
	
	public void produceRouting(MessageTopic topic, String content) throws MqException{
		messageProducer.publishMessage(topic, content);
	}
	
	public void produceMessage(long msgId, MessageQueue queue, MessageType bizType) throws MqException{
		messageProducer.publishMessage(msgId, queue, bizType);
	}
	public void produceMessage(long msgId, MessageTopic topic) throws MqException{
		messageProducer.publishMessage(msgId, topic);
	}
	public void consumeRouting(MessageQueue queue, String message, MqMsgExcuteMethod method){
		log.info("MessageRouter received "+queue.getName()+" message："+message);
		try {
			JSONObject json = JSONObject.parseObject(message);
			if(!json.containsKey("id")){
				log.info("message id not found");
			}
			if(!json.containsKey("mt")){
				log.info("message type not found");
			}
			List<MessageHandler> handlers = hanlders.get(json.getString("mt"));
			if(handlers == null || handlers.size() == 0){
				log.info("message handler not found");
			}
			long id = json.getLongValue("id");
			for(MessageHandler handler : handlers){
				handler.excute(id, QueueType.QUEUE, method);
			}
		} catch (Exception e) {
			log.error("MessageRouter routing "+queue.getName()+" message fail", e);
		}
	}
	public void consumeRouting(MessageTopic topic, MessageType type, String message, MqMsgExcuteMethod method){
		log.info("MessageRouter received "+topic.getName()+" message："+message);
		try {
			JSONObject json = JSONObject.parseObject(message);
			if(!json.containsKey("id")){
				log.info("message id not found");
			}
			List<MessageHandler> handlers = hanlders.get(type.getName());
			if(handlers == null || handlers.size() == 0){
				log.info("message handler not found");
			}
			long id = json.getLongValue("id");
			for(MessageHandler handler : handlers){
				handler.excute(id, QueueType.TOPIC, method);
			}
		} catch (Exception e) {
			log.error("MessageRouter routing "+topic.getName()+" message fail", e);
		}
	}
}
