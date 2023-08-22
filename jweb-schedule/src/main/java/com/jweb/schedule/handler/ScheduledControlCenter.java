package com.jweb.schedule.handler;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import com.jweb.common.exception.JobException;
import com.jweb.common.exception.MyException;
import com.jweb.common.util.DataUtil;
import com.jweb.common.util.DateTimeUtil;
import com.jweb.dao.base.PageResult;
import com.jweb.dao.constant.DatabaseConstant;
import com.jweb.dao.constant.DatabaseConstant.JobRunStatus;
import com.jweb.dao.entity.JobRule;
import com.jweb.schedule.service.JobService;

import lombok.extern.slf4j.Slf4j;

/**
 * 任务调度控制中心
 * @author 邓超
 *
 * 2022/6/4 12:24
 */
@Slf4j
@Component
public class ScheduledControlCenter extends DataUtil{

	@Autowired
	private JobService jobService;
	@Autowired
	private ApplicationContext applicationContext;
	
	private ThreadPoolTaskScheduler taskPool;
	
	private Map<String, ScheduledFuture<?>> taskRunMap = new ConcurrentHashMap<String, ScheduledFuture<?>>();

	/**
	 * 初始化注册任务
	 * 
	 * @throws MyException
	 */
	public void initTask() throws MyException {
		log.info("====>初始化注册任务开始");
		
		initTaskThreadPool();
		
		registerFixedTask();
		registerTemporaryTask();
		log.info("====>初始化注册任务结束");
	}
	
	private void registerFixedTask() throws MyException {
		log.info("====>加载固定任务开始");
		JobRule query = new JobRule();
		query.setType(DatabaseConstant.JobType.FIXED.getValue());
		query.setStatus(DatabaseConstant.JobStatus.USEING.getValue());
		PageResult<JobRule> datas = jobService.getJobTaskList(query);
		List<JobRule> tasks = datas.getEntitys();
		if(!isNullOrEmpty(tasks)) {
			for(JobRule task : tasks){
				registerTask(1, task, true);
			}
		}
		log.info("=====加载固定任务成功");
		
	}
	private void registerTemporaryTask() throws MyException {
		log.info("====>加载临时任务开始");
		JobRule query = new JobRule();
		query.setType(DatabaseConstant.JobType.TEMPORARY.getValue());
		query.setStatus(DatabaseConstant.JobStatus.USEING.getValue());
		query.setCustomCondition("(run_status = "+JobRunStatus.UN_RUN.getValue()+" or run_status = "+JobRunStatus.RUNNING.getValue()+")");
		PageResult<JobRule> datas = jobService.getJobTaskList(query);
		List<JobRule> tasks = datas.getEntitys();
		if(!isNullOrEmpty(tasks)) {
			for(JobRule task : tasks){
				String taskCron = task.getTaskCron();
				String[] crons = taskCron.split("\\s+");
		
				int second = parseint(crons[0]);
				int minute = parseint(crons[1]);
				int hour = parseint(crons[2]);
				int day = parseint(crons[3]);
				int month = parseint(crons[4]);
				
				long nowTime = DateTimeUtil.nowTime();
				String timeString = DateTimeUtil.dateInfo(nowTime)[0]+"";
				if(month < 10) {
					timeString += "-0"+month;
				}else {
					timeString += "-"+month;
				}
				if(day < 10) {
					timeString += "-0"+day;
				}else {
					timeString += "-"+day;
				}
				if(hour < 10) {
					timeString += " 0"+hour;
				}else {
					timeString += " "+hour;
				}
				if(minute < 10) {
					timeString += ":0"+minute;
				}else {
					timeString += ":"+minute;
				}
				if(second < 10) {
					timeString += ":0"+second;
				}else {
					timeString += ":"+second;
				}
				long time = DateTimeUtil.stringToTime(timeString, DateTimeUtil.Format.YYYYMMDDHHMMSS_1);
				if(time < nowTime + 10000) {
					LocalDateTime date = DateTimeUtil.timeToLocalDateTime(nowTime + 10000);
					String newTaskCron = date.getSecond()+" "+date.getMinute()+" "+date.getHour() +" "+date.getDayOfMonth()+" "+date.getMonth().getValue()+" *";
					task.setTaskCron(newTaskCron);
				}
				
				registerTask(1, task, true);
			}
		}
		log.info("=====加载临时任务成功");
		
	}

