package com.jweb.mq.provider.rabbitmq;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.stereotype.Component;

import com.jweb.dao.constant.DatabaseConstant.MqMsgExcuteMethod;
import com.jweb.mq.message.MessageQueue;
import com.jweb.mq.message.MessageRouter;
import com.jweb.mq.message.MessageTopic;
import com.jweb.mq.message.MessageType;
import com.jweb.mq.message.QueueName;
import com.jweb.mq.message.TypeName;
/**
 * 
 * @author 邓超
 *
 * 2022/6/4 12:24
 */
@Component
@ConditionalOnExpression("'${mq.server:}'.equals('rabbitmq')")
public class RabbitMqMessageConsumer {

	@Autowired
	private MessageRouter messageRouter;
	
	@RabbitListener(queues = QueueName.VIP_QUEUE)
	public void vipQueue1(String message){
		messageRouter.consumeRouting(MessageQueue.VIP_QUEUE, message, MqMsgExcuteMethod.AUTO);
	}
	@RabbitListener(queues = QueueName.VIP_QUEUE)
	public void vipQueue2(String message){
		messageRouter.consumeRouting(MessageQueue.VIP_QUEUE, message, MqMsgExcuteMethod.AUTO);
	}
	@RabbitListener(queues = QueueName.VIP_QUEUE)
	public void vipQueue3(String message){
		messageRouter.consumeRouting(MessageQueue.VIP_QUEUE, message, MqMsgExcuteMethod.AUTO);
	}
	
	@RabbitListener(queues = QueueName.INDEX_DOC_QUEUE)
	public void indexDocSyncQueue1(String message){
		messageRouter.consumeRouting(MessageQueue.INDEX_DOC_QUEUE, message, MqMsgExcuteMethod.AUTO);
	}
	@RabbitListener(queues = QueueName.INDEX_DOC_QUEUE)
	public void indexDocSyncQueue2(String message){
		messageRouter.consumeRouting(MessageQueue.INDEX_DOC_QUEUE, message, MqMsgExcuteMethod.AUTO);
	}
	@RabbitListener(queues = QueueName.INDEX_DOC_QUEUE)
	public void indexDocSyncQueue3(String message){
		messageRouter.consumeRouting(MessageQueue.INDEX_DOC_QUEUE, message, MqMsgExcuteMethod.AUTO);
	}
	@RabbitListener(queues = QueueName.INDEX_DOC_QUEUE)
	public void indexDocSyncQueue4(String message){
		messageRouter.consumeRouting(MessageQueue.INDEX_DOC_QUEUE, message, MqMsgExcuteMethod.AUTO);
	}
	@RabbitListener(queues = QueueName.INDEX_DOC_QUEUE)
	public void indexDocSyncQueue5(String message){
		messageRouter.consumeRouting(MessageQueue.INDEX_DOC_QUEUE, message, MqMsgExcuteMethod.AUTO);
	}
	
	@RabbitListener(queues = QueueName.ORDER_QUEUE)
	public void orderQueue(String message){
		messageRouter.consumeRouting(MessageQueue.ORDER_QUEUE, message, MqMsgExcuteMethod.AUTO);
	}
	@RabbitListener(queues = QueueName.QUEUE_1)
	public void queue1(String message){
		messageRouter.consumeRouting(MessageQueue.QUEUE_1, message, MqMsgExcuteMethod.AUTO);
	}
	@RabbitListener(queues = QueueName.QUEUE_2)
	public void queue2(String message){
		messageRouter.consumeRouting(MessageQueue.QUEUE_2, message, MqMsgExcuteMethod.AUTO);
	}
	@RabbitListener(queues = QueueName.QUEUE_3)
	public void queue3(String message){
		messageRouter.consumeRouting(MessageQueue.QUEUE_3, message, MqMsgExcuteMethod.AUTO);
	}
	@RabbitListener(queues = QueueName.QUEUE_4)
	public void queue4(String message){
		messageRouter.consumeRouting(MessageQueue.QUEUE_4, message, MqMsgExcuteMethod.AUTO);
	}
	@RabbitListener(queues = QueueName.QUEUE_5)
	public void queue5(String message){
		messageRouter.consumeRouting(MessageQueue.QUEUE_5, message, MqMsgExcuteMethod.AUTO);
	}
	@RabbitListener(queues = QueueName.QUEUE_6)
	public void queue6(String message){
		messageRouter.consumeRouting(MessageQueue.QUEUE_6, message, MqMsgExcuteMethod.AUTO);
	}
	@RabbitListener(queues = QueueName.QUEUE_7)
	public void queue7(String message){
		messageRouter.consumeRouting(MessageQueue.QUEUE_7, message, MqMsgExcuteMethod.AUTO);
	}
	@RabbitListener(queues = QueueName.QUEUE_8)
	public void queue8(String message){
		messageRouter.consumeRouting(MessageQueue.QUEUE_8, message, MqMsgExcuteMethod.AUTO);
	}
	@RabbitListener(queues = QueueName.QUEUE_9)
	public void queue9(String message){
		messageRouter.consumeRouting(MessageQueue.QUEUE_9, message, MqMsgExcuteMethod.AUTO);
	}
	@RabbitListener(queues = QueueName.QUEUE_10)
	public void queue10(String message){
		messageRouter.consumeRouting(MessageQueue.QUEUE_10, message, MqMsgExcuteMethod.AUTO);
	}
	@RabbitListener(queues = TypeName.TOPIC_1)
	public void topicDefault1(String message){
		messageRouter.consumeRouting(MessageTopic.TOPIC_DEFAULT, MessageType.TOPIC_1, message, MqMsgExcuteMethod.AUTO);
	}
	@RabbitListener(queues = TypeName.TOPIC_2)
	public void topicDefault2(String message){
		messageRouter.consumeRouting(MessageTopic.TOPIC_DEFAULT, MessageType.TOPIC_2, message, MqMsgExcuteMethod.AUTO);
	}
}
