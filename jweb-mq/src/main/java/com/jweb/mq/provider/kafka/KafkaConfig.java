package com.jweb.mq.provider.kafka;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
/**
 * 
 * @author 邓超
 *
 * 2022/6/4 12:24
 */
@Configuration
@PropertySource(value = "classpath:mq/mq-${spring.profiles.active}.properties", ignoreResourceNotFound = true)
@ConditionalOnExpression("'${mq.server:}'.equals('kafka')")
public class KafkaConfig {
	
	@Value("${kafka.bootstrap-servers:}")
	private String servers;
	@Value("${kafka.retries:0}")
	private Integer retries;
	@Value("${kafka.acks:all}")
	private String acks;
	
	@Bean
	public ProducerFactory<String, Object> producerFactory(){
		Map<String, Object> configs = new HashMap<String, Object>();
		configs.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, servers);
		configs.put(ProducerConfig.RETRIES_CONFIG, retries);
		configs.put(ProducerConfig.ACKS_CONFIG, acks);
		//configs.put(ProducerConfig.BATCH_SIZE_CONFIG, 16384);
		//configs.put(ProducerConfig.LINGER_MS_CONFIG, 1);
		//configs.put(ProducerConfig.BUFFER_MEMORY_CONFIG, 33554432);
		configs.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		configs.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		return new DefaultKafkaProducerFactory<String, Object>(configs);
		
	}
	
	@Bean
	public KafkaTemplate<String, Object> kafkaTemplate(){
		return new KafkaTemplate<>(producerFactory());
	}
}
