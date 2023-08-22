package com.jweb.mq.provider.activemq;

import javax.jms.ConnectionFactory;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.config.SimpleJmsListenerContainerFactory;
import org.springframework.jms.core.JmsMessagingTemplate;
/**
 * 
 * @author 邓超
 *
 * 2022/6/4 12:24
 */
@Configuration
@PropertySource(value = "classpath:mq/mq-${spring.profiles.active}.properties", ignoreResourceNotFound = true)
@ConditionalOnExpression("'${mq.server:}'.equals('activemq')")
public class ActiveMqConfig {
	
	@Bean
    public JmsMessagingTemplate jmsMessageTemplate(@Value("${activemq.brokerUrl:}") String brokerUrl, 
			   									   @Value("${activemq.username:}") String username, 
			   									   @Value("${activemq.password:}") String password){
		return new JmsMessagingTemplate(connectionFactory(brokerUrl, username, password));
    }
	
	@Bean
    public ConnectionFactory connectionFactory(String brokerUrl, String username, String password){
		return new ActiveMQConnectionFactory(username, password, brokerUrl);
    }
	
	// 在Queue模式中，对消息的监听需要对containerFactory进行配置
	@Bean("queueListener")
    public JmsListenerContainerFactory<?> queueJmsListenerContainerFactory(ConnectionFactory connectionFactory){
		 SimpleJmsListenerContainerFactory factory = new SimpleJmsListenerContainerFactory();
	     factory.setConnectionFactory(connectionFactory);
	     factory.setPubSubDomain(false);
	     return factory;
    }

    //在Topic模式中，对消息的监听需要对containerFactory进行配置
    @Bean("topicListener")
    public JmsListenerContainerFactory<?> topicJmsListenerContainerFactory(ConnectionFactory connectionFactory){
        SimpleJmsListenerContainerFactory factory = new SimpleJmsListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setPubSubDomain(true);
        return factory;
    }
}
