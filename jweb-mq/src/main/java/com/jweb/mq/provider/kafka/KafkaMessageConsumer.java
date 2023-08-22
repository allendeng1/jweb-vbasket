package com.jweb.mq.provider.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.jweb.dao.constant.DatabaseConstant.MqMsgExcuteMethod;
import com.jweb.mq.message.MessageQueue;
import com.jweb.mq.message.MessageRouter;
import com.jweb.mq.message.MessageTopic;
import com.jweb.mq.message.MessageType;
import com.jweb.mq.message.QueueName;
import com.jweb.mq.message.TopicName;
import com.jweb.mq.message.TypeName;
/**
 * 
 * @author 邓超
 *
 * 2022/6/4 12:24
 */
@Component
@ConditionalOnExpression("'${mq.server:}'.equals('kafka')")
public class KafkaMessageConsumer {

	@Autowired
	private MessageRouter messageRouter;
	
	@KafkaListener(topics = QueueName.VIP_QUEUE, concurrency="3", id=QueueName.VIP_QUEUE)
	public void vipQueue(String message){
		messageRouter.consumeRouting(MessageQueue.VIP_QUEUE, message, MqMsgExcuteMethod.AUTO);
	}
	@KafkaListener(topics = QueueName.INDEX_DOC_QUEUE, concurrency="5", id=QueueName.INDEX_DOC_QUEUE)
	public void indexDocSyncQueue(String message){
		messageRouter.consumeRouting(MessageQueue.INDEX_DOC_QUEUE, message, MqMsgExcuteMethod.AUTO);
	}
	@KafkaListener(topics = QueueName.ORDER_QUEUE, concurrency="1", id=QueueName.ORDER_QUEUE)
	public void orderQueue(String message){
		messageRouter.consumeRouting(MessageQueue.ORDER_QUEUE, message, MqMsgExcuteMethod.AUTO);
	}
	@KafkaListener(topics = QueueName.QUEUE_1, concurrency="1", id=QueueName.QUEUE_1)
	public void queue1(String message){
		messageRouter.consumeRouting(MessageQueue.QUEUE_1, message, MqMsgExcuteMethod.AUTO);
	}
	@KafkaListener(topics = QueueName.QUEUE_2, concurrency="1", id=QueueName.QUEUE_2)
	public void queue2(String message){
		messageRouter.consumeRouting(MessageQueue.QUEUE_2, message, MqMsgExcuteMethod.AUTO);
	}
	@KafkaListener(topics = QueueName.QUEUE_3, concurrency="1", id=QueueName.QUEUE_3)
	public void queue3(String message){
		messageRouter.consumeRouting(MessageQueue.QUEUE_3, message, MqMsgExcuteMethod.AUTO);
	}
	@KafkaListener(topics = QueueName.QUEUE_4, concurrency="1", id=QueueName.QUEUE_4)
	public void queue4(String message){
		messageRouter.consumeRouting(MessageQueue.QUEUE_4, message, MqMsgExcuteMethod.AUTO);
	}
	@KafkaListener(topics = QueueName.QUEUE_5, concurrency="1", id=QueueName.QUEUE_5)
	public void queue5(String message){
		messageRouter.consumeRouting(MessageQueue.QUEUE_5, message, MqMsgExcuteMethod.AUTO);
	}
	@KafkaListener(topics = QueueName.QUEUE_6, concurrency="1", id=QueueName.QUEUE_6)
	public void queue6(String message){
		messageRouter.consumeRouting(MessageQueue.QUEUE_6, message, MqMsgExcuteMethod.AUTO);
	}
	@KafkaListener(topics = QueueName.QUEUE_7, concurrency="1", id=QueueName.QUEUE_7)
	public void queue7(String message){
		messageRouter.consumeRouting(MessageQueue.QUEUE_7, message, MqMsgExcuteMethod.AUTO);
	}
	@KafkaListener(topics = QueueName.QUEUE_8, concurrency="1", id=QueueName.QUEUE_8)
	public void queue8(String message){
		messageRouter.consumeRouting(MessageQueue.QUEUE_8, message, MqMsgExcuteMethod.AUTO);
	}
	@KafkaListener(topics = QueueName.QUEUE_9, concurrency="1", id=QueueName.QUEUE_9)
	public void queue9(String message){
		messageRouter.consumeRouting(MessageQueue.QUEUE_9, message, MqMsgExcuteMethod.AUTO);
	}
	@KafkaListener(topics = QueueName.QUEUE_10, concurrency="1", id=QueueName.QUEUE_10)
	public void queue10(String message){
		messageRouter.consumeRouting(MessageQueue.QUEUE_10, message, MqMsgExcuteMethod.AUTO);
	}
	@KafkaListener(topics = TopicName.TOPIC_DEFAULT, concurrency="1", id=TypeName.TOPIC_1)
	public void topicDefault1(String message){
		messageRouter.consumeRouting(MessageTopic.TOPIC_DEFAULT, MessageType.TOPIC_1, message, MqMsgExcuteMethod.AUTO);
	}
	@KafkaListener(topics = TopicName.TOPIC_DEFAULT, concurrency="1", id=TypeName.TOPIC_2)
	public void topicDefault2(String message){
		messageRouter.consumeRouting(MessageTopic.TOPIC_DEFAULT, MessageType.TOPIC_2, message, MqMsgExcuteMethod.AUTO);
	}
}
