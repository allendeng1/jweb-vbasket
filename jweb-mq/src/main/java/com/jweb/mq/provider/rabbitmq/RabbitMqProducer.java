package com.jweb.mq.provider.rabbitmq;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.stereotype.Component;

import com.jweb.dao.constant.DatabaseConstant.MqMsgChannel;
import com.jweb.mq.message.MessageProducer;
import com.jweb.mq.message.MessageQueue;
import com.jweb.mq.message.MessageTopic;
/**
 * 
 * @author 邓超
 *
 * 2022/6/4 12:24
 */
@Component
@ConditionalOnExpression("'${mq.server:}'.equals('rabbitmq')")
public class RabbitMqProducer extends MessageProducer{
	
	@Autowired
	private RabbitTemplate rabbitTemplate;
	@Autowired
	private RabbitMqConfig rabbitMqConfig;

	@Override
	protected void sentToMqServer(MessageQueue queue, String message) throws Exception {
		rabbitTemplate.convertAndSend(rabbitMqConfig.getExchange(queue.getName()), queue.getName(), message);
	}

	@Override
	protected void sentToMqServer(MessageTopic topic, String message) throws Exception {
		rabbitTemplate.convertAndSend(topic.getName(), null, message);
	}

	@Override
	protected MqMsgChannel getChannel() {
		return MqMsgChannel.RABBITMQ;
	}

}
