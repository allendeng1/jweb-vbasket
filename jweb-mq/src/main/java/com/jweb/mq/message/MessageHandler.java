package com.jweb.mq.message;

import java.util.concurrent.TimeUnit;

import org.redisson.api.RLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jweb.common.exception.MqException;
import com.jweb.common.exception.MyException;
import com.jweb.common.util.DateTimeUtil;
import com.jweb.dao.component.RedisComponent;
import com.jweb.dao.constant.DatabaseConstant.MqMsgExcuteMethod;
import com.jweb.dao.entity.MqMessage;
import com.jweb.mq.service.MqService;

import lombok.extern.slf4j.Slf4j;
/**
 * 
 * @author 邓超
 *
 * 2022/6/4 12:24
 */
@Slf4j
@Component
public abstract class MessageHandler {

	@Autowired
	private MqService mqService;
	@Autowired
	private RedisComponent redisComponent;
	
	
	public void excute(long id, String queueType, MqMsgExcuteMethod method){
		long startTime = DateTimeUtil.nowTime();
		MqMessage message = null;
		try {
			message = mqService.getById(id);
		} catch (MqException e) {
			log.error("", e);
			return;
		}
		if(message == null){
			log.info("message["+id+"] not found");
			return;
		}
		if(queueType.equalsIgnoreCase(QueueType.QUEUE) && message.getStatus().intValue() == 2){
			log.info("message["+id+"] is processed");
			return;
		}
		RLock lock = null;
		String result = "success";
		try {
			String lockName = "mq-"+getType().getName()+"-"+id;
			lock = redisComponent.getFairLock(lockName);
			if(!lock.isLocked()){
				lock.lock(3600, TimeUnit.SECONDS);
				todo(message.getContent());
			}else{
				log.info(lockName+"已被消费：放弃执行");
				result = "skip";
			}
			
			log.info("message["+id+"] excute success");
		} catch (Exception e) {
			log.error("message["+id+"] excute fail", e);
			result = e.getMessage();
		}finally {
			if(lock != null && lock.isHeldByCurrentThread()){
				lock.unlock();
			}
		}
		long endTime = DateTimeUtil.nowTime();
		try {
			mqService.consumeMqMessage(id, method, getExcuteHandler(), startTime, endTime, result);
		} catch (MqException e) {
			log.error("", e);
		}
	}
	
	protected abstract void todo(String content)throws MyException;
	
	protected abstract MessageType getType();
	
	protected abstract String getExcuteHandler();
}
