package com.jweb.schedule.service;

import com.jweb.common.exception.MyException;
import com.jweb.dao.base.PageResult;
import com.jweb.dao.constant.DatabaseConstant.JobRunResult;
import com.jweb.dao.constant.DatabaseConstant.JobRunStatus;
import com.jweb.dao.constant.DatabaseConstant.JobStatus;
import com.jweb.dao.constant.DatabaseConstant.JobType;
import com.jweb.dao.entity.JobRule;
import com.jweb.dao.entity.JobRunLog;
import com.jweb.dao.entity.JobUpdateLog;
import com.jweb.schedule.handler.executor.temporary.TemporaryJobType;

/**
 * 
 * @author 邓超
 *
 * 2022/6/4 12:24
 */
public interface JobService {

	/**
	 * 新增固定任务
	 * @param adminUserId 操作员ID
	 * @param taskName 任务名称
	 * @param taskCron 定时表达式
	 * @param handlerName 任务执行器类完整包名
	 * @param remark 任务描述
	 * @throws MyException
	 */
	void createFixedJobTask(long adminUserId, String taskName, String taskCron, String handlerName, String remark)throws MyException;
	
	/**
	 * 新增临时任务
	 * @param type 任务类型
	 * @param timingTime 定时时间（格式：yyyy-MM-dd HH:mm:ss），5分钟以后
	 * @param content 任务内容
	 * @throws MyException
	 */
	void createTemporaryJobTask(TemporaryJobType type, String timingTime, String content)throws MyException;
	/**
	 * 编辑任务
	 * @param adminUserId 操作员ID
	 * @param taskId 任务ID
	 * @param taskCron 定时表达式
	 * @param handlerName 任务执行器类完整包名
	 * @param remark 任务描述
	 * @throws MyException
	 */
	void updateJobTask(long adminUserId, long taskId, String taskCron, String handlerName, String remark)throws MyException;
	
	/**
	 * 启用/废弃任务
	 * @param adminUserId 操作员ID
	 * @param taskId 任务ID
	 * @param jobStatus 启用/废弃状态
	 * @throws MyException
	 */
	void updateJobTaskForUse(long adminUserId, long taskId, JobStatus jobStatus)throws MyException;
	
	/**
	 * 运行/停止任务
	 * @param adminUserId 操作员ID
	 * @param taskId 任务ID
	 * @param jobStatus 运行/停止状态
	 * @throws MyException
	 */
	void updateJobTaskForRun(long adminUserId, long taskId, JobRunStatus jobRunStatus)throws MyException;
	

	PageResult<JobRule> getJobTaskList(JobRule query)throws MyException;
	
	PageResult<JobUpdateLog> getJobUpdateLogList(JobUpdateLog query)throws MyException;
	
	PageResult<JobRunLog> getJobRunLogList(JobRunLog query)throws MyException;
	
	JobRule getJobTaskById(long taskId)throws MyException;
	
	long createJobRunLog(JobRunLog log)throws MyException;
	
	void updateJobRunLog(JobRunLog log)throws MyException;
	
	void doJobOnceRunFinish(long taskId, long logId, JobType type, JobRunResult result, String remark)throws MyException;
}
