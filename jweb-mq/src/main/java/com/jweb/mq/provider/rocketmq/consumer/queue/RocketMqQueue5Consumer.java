package com.jweb.mq.provider.rocketmq.consumer.queue;

import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.stereotype.Component;

import com.jweb.mq.message.MessageQueue;
/**
 * 
 * @author 邓超
 *
 * 2022/6/4 12:24
 */
@Component
@ConditionalOnExpression("'${mq.server:}'.equals('rocketmq')")
public class RocketMqQueue5Consumer extends RocketMqQueueConsumer{

	@Override
	public MessageQueue consumerQueue() {
		return MessageQueue.QUEUE_5;
	}
	@Override
	public String instanceName() {
		return consumerQueue().getName();
	}
}
