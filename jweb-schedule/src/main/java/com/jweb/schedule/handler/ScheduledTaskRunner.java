package com.jweb.schedule.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;
/**
 * 
 * @author 邓超
 *
 * 2022/6/4 12:24
 */
@Configuration
public class ScheduledTaskRunner implements ApplicationRunner{
	
	@Autowired
	private ScheduledControlCenter scheduledControlCenter;

	@Override
	public void run(ApplicationArguments args) throws Exception {
		scheduledControlCenter.initTask();
	}
}
