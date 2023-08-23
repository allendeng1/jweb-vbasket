package com.jweb.message.provider.email;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
@ConditionalOnExpression("${email.enable:false}")
@PropertySource(ignoreResourceNotFound = false, value = "classpath:msgconfig.properties")
public class EmailConfig {
	
	@Value("${email.host:}")
	private String host;
	@Value("${email.username:}")
	private String username;
	@Value("${email.password:}")
	private String password;
	@Value("${email.port:0}")
	private int port;
	@Value("${email.protocol:}")
	private String protocol;
	
	@Bean
	public JavaMailSenderImpl createEmailSender() {
		JavaMailSenderImpl emailSender = new JavaMailSenderImpl();
		emailSender.setHost(host);
		emailSender.setUsername(username);
		emailSender.setPassword(password);
		emailSender.setPort(port);
		emailSender.setProtocol(protocol);
		emailSender.setDefaultEncoding("UTF-8");
		
		Properties prop = new Properties();
		prop.setProperty("mail.smtp.auth", "true");
		prop.setProperty("mail.smtp.starttls.enable", "true");
		prop.setProperty("mail.smtp.starttls.required", "true");
		prop.setProperty("mail.smtp.ssl.enable", "true");
		prop.setProperty("mail.smtp.socketFactoryClass", "javax.net.ssl.SSLSocketFactory");
		emailSender.setJavaMailProperties(prop);
		return emailSender;
	}
}
