package com.jweb.schedule.service;


import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jweb.common.annotation.param.ParamRule;
import com.jweb.common.annotation.param.ParameterCheck;
import com.jweb.common.exception.JobException;
import com.jweb.common.exception.MyException;
import com.jweb.common.util.DataUtil;
import com.jweb.common.util.DateTimeUtil;
import com.jweb.dao.JobRuleDao;
import com.jweb.dao.JobRunLogDao;
import com.jweb.dao.JobUpdateLogDao;
import com.jweb.dao.base.PageResult;
import com.jweb.dao.constant.DatabaseConstant;
import com.jweb.dao.constant.DatabaseConstant.JobRunLogStatus;
import com.jweb.dao.constant.DatabaseConstant.JobRunResult;
import com.jweb.dao.constant.DatabaseConstant.JobRunStatus;
import com.jweb.dao.constant.DatabaseConstant.JobStatus;
import com.jweb.dao.constant.DatabaseConstant.JobType;
import com.jweb.dao.entity.JobRule;
import com.jweb.dao.entity.JobRunLog;
import com.jweb.dao.entity.JobUpdateLog;
import com.jweb.schedule.cluster.CommandType;
import com.jweb.schedule.cluster.JobClusterManager;
import com.jweb.schedule.cluster.JobCommand;
import com.jweb.schedule.handler.BaseJob;
import com.jweb.schedule.handler.ScheduledControlCenter;
import com.jweb.schedule.handler.executor.temporary.TemporaryJob;
import com.jweb.schedule.handler.executor.temporary.TemporaryJobType;

import lombok.extern.slf4j.Slf4j;
/**
 * 
 * @author 邓超
 *
 * 2022/6/4 12:24
 */
@Service
@Slf4j
public class JobServiceImpl extends DataUtil implements JobService {
	
	@Autowired
	private JobRuleDao jobRuleDao;
	@Autowired
	private JobUpdateLogDao jobUpdateLogDao;
	@Autowired
	private JobRunLogDao jobRunLogDao;
	@Autowired
	private TemporaryJob[] temporaryJob;
	@Autowired
	private ScheduledControlCenter scheduledControlCenter;
	@Autowired
	private JobClusterManager jobClusterManager;
	@Autowired
	private ApplicationContext applicationContext;
	
	private Map<String, String> temporaryJobHandler = null;

	@ParameterCheck({
		@ParamRule(name="adminUserId", rule=ParamRule.Rule.MORE_THAN, value="0"),
		@ParamRule(name="taskName", rule=ParamRule.Rule.NOT_NULL_AND_EMPTY, maxLength=30),
		@ParamRule(name="taskCron", rule=ParamRule.Rule.MATCH, pattern="[0-9\\*/\\?\\s-]+", maxLength=50),
		@ParamRule(name="handlerName", rule=ParamRule.Rule.MATCH, pattern="((?i)[a-z]+\\.[a-z]+)+", maxLength=100),
		@ParamRule(name="remark", rule=ParamRule.Rule.MAX_LENGTH_OR_NULL, maxLength=100)
	})
	@Override
	@Transactional
	public void createFixedJobTask(long adminUserId, String taskName, String taskCron, String handlerName, String remark)
			throws MyException {
		createJobTask(adminUserId, JobType.FIXED, taskName, taskCron, handlerName, remark, null);
	}
	
