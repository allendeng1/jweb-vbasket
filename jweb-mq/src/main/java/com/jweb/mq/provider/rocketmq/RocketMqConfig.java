package com.jweb.mq.provider.rocketmq;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
/**
 * 
 * @author 邓超
 *
 * 2022/6/4 12:24
 */
@Configuration
@PropertySource(value = "classpath:mq/mq-${spring.profiles.active}.properties", ignoreResourceNotFound = true)
@ConditionalOnExpression("'${mq.server:}'.equals('rocketmq')")
public class RocketMqConfig {
	
	@Bean
    public DefaultMQProducer defaultMQProducer(@Value("${rocketmq.name-server:}") String nameServer, 
			   							     @Value("${rocketmq.producer.group:}") String producerGroup){

		DefaultMQProducer producer = new DefaultMQProducer(producerGroup);
		producer.setNamesrvAddr(nameServer);
	    try {
			producer.start();
		} catch (MQClientException e) {
			throw new RuntimeException(e);
		}
	    return producer;
    }
	
}
