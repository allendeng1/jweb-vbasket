package com.jweb.dao.transaction;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
/**
 * 
 * @author 邓超
 *
 * 2022/6/4 12:24
 */
@Configuration
@EnableTransactionManagement
public class TransactionConfiguration {

	@Bean
	public PlatformTransactionManager txManager(final DataSource dataSource){
		return new DataSourceTransactionManager(dataSource);
		
	}
}
