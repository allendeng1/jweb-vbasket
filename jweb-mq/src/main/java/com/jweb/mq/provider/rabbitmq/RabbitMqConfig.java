package com.jweb.mq.provider.rabbitmq;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.jweb.mq.message.MessageQueue;
import com.jweb.mq.message.MessageTopic;
import com.jweb.mq.message.MessageType;
/**
 * 
 * @author 邓超
 *
 * 2022/6/4 12:24
 */
@Configuration
@PropertySource(value = "classpath:mq/mq-${spring.profiles.active}.properties", ignoreResourceNotFound = true)
@ConditionalOnExpression("'${mq.server:}'.equals('rabbitmq')")
public class RabbitMqConfig {
	
	@Value("${rabbitmq.host:}")
	private String host;
	@Value("${rabbitmq.port:}")
	private Integer port;
	@Value("${rabbitmq.virtual_host:/}")
	private String virtualHost;
	@Value("${rabbitmq.username:}")
	private String username;
	@Value("${rabbitmq.password:}")
	private String password;

	private Map<String, String> bindMap = null;

	@Bean
	public ConnectionFactory connectionFactory() {
		CachingConnectionFactory connectionFactory = new CachingConnectionFactory(host, port);
		connectionFactory.setVirtualHost(virtualHost);
		connectionFactory.setUsername(username);
		connectionFactory.setPassword(password);
		return connectionFactory;
	}
	
	@Bean
    public AmqpAdmin amqpAdmin() {
        return new RabbitAdmin(connectionFactory());
    }
 
    @Bean
    public RabbitTemplate rabbitTemplate() {
    	RabbitTemplate template = new RabbitTemplate(connectionFactory());
    	template.containerAckMode(AcknowledgeMode.AUTO);
        return template;
    }

    public String getExchange(String routingKey){
    	return bindMap.get(routingKey);
    }
    
    @PostConstruct
    public void bindingExchangeAndQueue() {
    	bindMap = new HashMap<String, String>();
    	bindMessageQueue();
    	bindMessageTopic();
    }
    private void bindMessageTopic(){
    	for (MessageTopic topic : MessageTopic.values()) {
    		FanoutExchange exchange = ExchangeBuilder.fanoutExchange(topic.getName()).durable(true).build();
    		amqpAdmin().declareExchange(exchange);
    		List<MessageType> types = MessageType.getMessageType(topic.getName());
    		for(MessageType type : types){
    			Queue queue = new Queue(type.getName());
    			amqpAdmin().declareQueue(queue);
    			Binding binding = BindingBuilder.bind(queue).to(exchange);
    			amqpAdmin().declareBinding(binding);
    		}
	   	}
    }
    private void bindMessageQueue(){
    	DirectExchange vipExchange = ExchangeBuilder.directExchange("vip_direct").durable(true).build();
    	DirectExchange orderExchange = ExchangeBuilder.directExchange("order_direct").durable(true).build();
    	DirectExchange generalExchange = ExchangeBuilder.directExchange("general_direct").durable(true).build();
    	
    	amqpAdmin().declareExchange(vipExchange);
    	amqpAdmin().declareExchange(orderExchange);
    	amqpAdmin().declareExchange(generalExchange);
    	
	   	for (MessageQueue mqueue : MessageQueue.values()) {
	   		Queue queue = new Queue(mqueue.getName());
			amqpAdmin().declareQueue(queue);
			Binding binding = null;
	   		if(mqueue.isOrderQueue()){
	   			binding = BindingBuilder.bind(queue).to(orderExchange).with(mqueue.getName());
	   			bindMap.put(mqueue.getName(), orderExchange.getName());
	   		}else if(mqueue.isVipQueue()){
	   			binding = BindingBuilder.bind(queue).to(vipExchange).with(mqueue.getName());
	   			bindMap.put(mqueue.getName(), vipExchange.getName());
	   		}else{
	   			binding = BindingBuilder.bind(queue).to(generalExchange).with(mqueue.getName());
	   			bindMap.put(mqueue.getName(), generalExchange.getName());
	   		}
	   		amqpAdmin().declareBinding(binding);
	   	}
    }
}
