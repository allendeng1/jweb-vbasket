package com.jweb.mq.monitor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.jweb.common.exception.MqException;
import com.jweb.common.util.DateTimeUtil;
import com.jweb.mq.service.MqMessageService;

import lombok.extern.slf4j.Slf4j;
/**
 * 
 * @author 邓超
 *
 * 2022/6/4 12:24
 */
@Slf4j
@Component
@ConditionalOnExpression("${mq.auto-clear.enabled:false}")
public class AutoClearMessageMonitor {
	@Autowired
	private MqMessageService mqMessageService;
	
	@Value("${mq.auto-clear.difftime:7776000000}")
	private long difftime;
	
	@Scheduled(cron = "0 0 3 * * ?")
	public void run(){
		long clearEndTime = DateTimeUtil.nowTime() - difftime;
		try {
			mqMessageService.autoClearMessage(clearEndTime);
		} catch (MqException e) {
			log.error("", e);
		}
	}
}
