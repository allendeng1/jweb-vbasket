package com.jweb.schedule;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@SpringBootApplication
@ComponentScan(value= {"com.jweb.dao.flyway","com.jweb.*"})
public class JwebScheduleServer extends WebMvcConfigurationSupport
{
	public static void main(String[] args) {
		SpringApplication.run(JwebScheduleServer.class, args);
	}
	
	@Bean
	public RequestContextListener requestContextListener(){
	    return new RequestContextListener();
	}
}