	@ParameterCheck({
		@ParamRule(name="type", rule=ParamRule.Rule.NOT_NULL),
		@ParamRule(name="timingTime,content", rule=ParamRule.Rule.NOT_NULL_AND_EMPTY),
	})
	@Override
	@Transactional
	public void createTemporaryJobTask(TemporaryJobType type, String timingTime, String content) throws MyException {
		
		String handlerName = temporaryJobHandler.get(type.name());
		if(isNullOrTrimEmpty(handlerName)) {
			JobException.handlerNotExist();
		}
		long time = DateTimeUtil.stringToTime(timingTime, DateTimeUtil.Format.YYYYMMDDHHMMSS_1);
		if(time < DateTimeUtil.nowTime() + 300000) {
			JobException.invalidTimingTime();
		}
		LocalDateTime date = DateTimeUtil.timeToLocalDateTime(time);
		String taskCron = date.getSecond()+" "+date.getMinute()+" "+date.getHour() +" "+date.getDayOfMonth()+" "+date.getMonth().getValue()+" *";
		long taskId = createJobTask(0L, JobType.TEMPORARY, type.name(), taskCron, handlerName, null, content);
		
		scheduledControlCenter.restart(0L, taskId, true);
			
	}
	
	private long createJobTask(long adminUserId, JobType type, String taskName, String taskCron, String handlerName, String remark, String content) throws MyException {
		JobRule job = new JobRule();
		job.setType(type.getValue());
		job.setStatus(DatabaseConstant.JobStatus.USEING.getValue());

		if(type.equals(JobType.FIXED)) {
			job.setTaskName(taskName);
			int count = jobRuleDao.selectCountByExample(job);
			if(count > 0){
				JobException.repeatTaskName();
			}
			job.setTaskName(null);
			job.setHandlerName(handlerName);
			count = jobRuleDao.selectCountByExample(job);
			if(count > 0){
				JobException.repeatHandler();
			}
		}
	
		checkHandlerExistOrNot(handlerName);
		
		job.setTaskNo(generateNo("T"));
		job.setTaskName(taskName);
		job.setTaskCron(taskCron);
		job.setHandlerName(handlerName);
		job.setRunStatus(DatabaseConstant.JobRunStatus.UN_RUN.getValue());
		job.setRemark(remark);
		job.setTaskContent(content);
		job.setCtdate(DateTimeUtil.nowTime());
		job.setMddate(DateTimeUtil.nowTime());
		jobRuleDao.insert(job);
		
		createRuleUpdateLog(adminUserId, null, job);
		
		return job.getId();
	}

	@ParameterCheck({
		@ParamRule(name="adminUserId,taskId", rule=ParamRule.Rule.MORE_THAN, value="0"),
		@ParamRule(name="taskCron", rule=ParamRule.Rule.MATCH_OR_NULL, pattern="[0-9\\*/\\?\\s-]+", maxLength=50),
		@ParamRule(name="handlerName", rule=ParamRule.Rule.MATCH_OR_NULL, pattern="((?i)[a-z]+\\.[a-z]+)+", maxLength=100),
		@ParamRule(name="remark", rule=ParamRule.Rule.MAX_LENGTH_OR_NULL, maxLength=100)
	})
	@Override
	@Transactional
	public void updateJobTask(long adminUserId, long taskId, String taskCron, String handlerName, String remark)
			throws MyException {
		JobRule oldRule = jobRuleDao.selectById(taskId);
		if(isNull(oldRule)){
			JobException.jobNotExist();
		}
		if(isNull(taskCron) && isNull(handlerName) && isNull(remark)){
			return;
		}
		if(isEqual(oldRule.getType(), JobType.FIXED.getValue())){
			updateFixedJobTask(adminUserId, taskId, taskCron, handlerName, remark, oldRule);
		}
		
		if(isEqual(oldRule.getType(), JobType.TEMPORARY.getValue())){
			updateTemporaryJobTask(adminUserId, taskId, taskCron, oldRule);
		}
	}
	
