package com.jweb.mq.provider.activemq;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import com.jweb.dao.constant.DatabaseConstant.MqMsgExcuteMethod;
import com.jweb.mq.message.MessageQueue;
import com.jweb.mq.message.MessageRouter;
import com.jweb.mq.message.MessageTopic;
import com.jweb.mq.message.MessageType;
import com.jweb.mq.message.QueueName;
import com.jweb.mq.message.TopicName;
/**
 * 
 * @author 邓超
 *
 * 2022/6/4 12:24
 */
@Component
@ConditionalOnExpression("'${mq.server:}'.equals('activemq')")
public class ActiveMqMessageConsumer {
	
	@Autowired
	private MessageRouter messageRouter;

	@JmsListener(destination = QueueName.VIP_QUEUE, containerFactory="queueListener")
	public void vipQueue1(String message){
		messageRouter.consumeRouting(MessageQueue.VIP_QUEUE, message, MqMsgExcuteMethod.AUTO);
	}
	@JmsListener(destination = QueueName.VIP_QUEUE, containerFactory="queueListener")
	public void vipQueue2(String message){
		messageRouter.consumeRouting(MessageQueue.VIP_QUEUE, message, MqMsgExcuteMethod.AUTO);
	}
	@JmsListener(destination = QueueName.VIP_QUEUE, containerFactory="queueListener")
	public void vipQueue3(String message){
		messageRouter.consumeRouting(MessageQueue.VIP_QUEUE, message, MqMsgExcuteMethod.AUTO);
	}
	@JmsListener(destination = QueueName.INDEX_DOC_QUEUE, containerFactory="queueListener")
	public void indexDocSyncQueue1(String message){
		messageRouter.consumeRouting(MessageQueue.INDEX_DOC_QUEUE, message, MqMsgExcuteMethod.AUTO);
	}
	@JmsListener(destination = QueueName.INDEX_DOC_QUEUE, containerFactory="queueListener")
	public void indexDocSyncQueue2(String message){
		messageRouter.consumeRouting(MessageQueue.INDEX_DOC_QUEUE, message, MqMsgExcuteMethod.AUTO);
	}
	@JmsListener(destination = QueueName.INDEX_DOC_QUEUE, containerFactory="queueListener")
	public void indexDocSyncQueue3(String message){
		messageRouter.consumeRouting(MessageQueue.INDEX_DOC_QUEUE, message, MqMsgExcuteMethod.AUTO);
	}
	@JmsListener(destination = QueueName.INDEX_DOC_QUEUE, containerFactory="queueListener")
	public void indexDocSyncQueue4(String message){
		messageRouter.consumeRouting(MessageQueue.INDEX_DOC_QUEUE, message, MqMsgExcuteMethod.AUTO);
	}
	@JmsListener(destination = QueueName.INDEX_DOC_QUEUE, containerFactory="queueListener")
	public void indexDocSyncQueue5(String message){
		messageRouter.consumeRouting(MessageQueue.INDEX_DOC_QUEUE, message, MqMsgExcuteMethod.AUTO);
	}
	@JmsListener(destination = QueueName.ORDER_QUEUE+"?consumer.exclusive=true", containerFactory="queueListener")
	public void orderQueue(String message){
		messageRouter.consumeRouting(MessageQueue.ORDER_QUEUE, message, MqMsgExcuteMethod.AUTO);
	}
	@JmsListener(destination = QueueName.QUEUE_1, containerFactory="queueListener")
	public void queue1(String message){
		messageRouter.consumeRouting(MessageQueue.QUEUE_1, message, MqMsgExcuteMethod.AUTO);
	}
	@JmsListener(destination = QueueName.QUEUE_2, containerFactory="queueListener")
	public void queue2(String message){
		messageRouter.consumeRouting(MessageQueue.QUEUE_2, message, MqMsgExcuteMethod.AUTO);
	}
	@JmsListener(destination = QueueName.QUEUE_3, containerFactory="queueListener")
	public void queue3(String message){
		messageRouter.consumeRouting(MessageQueue.QUEUE_3, message, MqMsgExcuteMethod.AUTO);
	}
	@JmsListener(destination = QueueName.QUEUE_4, containerFactory="queueListener")
	public void queue4(String message){
		messageRouter.consumeRouting(MessageQueue.QUEUE_4, message, MqMsgExcuteMethod.AUTO);
	}
	@JmsListener(destination = QueueName.QUEUE_5, containerFactory="queueListener")
	public void queue5(String message){
		messageRouter.consumeRouting(MessageQueue.QUEUE_5, message, MqMsgExcuteMethod.AUTO);
	}
	@JmsListener(destination = QueueName.QUEUE_6, containerFactory="queueListener")
	public void queue6(String message){
		messageRouter.consumeRouting(MessageQueue.QUEUE_6, message, MqMsgExcuteMethod.AUTO);
	}
	@JmsListener(destination = QueueName.QUEUE_7, containerFactory="queueListener")
	public void queue7(String message){
		messageRouter.consumeRouting(MessageQueue.QUEUE_7, message, MqMsgExcuteMethod.AUTO);
	}
	@JmsListener(destination = QueueName.QUEUE_8, containerFactory="queueListener")
	public void queue8(String message){
		messageRouter.consumeRouting(MessageQueue.QUEUE_8, message, MqMsgExcuteMethod.AUTO);
	}
	@JmsListener(destination = QueueName.QUEUE_9, containerFactory="queueListener")
	public void queue9(String message){
		messageRouter.consumeRouting(MessageQueue.QUEUE_9, message, MqMsgExcuteMethod.AUTO);
	}
	@JmsListener(destination = QueueName.QUEUE_10, containerFactory="queueListener")
	public void queue10(String message){
		messageRouter.consumeRouting(MessageQueue.QUEUE_10, message, MqMsgExcuteMethod.AUTO);
	}
	
	@JmsListener(destination = TopicName.TOPIC_DEFAULT, containerFactory="topicListener")
	public void topicDefault1(String message){
		messageRouter.consumeRouting(MessageTopic.TOPIC_DEFAULT, MessageType.TOPIC_1, message, MqMsgExcuteMethod.AUTO);
	}
	@JmsListener(destination = TopicName.TOPIC_DEFAULT, containerFactory="topicListener")
	public void topicDefault2(String message){
		messageRouter.consumeRouting(MessageTopic.TOPIC_DEFAULT, MessageType.TOPIC_2, message, MqMsgExcuteMethod.AUTO);
	}
}
