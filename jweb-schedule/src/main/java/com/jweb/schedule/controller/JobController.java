package com.jweb.schedule.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.jweb.common.api.ApiResult;
import com.jweb.common.api.BaseController;
import com.jweb.common.exception.MyException;
import com.jweb.dao.base.PageResult;
import com.jweb.dao.constant.DatabaseConstant;
import com.jweb.dao.constant.DatabaseConstant.JobRunLogStatus;
import com.jweb.dao.constant.DatabaseConstant.JobRunResult;
import com.jweb.dao.constant.DatabaseConstant.JobRunStatus;
import com.jweb.dao.constant.DatabaseConstant.JobStatus;
import com.jweb.dao.constant.DatabaseConstant.JobUpdateType;
import com.jweb.dao.entity.JobRule;
import com.jweb.dao.entity.JobRunLog;
import com.jweb.dao.entity.JobUpdateLog;
import com.jweb.schedule.cluster.CommandType;
import com.jweb.schedule.cluster.JobClusterManager;
import com.jweb.schedule.cluster.JobCommand;
import com.jweb.schedule.controller.vo.JobRuleResult;
import com.jweb.schedule.controller.vo.JobRunLogResult;
import com.jweb.schedule.controller.vo.JobUpdateLogResult;
import com.jweb.schedule.handler.ScheduledControlCenter;
import com.jweb.schedule.handler.executor.temporary.TemporaryJobType;
import com.jweb.schedule.service.JobService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class JobController extends BaseController implements JobApiDoc{

	@Autowired
	private JobService jobService;
	@Autowired
	private ScheduledControlCenter scheduledControlCenter;
	@Autowired
	private JobClusterManager jobClusterManager;
	
	@Override
	public ApiResult addFixedTask(String taskName, String taskCron, String handlerName, String remark) {
		ApiResult result = new ApiResult();
		try {
			jobService.createFixedJobTask(123L, taskName, taskCron, handlerName, remark);
		} catch (MyException e) {
			log.error(e.getMessage(), e);
			result.bizFail(e);
		}
		return result;
	}

	@Override
	public ApiResult addTemporaryTask(TemporaryJobType type, String timingTime, String content) {
		ApiResult result = new ApiResult();
		try {
			jobService.createTemporaryJobTask(type, timingTime, content);
		} catch (MyException e) {
			log.error(e.getMessage(), e);
			result.bizFail(e);
		}
		return result;
	}
	@Override
	public ApiResult updateTask(Long taskId, String taskCron, String handlerName, String remark) {
		ApiResult result = new ApiResult();
		try {
			jobService.updateJobTask(123L, taskId, taskCron, handlerName, remark);
		} catch (MyException e) {
			log.error(e.getMessage(), e);
			result.bizFail(e);
		}
		return result;
	}
	
	@Override
	public ApiResult updateTaskUse(Long taskId, JobStatus jobStatus) {
		ApiResult result = new ApiResult();
		try {
			jobService.updateJobTaskForUse(123L, taskId, jobStatus);
		} catch (MyException e) {
			log.error(e.getMessage(), e);
			result.bizFail(e);
		}
		return result;
	}

	@Override
	public ApiResult updateTaskRun(Long taskId, JobRunStatus jobRunStatus) {
		ApiResult result = new ApiResult();
		try {
			JobCommand command = new JobCommand();
			command.setAdminUserId(123L);
			command.setTaskId(taskId);
			if(jobRunStatus.equals(JobRunStatus.RUNNING)){
				scheduledControlCenter.restart(123L, taskId, true);
				command.setType(CommandType.RESTART_JOB);
			}else{
				scheduledControlCenter.stop(123L, taskId, true);
				command.setType(CommandType.STOP_JOB);
			}
			jobClusterManager.sentCommand(command);
		} catch (MyException e) {
			log.error(e.getMessage(), e);
			result.bizFail(e);
		}
		return result;
	}

	@Override
	public JobRuleResult getJobTaskList(String taskNo, String taskNameLike, DatabaseConstant.JobType JobType, DatabaseConstant.JobStatus jobStatus, DatabaseConstant.JobRunStatus runStatus,
			Integer page, Integer pageSize) {
		JobRuleResult result = new JobRuleResult();
		try {
			JobRule query = new JobRule();
			query.setTaskNo(taskNo);
			query.andLike("task_name", taskNameLike);
			if(isNotNull(JobType)){
				query.setType(JobType.getValue());
			}
			if(isNotNull(jobStatus)){
				query.setStatus(jobStatus.getValue());
			}
			if(isNotNull(runStatus)){
				query.setRunStatus(runStatus.getValue());
			}
			query.page(page, pageSize);
			PageResult<JobRule> pageResult = jobService.getJobTaskList(query);
			result.setData(pageResult);
		} catch (MyException e) {
			log.error(e.getMessage(), e);
			result.bizFail(e);
		}
		return result;
	}

	@Override
	public JobUpdateLogResult getJobUpdateLogList(Long taskId, JobUpdateType type, Integer page, Integer pageSize) {
		JobUpdateLogResult result = new JobUpdateLogResult();
		try {
			
			JobUpdateLog query = new JobUpdateLog();
			query.setTaskId(taskId);
			if(isNotNull(type)){
				query.setType(type.getValue());
			}
			query.page(page, pageSize);
			PageResult<JobUpdateLog> pageResult = jobService.getJobUpdateLogList(query);
			result.setData(pageResult);
		} catch (MyException e) {
			log.error(e.getMessage(), e);
			result.bizFail(e);
		}
		return result;
	}

	@Override
	public JobRunLogResult getJobRunLogList(Long taskId, JobRunLogStatus status, JobRunResult runResult, Integer page,
			Integer pageSize) {
		
		JobRunLogResult result = new JobRunLogResult();
		try {
			JobRunLog query = new JobRunLog();
			query.setTaskId(taskId);
			if(isNotNull(status)){
				query.setStatus(status.getValue());
			}
			if(isNotNull(runResult)){
				query.setRunResult(runResult.getValue());
			}
			query.page(page, pageSize);
			PageResult<JobRunLog> pageResult =  jobService.getJobRunLogList(query);
			result.setData(pageResult);
			
		} catch (MyException e) {
			log.error(e.getMessage(), e);
			result.bizFail(e);
		}
		return result;
	}

}
