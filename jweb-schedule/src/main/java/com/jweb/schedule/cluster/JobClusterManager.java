package com.jweb.schedule.cluster;

import java.util.UUID;

import javax.annotation.PostConstruct;

import org.redisson.api.RTopic;
import org.redisson.api.RedissonClient;
import org.redisson.api.listener.MessageListener;
import org.redisson.codec.SerializationCodec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.jweb.common.exception.JobException;
import com.jweb.common.exception.MyException;
import com.jweb.common.util.DataUtil;
import com.jweb.schedule.handler.ScheduledControlCenter;

import lombok.extern.slf4j.Slf4j;
/**
 * 
 * @author 邓超
 *
 * 2022/6/4 12:24
 */
@Slf4j
@Component
public class JobClusterManager extends DataUtil{
	
	@Autowired
	private RedissonClient redissonClient;
	@Autowired
	private ScheduledControlCenter scheduledControlCenter;
	
	private static final String TOPIC_NAME = "job-topic";
	
	private String nodeId = null;
	
	private RTopic<JobCommand> topic = null;
	
	@Value("${job.cluster.enabled:false}")
	private boolean isClusterEnabled;
	
	public void sentCommand(JobCommand command)throws MyException{
		if(!isClusterEnabled){
			return;
		}
		if(isNull(command)){
			JobException.commandIsNull();
		}
		if(isNull(command.getType())){
			JobException.commandDataError();
		}
		if(isNull(command.getAdminUserId())){
			JobException.commandDataError();
		}
		if(isNull(command.getTaskId()) || command.getTaskId() <= 0){
			JobException.commandDataError();
		}
		command.setNodeId(nodeId);
		topic.publish(command);
	}
	
	@PostConstruct
	public void listener(){
		if(!isClusterEnabled){
			return;
		}
		nodeId = UUID.randomUUID().toString();
		topic = redissonClient.getTopic(TOPIC_NAME, new SerializationCodec());
		topic.addListener(new MessageListener<JobCommand>() {
			@Override
			public void onMessage(String channel, JobCommand command) {
				if(command.getNodeId().equals(nodeId)){
					return;
				}
				log.info("job receive message from channel："+channel+"，message："+JSONObject.toJSONString(command));
				String result = executeCommand(command);
				log.info("job execute command "+result);
			}
		});
	}
	
	private String executeCommand(JobCommand command){
		try {
			switch (command.getType()) {
			case RESTART_JOB:
				scheduledControlCenter.restart(command.getAdminUserId(), command.getTaskId(), false);
				break;
			case STOP_JOB:
				scheduledControlCenter.stop(command.getAdminUserId(), command.getTaskId(), false);
				break;
			default:
				break;
			}
			return "success";
		} catch (MyException e) {
			log.error(e.getMessage(), e);
		}
	
		return "fail";
	}
}
