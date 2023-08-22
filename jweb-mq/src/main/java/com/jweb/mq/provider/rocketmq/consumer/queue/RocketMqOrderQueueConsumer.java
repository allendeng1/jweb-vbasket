package com.jweb.mq.provider.rocketmq.consumer.queue;

import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerOrderly;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import com.jweb.dao.constant.DatabaseConstant.MqMsgExcuteMethod;
import com.jweb.mq.message.MessageQueue;
import com.jweb.mq.message.MessageRouter;
import com.jweb.mq.message.QueueName;
/**
 * 
 * @author 邓超
 *
 * 2022/6/4 12:24
 */
@Component
@PropertySource(value = "classpath:mq/mq-${spring.profiles.active}.properties", ignoreResourceNotFound = true)
@ConditionalOnExpression("'${mq.server:}'.equals('rocketmq')")
public class RocketMqOrderQueueConsumer{

	@Autowired
	private MessageRouter messageRouter;
	
	@Value("${rocketmq.name-server:}")
	private String nameServer;
	
	private DefaultMQPushConsumer consumer;
	
	@PostConstruct
	public void init(){
		
        try {
        	consumer=new DefaultMQPushConsumer(QueueName.ORDER_QUEUE);
            consumer.setNamesrvAddr(nameServer);
            consumer.setInstanceName(QueueName.ORDER_QUEUE);
            
			consumer.subscribe(QueueName.ORDER_QUEUE, "*");
			// 程序第一次启动从消息队列头获取数据
            consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
            //可以修改每次消费消息的数量，默认设置是每次消费一条
            consumer.setConsumeMessageBatchMaxSize(1);
            consumer.registerMessageListener(new MessageListenerOrderly() {	
				@Override
				public ConsumeOrderlyStatus consumeMessage(List<MessageExt> msgs, ConsumeOrderlyContext context) {
					String message = new String(msgs.get(0).getBody());
					messageRouter.consumeRouting(MessageQueue.ORDER_QUEUE, message, MqMsgExcuteMethod.AUTO);
					return ConsumeOrderlyStatus.SUCCESS;
				}
			});
            consumer.start();
		} catch (MQClientException e) {
			throw new RuntimeException(e);
		}
	}
}
