package com.jweb.mq.provider.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.kafka.core.KafkaTemplate;
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
@ConditionalOnExpression("'${mq.server:}'.equals('kafka')")
public class KafkaProducer extends MessageProducer{
	
	@Autowired
	private KafkaTemplate<String, Object> kafkaTemplate;

	@Override
	protected void sentToMqServer(MessageQueue queue, String message) throws Exception {
		kafkaTemplate.send(queue.getName(), queue.getName(), message);
	}

	@Override
	protected void sentToMqServer(MessageTopic topic, String message) throws Exception {
		kafkaTemplate.send(topic.getName(), message);
	}

	@Override
	protected MqMsgChannel getChannel() {
		return MqMsgChannel.KAFKA;
	}

}
