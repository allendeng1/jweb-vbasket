package com.jweb.mq.provider.activemq;

import javax.jms.Destination;

import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.jms.core.JmsMessagingTemplate;
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
@ConditionalOnExpression("'${mq.server:}'.equals('activemq')")
public class ActiveMqProducer extends MessageProducer{
	
	@Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;

	@Override
	protected void sentToMqServer(MessageQueue queue, String message) {
		Destination destination = null;
		if(queue.isOrderQueue()){
			destination = new ActiveMQQueue(queue.getName()+"?consumer.exclusive=true");
		}else{
			destination = new ActiveMQQueue(queue.getName());
		}
		jmsMessagingTemplate.convertAndSend(destination, message);
	}
	@Override
	protected void sentToMqServer(MessageTopic topic, String message) throws Exception {
		Destination destination = new ActiveMQTopic(topic.getName());
		jmsMessagingTemplate.convertAndSend(destination, message);
	}
	@Override
	protected MqMsgChannel getChannel() {
		return MqMsgChannel.ACTIVEMQ;
	}

	
}
