package com.jweb.mq.provider.rocketmq.consumer.topic;

import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import com.jweb.dao.constant.DatabaseConstant.MqMsgExcuteMethod;
import com.jweb.mq.message.MessageRouter;
import com.jweb.mq.message.MessageTopic;
import com.jweb.mq.message.MessageType;
/**
 * 
 * @author 邓超
 *
 * 2022/6/4 12:24
 */
@Component
@PropertySource(value = "classpath:mq/mq-${spring.profiles.active}.properties", ignoreResourceNotFound = true)
@ConditionalOnExpression("'${mq.server:}'.equals('rocketmq')")
public abstract class RocketMqTopicConsumer {

	@Autowired
	private MessageRouter messageRouter;
	
	@Value("${rocketmq.name-server:}")
	private String nameServer;
	
	private DefaultMQPushConsumer consumer;
	
	public abstract MessageTopic consumerTopic();
	
	public abstract MessageType consumerType();
	
	@PostConstruct
	public void init(){
		
        try {
        	consumer=new DefaultMQPushConsumer(consumerTopic().getName());
            consumer.setNamesrvAddr(nameServer);
            consumer.setInstanceName(consumerType().getName());
            
			consumer.subscribe(consumerTopic().getName(), "*");
			// 程序第一次启动从消息队列头获取数据
            consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
            //可以修改每次消费消息的数量，默认设置是每次消费一条
            consumer.setConsumeMessageBatchMaxSize(1);
            consumer.setMessageModel(MessageModel.BROADCASTING);
            consumer.registerMessageListener(new MessageListenerConcurrently() {
				@Override
				public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
					String message = new String(msgs.get(0).getBody());
					messageRouter.consumeRouting(consumerTopic(), consumerType(), message, MqMsgExcuteMethod.AUTO);
					return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
				}
			});
            consumer.start();
		} catch (MQClientException e) {
			throw new RuntimeException(e);
		}
	}
}