	private void updateTemporaryJobTask(long adminUserId, long taskId, String taskCron, JobRule oldRule) throws MyException {
		if(isEqual(oldRule.getRunStatus(), JobRunStatus.EXCUTED.getValue())) {
			JobException.jobRunStatusError();
		}
		if(isNullOrTrimEmpty(taskCron)) {
			return;
		}
		if(isEqual(taskCron, oldRule.getTaskCron())) {
			return;
		}
		
		String[] crons = taskCron.split("\\s+");
		if(crons.length != 6) {
			JobException.invalidTimingTime();
		}
		try {
			int second = parseint(crons[0]);
			int minute = parseint(crons[1]);
			int hour = parseint(crons[2]);
			int day = parseint(crons[3]);
			int month = parseint(crons[4]);
			if(second < 0 || second > 59) {
				JobException.invalidTimingTime();
			}
			if(minute < 0 || minute > 59) {
				JobException.invalidTimingTime();
			}
			if(hour < 0 || hour > 23) {
				JobException.invalidTimingTime();
			}
			if(day < 0 || day > 31) {
				JobException.invalidTimingTime();
			}
			if(month < 0 || month > 12) {
				JobException.invalidTimingTime();
			}
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
			if(time < nowTime + 180000) {
				JobException.invalidTimingTime();
			}
		} catch (MyException e) {
			log.error("", e);
			JobException.invalidTimingTime();
		}
		
		JobRule newRule = new JobRule();
		newRule.setId(taskId);
		newRule.setTaskCron(taskCron);
		newRule.setMddate(DateTimeUtil.nowTime());
		jobRuleDao.insert(newRule);
		
		createRuleUpdateLog(adminUserId, oldRule, newRule);
		
		restartJobForUpdateJobTask(adminUserId, taskCron, null, oldRule);
	}
	
	private void updateFixedJobTask(long adminUserId, long taskId, String taskCron, String handlerName, String remark, JobRule oldRule) throws MyException {
		if(isNotNull(handlerName)){
			JobRule query = new JobRule();
			query.setType(oldRule.getType());
			query.setHandlerName(handlerName);
			query.setStatus(DatabaseConstant.JobStatus.USEING.getValue());
			JobRule job = jobRuleDao.selectOneByExample(query);
			if(isNotNull(job) && !job.getId().equals(oldRule.getId())){
				JobException.repeatHandler();
			}
			
			checkHandlerExistOrNot(handlerName);
		}
		
		JobRule newRule = new JobRule();
		newRule.setId(taskId);
		newRule.setTaskCron(taskCron);
		newRule.setHandlerName(handlerName);
		newRule.setRemark(remark);
		newRule.setMddate(DateTimeUtil.nowTime());
		jobRuleDao.insert(newRule);
		
		createRuleUpdateLog(adminUserId, oldRule, newRule);
		
		restartJobForUpdateJobTask(adminUserId, taskCron, handlerName, oldRule);
	
	}
	
	private void restartJobForUpdateJobTask(long adminUserId, String taskCron, String handlerName, JobRule oldRule) throws MyException {
		if(!isEqual(oldRule.getRunStatus(), JobRunStatus.RUNNING.getValue())) {
			return;
		}
		
		boolean isRestart = false;
		if(!isNullOrTrimEmpty(taskCron) && !isEqual(taskCron, oldRule.getTaskCron())) {
			isRestart = true;
		}
		if(!isNullOrTrimEmpty(handlerName) && !isEqual(handlerName, oldRule.getHandlerName())) {
			isRestart = true;
		}
		if(isRestart) {
			scheduledControlCenter.restart(adminUserId, oldRule.getId(), true);
			
			JobCommand command = new JobCommand();
			command.setAdminUserId(adminUserId);
			command.setTaskId(oldRule.getId());
			command.setType(CommandType.RESTART_JOB);
			jobClusterManager.sentCommand(command);
		}
	}
	
