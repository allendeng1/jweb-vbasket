package com.jweb.dao.component;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import lombok.Data;
/**
 * 
 * @author 邓超
 *
 * 2022/6/4 12:24
 */
@Configuration
@PropertySource(value = {"classpath:redis-${spring.profiles.active}.properties"})
@ConfigurationProperties(prefix = "redis")
@Data
public class RedisProperties {
	
	 private String address;
	 private int database = 0;
	 private String password;
	 private int connectionMinimumIdleSize = 32;
	 private int connectionPoolSize = 64;
	 private int connectTimeout = 10000;
	 private int pingConnectionInterval = 500;
	 private int pingTimeout = 10000;
	 private int timeout = 10000;
	 private int isCluster = 0; //是否开启集群 0 关闭 1开启

}
