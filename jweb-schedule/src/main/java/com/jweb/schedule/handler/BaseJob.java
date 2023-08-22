package com.jweb.schedule.handler;

import java.util.concurrent.TimeUnit;

import org.redisson.api.RLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jweb.common.exception.MyException;
import com.jweb.common.util.DataUtil;
import com.jweb.common.util.DateTimeUtil;
import com.jweb.dao.component.RedisComponent;
import com.jweb.dao.constant.DatabaseConstant.JobRunLogStatus;
import com.jweb.dao.constant.DatabaseConstant.JobRunResult;
import com.jweb.dao.constant.DatabaseConstant.JobType;
import com.jweb.dao.entity.JobRule;
import com.jweb.dao.entity.JobRunLog;
import com.jweb.schedule.service.JobService;

import lombok.extern.slf4j.Slf4j;
/**
 * 
 * @author 邓超
 *
 * 2022/6/4 12:24
 */
@Slf4j
@Component
public abstract class BaseJob extends DataUtil implements Runnable{
	
	@Autowired
	private JobService jobService;
	@Autowired
	private RedisComponent redisComponent;
	
	private JobRule task;
	
	@Override
	public void run() {
		RLock lock = null;
		long logId = 0 ;
		JobRunResult result = null;
		String remark = null;
		String taskMark = "===>任务["+task.getTaskNo()+"]";
		try {
			log.info(taskMark+"运行开始："+DateTimeUtil.timeToString(DateTimeUtil.nowTime(), DateTimeUtil.Format.YYYYMMDDHHMMSS_1));
	
			lock = redisComponent.getFairLock("job-"+task.getTaskNo());
			if(lock.isLocked()){
				log.info(taskMark+"已在其他实例运行：当前实例放弃执行");
				return;
			}
			lock.lock(3600, TimeUnit.SECONDS);

			//记录开始日志
			logId = createRunStartLog();
			
			//执行具体业务逻辑
			excute(task);
			
			result = JobRunResult.SUCCESS;
			remark = "成功结束";
		} catch (MyException e) {
			result = JobRunResult.FAIL;
			remark = e.getMessage();
		}finally {
			if(isNotNull(lock) && lock.isHeldByCurrentThread()){
				lock.unlock();
			}
		}

		try {
			jobService.doJobOnceRunFinish(task.getId(), logId, JobType.get(task.getType()), result, remark);
			log.info(taskMark+"运行结束："+DateTimeUtil.timeToString(DateTimeUtil.nowTime(), DateTimeUtil.Format.YYYYMMDDHHMMSS_1));
		} catch (MyException e) {
			log.error("", e);
		}
	}

	private long createRunStartLog(){
		JobRunLog runLog = new JobRunLog();
		runLog.setTaskId(task.getId());
		runLog.setTaskNo(task.getTaskNo());
		runLog.setTaskName(task.getTaskName());
		runLog.setRunNo(generateNo("R"));
		runLog.setStatus(JobRunLogStatus.RUNNING.getValue());
		runLog.setRemark("开始执行");
		runLog.setStartDate(DateTimeUtil.nowTime());
		runLog.setCtdate(DateTimeUtil.nowTime());
		runLog.setMddate(DateTimeUtil.nowTime());
		try {
			return jobService.createJobRunLog(runLog);
		} catch (MyException e) {
			log.error("记录任务运行开始日志失败", e);
		}
		return 0;
	}
	private String generateNo(String mark){
		mark = isNull(mark) ? "" : mark;
		return mark+redisComponent.increment("job-run-no", 1);
	}
	public abstract void excute(JobRule task)throws MyException;

	public JobRule getTask() {
		return task;
	}

	public void setTask(JobRule task) {
		this.task = task;
	}
}