	@ParameterCheck({
		@ParamRule(name="adminUserId,taskId", rule=ParamRule.Rule.MORE_THAN, value="0")
	})
	@Override
	@Transactional
	public void updateJobTaskForUse(long adminUserId, long taskId, JobStatus jobStatus) throws MyException {
		JobRule oldRule = jobRuleDao.selectById(taskId);
		if(isNull(oldRule)){
			JobException.jobNotExist();
		}
		if(isEqual(oldRule.getStatus(), jobStatus.getValue())){
			return;
		}
		JobRule newRule = new JobRule();
		newRule.setId(taskId);
		newRule.setStatus(jobStatus.getValue());
		newRule.setMddate(DateTimeUtil.nowTime());
		jobRuleDao.insert(newRule);
		
		createRuleUpdateLog(adminUserId, oldRule, newRule);
		
	}
	@ParameterCheck({
		@ParamRule(name="taskId", rule=ParamRule.Rule.MORE_THAN, value="0")
	})
	@Override
	@Transactional
	public void updateJobTaskForRun(long adminUserId, long taskId, JobRunStatus jobRunStatus) throws MyException {
		JobRule oldRule = jobRuleDao.selectById(taskId);
		if(isNull(oldRule)){
			JobException.jobNotExist();
		}
		
		if(isEqual(oldRule.getRunStatus(), jobRunStatus.getValue())){
			return;
		}
		JobRule newRule = new JobRule();
		newRule.setId(taskId);
		newRule.setRunStatus(jobRunStatus.getValue());
		newRule.setMddate(DateTimeUtil.nowTime());
		jobRuleDao.insert(newRule);
		
		createRuleUpdateLog(adminUserId, oldRule, newRule);
		
	}
	@Override
	public long createJobRunLog(JobRunLog log) throws MyException {
		jobRunLogDao.insert(log);
		return log.getId();
	}
	@ParameterCheck({
		@ParamRule(name="log", fieldName = "id", rule=ParamRule.Rule.MORE_THAN, value="0")
	})
	@Override
	public void updateJobRunLog(JobRunLog log) throws MyException {
		jobRunLogDao.updateById(log);
	}
	@Override
	public JobRule getJobTaskById(long taskId) throws MyException {
		return jobRuleDao.selectById(taskId);
	}
	