	/**
	 * 停止任务
	 * @param taskNo 任务编号
	 * @throws MyException
	 */
	public void stop(long adminUserId, long taskId, boolean createLog) throws MyException {
		JobRule task = jobService.getJobTaskById(taskId);
		if(isNull(task)){
			JobException.jobNotExist();
		}
		String taskNo = task.getTaskNo();
		if(!taskRunMap.containsKey(taskNo)){
			return;
		}
		 ScheduledFuture<?> scheduledFuture = taskRunMap.get(taskNo);
         //关闭实例
         scheduledFuture.cancel(true);

         //删除关闭的任务实例
         taskRunMap.remove(taskNo);
         
         if(createLog){
        	 jobService.updateJobTaskForRun(adminUserId, taskId, JobRunStatus.UN_RUN);
         }
	}
	/**
	 * 重启任务
	 * @param task
	 * @throws MyException
	 */
	public void restart(long adminUserId, long taskId, boolean createLog) throws MyException {
		JobRule task = jobService.getJobTaskById(taskId);
		if(isNull(task)){
			JobException.jobNotExist();
		}
		String taskNo = task.getTaskNo();
		if(taskRunMap.containsKey(taskNo)){
			stop(adminUserId, task.getId(), createLog);
		}
		registerTask(adminUserId, task, createLog);
	}
	
	public void registerTask(long adminUserId, JobRule task, boolean createLog)throws MyException {
		String taskNo = task.getTaskNo();
		String taskCron = task.getTaskCron();
		String handlerName = task.getHandlerName();
		log.info("====>任务"+taskNo+"注册开始");
		
		String beanName = handlerName.substring(handlerName.lastIndexOf(".")+1, handlerName.length());
		beanName = beanName.substring(0, 1).toLowerCase()+beanName.substring(1, beanName.length());
		boolean isExist = applicationContext.containsBean(beanName);
		if(!isExist){
			log.info("====>任务"+taskNo+"未能找到执行器"+handlerName+"，注册失败");
			return;
		}
		Object obj = applicationContext.getBean(beanName);
		if(!(obj instanceof BaseJob)){
			log.info("====>任务"+taskNo+"找到的执行器"+handlerName+"不合规，注册失败");
			return;
		}
		BaseJob job = (BaseJob) obj;
		job.setTask(task);
		ScheduledFuture<?> scheduledFuture = taskPool.schedule(job, (TriggerContext triggerContext) -> new CronTrigger(taskCron).nextExecutionTime(triggerContext));
		taskRunMap.put(taskNo, scheduledFuture);
		
		if(createLog){
			jobService.updateJobTaskForRun(adminUserId, task.getId(), JobRunStatus.RUNNING);
		}
		log.info("====>任务"+taskNo+"注册结束");
	}

	
	private void initTaskThreadPool() {
		taskPool = new ThreadPoolTaskScheduler();
		taskPool.setPoolSize(20);
		taskPool.setThreadNamePrefix("taskExecutor-");
		// 用来设置线程池关闭的时候等待所有任务都完成再继续销毁其他的Bean，这样这些异步任务的销毁就会先于Redis线程池的销毁。
		taskPool.setWaitForTasksToCompleteOnShutdown(true);
		// 该方法用来设置线程池中任务的等待时间，如果超过这个时候还没有销毁就强制销毁，以确保应用最后能够被关闭，而不是阻塞住。
		taskPool.setAwaitTerminationSeconds(60);
		taskPool.initialize();
	}
}
