package com.jweb.mq.provider.rocketmq.consumer.topic;
/**
 * 
 * @author 邓超
 *
 * 2022/6/4 12:24
 */
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.stereotype.Component;

import com.jweb.mq.message.MessageTopic;
import com.jweb.mq.message.MessageType;

@Component
@ConditionalOnExpression("'${mq.server:}'.equals('rocketmq')")
public class RocketMqTopicDefault1Consumer extends RocketMqTopicConsumer{

	@Override
	public MessageTopic consumerTopic() {
		return MessageTopic.TOPIC_DEFAULT;
	}

	@Override
	public MessageType consumerType() {
		return MessageType.TOPIC_1;
	}

}
