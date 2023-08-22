package com.jweb.adminweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@SpringBootApplication
@ComponentScan(value= {"com.jweb.dao.flyway","com.jweb.*"}, excludeFilters = {
	@ComponentScan.Filter(type = FilterType.REGEX, pattern = {
            //排除自动生成的controller
			"com.jweb.adminweb.generatecode.*"
	})
})
public class JwebAdminServer extends WebMvcConfigurationSupport
{
	public static void main(String[] args) {
		SpringApplication.run(JwebAdminServer.class, args);
	}
	
	@Bean
	public RequestContextListener requestContextListener(){
	    return new RequestContextListener();
	}
}
