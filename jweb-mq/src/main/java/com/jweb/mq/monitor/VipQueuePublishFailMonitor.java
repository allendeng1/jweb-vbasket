package com.jweb.mq.monitor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.jweb.common.exception.MqException;
import com.jweb.dao.entity.MqMessage;
import com.jweb.mq.message.QueueName;
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
public class VipQueuePublishFailMonitor {

	@Autowired
	private MqMessageService mqMessageService;

	private MqMessage queue = null;
	
	@Scheduled(cron = "0/10 * * * * ?")
	public void run(){
		if(queue == null){
			queue = new MqMessage();
			queue.setQueueName(QueueName.VIP_QUEUE);
			queue.setStatus(0);
			queue.setIsDelete(false);
		}
		try {
			mqMessageService.queuePublishFailMonitor(queue);
		} catch (MqException e) {
			log.error("", e);
		}
	}
}