	@ParameterCheck({
		@ParamRule(name="taskId,logId", rule=ParamRule.Rule.MORE_THAN, value="0"),
		@ParamRule(name="type,result", rule=ParamRule.Rule.NOT_NULL),
	})
	@Override
	public void doJobOnceRunFinish(long taskId, long logId, JobType type, JobRunResult result, String remark) throws MyException {
		if(type.equals(JobType.TEMPORARY)) {
			JobRule jobRule = new JobRule();
			jobRule.setId(taskId);
			jobRule.setRunStatus(JobRunStatus.EXCUTED.getValue());
			jobRule.setMddate(DateTimeUtil.nowTime());
			jobRuleDao.updateById(jobRule);
			
			scheduledControlCenter.stop(0, taskId, false);
		}
		JobRunLog runLog = new JobRunLog();
		runLog.setId(logId);
		runLog.setStatus(JobRunLogStatus.FINISH.getValue());
		runLog.setRunResult(result.getValue());
		runLog.setRemark(remark);
		runLog.setEndDate(DateTimeUtil.nowTime());
		runLog.setMddate(DateTimeUtil.nowTime());
		updateJobRunLog(runLog);
		
	}
	private void createRuleUpdateLog(long adminUserId, JobRule oldRule, JobRule newRule)throws MyException{
		JobUpdateLog log = new JobUpdateLog();
		log.setAdminUserId(adminUserId);
		log.setCtdate(DateTimeUtil.nowTime());
		log.setMddate(DateTimeUtil.nowTime());
		if(isNull(oldRule)){
			log.setTaskId(newRule.getId());
			log.setTaskNo(newRule.getTaskNo());
			log.setTaskName(newRule.getTaskName());
			log.setType(DatabaseConstant.JobUpdateType.CREATE.getValue());
			log.setRemark("创建任务");
			jobUpdateLogDao.insert(log);
		}else{
			log.setTaskId(oldRule.getId());
			log.setTaskNo(oldRule.getTaskNo());
			log.setTaskName(oldRule.getTaskName());
			if(isNotNull(newRule.getTaskCron()) && !isEqual(oldRule.getTaskCron(), newRule.getTaskCron())){
				log.setId(null);
				log.setType(DatabaseConstant.JobUpdateType.UPDATE_EXECUTE_TIME.getValue());
				log.setRemark(oldRule.getTaskCron()+"===>"+newRule.getTaskCron());
				jobUpdateLogDao.insert(log);
			}
			if(isNotNull(newRule.getHandlerName()) && !isEqual(oldRule.getHandlerName(), newRule.getHandlerName())){
				log.setId(null);
				log.setType(DatabaseConstant.JobUpdateType.UPDATE_EXECUTE_HANDLER.getValue());
				log.setRemark(oldRule.getHandlerName()+"===>"+newRule.getHandlerName());
				jobUpdateLogDao.insert(log);
			}
			if(isNotNull(newRule.getStatus()) && !isEqual(oldRule.getStatus(),newRule.getStatus())){
				String remark = null;
				if(newRule.getStatus().intValue() == DatabaseConstant.JobStatus.USEING.getValue()){
					remark = "启用任务";
				}else{
					remark = "废弃任务";
				}
				log.setId(null);
				log.setType(DatabaseConstant.JobUpdateType.UPDATE_USE_STATUS.getValue());
				log.setRemark(remark);
				jobUpdateLogDao.insert(log);
			}
			if(isNotNull(newRule.getRunStatus()) && !isEqual(oldRule.getRunStatus(),newRule.getRunStatus())){
				String remark = null;
				if(newRule.getRunStatus().intValue() == DatabaseConstant.JobRunStatus.RUNNING.getValue()){
					remark = "运行任务";
				}else{
					remark = "停止任务";
				}
				log.setId(null);
				log.setType(DatabaseConstant.JobUpdateType.UPDATE_RUN_STATUS.getValue());
				log.setRemark(remark);
				jobUpdateLogDao.insert(log);
			}
			if(isNotNull(newRule.getRemark()) && !isEqual(newRule.getRemark(),oldRule.getRemark())){
				log.setId(null);
				log.setType(DatabaseConstant.JobUpdateType.UPDATE_OTHER_INFO.getValue());
				log.setRemark(oldRule.getRemark()+"===>"+newRule.getRemark());
				jobUpdateLogDao.insert(log);
			}
		}
	}
	
	private void checkHandlerExistOrNot(String handlerName)throws MyException{
		String beanName = handlerName.substring(handlerName.lastIndexOf(".")+1, handlerName.length());
		beanName = beanName.substring(0, 1).toLowerCase()+beanName.substring(1, beanName.length());
		boolean isExist = applicationContext.containsBean(beanName);
		if(!isExist){
			JobException.handlerNotExist();
		}
		Object obj = applicationContext.getBean(beanName);
		if(!(obj instanceof BaseJob)){
			JobException.handlerNotSupport();
		}
	}
	
	private String generateNo(String mark){
		mark = isNull(mark) ? "" : mark;
		return mark+DateTimeUtil.nowTime();
	}

	@Override
	public PageResult<JobUpdateLog> getJobUpdateLogList(JobUpdateLog query) throws MyException {
		return jobUpdateLogDao.selectPageResultByExample(query);
	}

	@Override
	public PageResult<JobRunLog> getJobRunLogList(JobRunLog query) throws MyException {
		return jobRunLogDao.selectPageResultByExample(query);
	}

	@Override
	public PageResult<JobRule> getJobTaskList(JobRule query) throws MyException {
		return jobRuleDao.selectPageResultByExample(query);
	}

	@PostConstruct
	private void initTemporaryJobHandler() {
		temporaryJobHandler = new HashMap<String, String>();
		if(isNullOrEmpty(temporaryJob)) {
			return;
		}
		for(int i=0; i<temporaryJob.length; i++) {
			TemporaryJob job = temporaryJob[i];
			temporaryJobHandler.put(job.getType().name(), job.getClass().getName());
		}
	}
}
