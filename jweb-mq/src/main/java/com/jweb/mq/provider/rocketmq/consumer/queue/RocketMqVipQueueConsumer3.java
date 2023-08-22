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
public class RocketMqVipQueueConsumer3 extends RocketMqQueueConsumer{

	@Override
	public MessageQueue consumerQueue() {
		return MessageQueue.VIP_QUEUE;
	}
	@Override
	public String instanceName() {
		return consumerQueue().getName()+"_3";
	}
}
