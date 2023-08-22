package com.jweb.mq.provider.rocketmq;


import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;
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
@ConditionalOnExpression("'${mq.server:}'.equals('rocketmq')")
public class RocketMqProducer extends MessageProducer{
	
	@Autowired
    private DefaultMQProducer defaultMQProducer;

	@Override
	protected void sentToMqServer(MessageQueue queue, String message) {
		
		Message msg = new Message(queue.getName(), message.getBytes());
		try {
			 SendResult sendResult = defaultMQProducer.send(msg);
			 if(!sendResult.getSendStatus().equals(SendStatus.SEND_OK)){
				 throw new RuntimeException(sendResult.getSendStatus().name());
			 }
		} catch (MQClientException e) {
			throw new RuntimeException(e);
		} catch (RemotingException e) {
			throw new RuntimeException(e);
		} catch (MQBrokerException e) {
			throw new RuntimeException(e);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	protected void sentToMqServer(MessageTopic topic, String message) throws Exception {
		Message msg = new Message(topic.getName(), message.getBytes());
		try {
			 SendResult sendResult = defaultMQProducer.send(msg);
			 if(!sendResult.getSendStatus().equals(SendStatus.SEND_OK)){
				 throw new RuntimeException(sendResult.getSendStatus().name());
			 }
		} catch (MQClientException e) {
			throw new RuntimeException(e);
		} catch (RemotingException e) {
			throw new RuntimeException(e);
		} catch (MQBrokerException e) {
			throw new RuntimeException(e);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	protected MqMsgChannel getChannel() {
		return MqMsgChannel.ROCKETMQ;
	}
}
