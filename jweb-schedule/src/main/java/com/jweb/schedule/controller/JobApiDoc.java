package com.jweb.schedule.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.jweb.common.api.ApiResult;
import com.jweb.dao.constant.DatabaseConstant;
import com.jweb.dao.constant.DatabaseConstant.JobRunLogStatus;
import com.jweb.dao.constant.DatabaseConstant.JobRunResult;
import com.jweb.dao.constant.DatabaseConstant.JobUpdateType;
import com.jweb.schedule.controller.vo.JobRuleResult;
import com.jweb.schedule.controller.vo.JobRunLogResult;
import com.jweb.schedule.controller.vo.JobUpdateLogResult;
import com.jweb.schedule.handler.executor.temporary.TemporaryJobType;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
/**
 * 
 * @author 邓超
 *
 * 2022/6/4 12:24
 */
@RequestMapping(value="job")
@Api(tags="任务调度管理接口")
public interface JobApiDoc {
	
	@ApiOperation(value = "新增固定任务",notes = "新增固定任务")
	@ApiImplicitParams({
    	@ApiImplicitParam(name="taskName", dataType="String", paramType="form", required=true, value="任务名称"),
    	@ApiImplicitParam(name="taskCron", dataType="String", paramType="form", required=true, value="定时表达式"),
    	@ApiImplicitParam(name="handlerName", dataType="String", paramType="form", required=true, value="任务执行器类完整包名"),
    	@ApiImplicitParam(name="remark", dataType="String", paramType="form", required=false, value="任务描述")
    	
    })
	@PostMapping("/addFixedTask")
	public ApiResult addFixedTask(@RequestParam(value="taskName", required=true) String taskName,
			                 @RequestParam(value="taskCron", required=true) String taskCron,
			                 @RequestParam(value="handlerName", required=true) String handlerName,
			                 @RequestParam(value="remark", required=false) String remark);
	
	
	@ApiOperation(value = "新增临时任务",notes = "新增临时任务")
	@ApiImplicitParams({
    	@ApiImplicitParam(name="type", dataType="String", paramType="form", required=true, value="任务类型"),
    	@ApiImplicitParam(name="timingTime", dataType="String", paramType="form", required=true, value="定时时间（格式：yyyy-MM-dd HH:mm:ss）"),
    	@ApiImplicitParam(name="content", dataType="String", paramType="form", required=true, value="任务内容")
    	
    })
	@PostMapping("/addTemporaryTask")
	public ApiResult addTemporaryTask(@RequestParam(value="type", required=true) TemporaryJobType type,
			                 @RequestParam(value="timingTime", required=true) String timingTime,
			                 @RequestParam(value="content", required=true) String content);
	
	@ApiOperation(value = "编辑任务",notes = "编辑任务")
	@ApiImplicitParams({
    	@ApiImplicitParam(name="taskId", dataType="Long", paramType="form", required=true, value="任务ID"),
    	@ApiImplicitParam(name="taskCron", dataType="String", paramType="form", required=false, value="定时表达式"),
    	@ApiImplicitParam(name="handlerName", dataType="String", paramType="form", required=false, value="任务执行器类完整包名"),
    	@ApiImplicitParam(name="remark", dataType="String", paramType="form", required=false, value="任务描述")
    	
    })
	@PostMapping("/updateTask")
	public ApiResult updateTask(@RequestParam(value="taskId", required=true) Long taskId,
			                 @RequestParam(value="taskCron", required=false) String taskCron,
			                 @RequestParam(value="handlerName", required=false) String handlerName,
			                 @RequestParam(value="remark", required=false) String remark);
	
	@ApiOperation(value = "启用/废弃任务",notes = "启用/废弃任务")
	@ApiImplicitParams({
    	@ApiImplicitParam(name="taskId", dataType="Long", paramType="form", required=true, value="任务ID"),
    	@ApiImplicitParam(name="status", dataType="String", paramType="form", required=true, value="状态：USEING-启用，DISCARD-废弃")
    })
	@PostMapping("/updateTask/use")
	public ApiResult updateTaskUse(@RequestParam(value="taskId", required=true) Long taskId,
			                       @RequestParam(value="status", required=true) DatabaseConstant.JobStatus jobStatus);
	
	@ApiOperation(value = "运行/停止任务",notes = "运行/停止任务")
	@ApiImplicitParams({
    	@ApiImplicitParam(name="taskId", dataType="Long", paramType="form", required=true, value="任务ID"),
    	@ApiImplicitParam(name="status", dataType="String", paramType="form", required=true, value="状态：UN_RUN-停止，RUNNING-运行")
    })
	@PostMapping("/updateTask/run")
	public ApiResult updateTaskRun(@RequestParam(value="taskId", required=true) Long taskId,
			                       @RequestParam(value="status", required=true) DatabaseConstant.JobRunStatus jobStatus);
	
	@ApiOperation(value = "查询任务列表",notes = "查询任务列表")
	@ApiImplicitParams({
    	@ApiImplicitParam(name="taskNo", dataType="String", paramType="query", required=false, value="任务编号"),
    	@ApiImplicitParam(name="taskNameLike", dataType="String", paramType="query", required=false, value="任务名(模糊匹配)"),
    	@ApiImplicitParam(name="type", dataType="String", paramType="query", required=false, value="任务类型：FIXED-固定任务，TEMPORARY-临时任务"),
    	@ApiImplicitParam(name="status", dataType="String", paramType="query", required=false, value="使用状态：USEING-启用，DISCARD-废弃"),
    	@ApiImplicitParam(name="runStatus", dataType="String", paramType="query", required=false, value="运行状态：UN_RUN-停止，RUNNING-运行，EXCUTED-已执行"),
    	@ApiImplicitParam(name="page", dataType="Int", paramType="query", required=false, value="分页页码，默认1"),
    	@ApiImplicitParam(name="pageSize", dataType="Int", paramType="query", required=false, value="分页每页条数，默认15")
    })
	@GetMapping("/getJobTask/list")
	public JobRuleResult getJobTaskList(@RequestParam(value="taskNo", required=false) String taskNo,
										@RequestParam(value="taskNameLike", required=false) String taskNameLike,
										@RequestParam(value="type", required=false) DatabaseConstant.JobType JobType,
										@RequestParam(value="status", required=false) DatabaseConstant.JobStatus jobStatus,
										@RequestParam(value="runStatus", required=false) DatabaseConstant.JobRunStatus runStatus,
										@RequestParam(value="page", required=false, defaultValue="1") Integer page,
										@RequestParam(value="pageSize", required=false, defaultValue="15") Integer pageSize);
	
	@ApiOperation(value = "查询任务修改日志列表",notes = "查询任务修改日志列表")
	@ApiImplicitParams({
    	@ApiImplicitParam(name="taskId", dataType="Long", paramType="query", required=false, value="任务Id"),
    	@ApiImplicitParam(name="type", dataType="String", paramType="query", required=false, value="修改类型：CREATE-创建任务，UPDATE_EXECUTE_TIME-修改执行时间，UPDATE_USE_STATUS-修改使用状态，UPDATE_RUN_STATUS-修改运行状态，UPDATE_EXECUTE_HANDLER-修改执行器，UPDATE_OTHER_INFO-修改其他信息"),
    	@ApiImplicitParam(name="page", dataType="Int", paramType="query", required=false, value="分页页码，默认1"),
    	@ApiImplicitParam(name="pageSize", dataType="Int", paramType="query", required=false, value="分页每页条数，默认15")
    })
	@GetMapping("/getJobTask/updateLog/list")
	public JobUpdateLogResult getJobUpdateLogList(@RequestParam(value="taskId", required=false) Long taskId,
										@RequestParam(value="type", required=false) JobUpdateType type,
										@RequestParam(value="page", required=false, defaultValue="1") Integer page,
										@RequestParam(value="pageSize", required=false, defaultValue="15") Integer pageSize);
	
	@ApiOperation(value = "查询任务运行日志列表",notes = "查询任务运行日志列表")
	@ApiImplicitParams({
    	@ApiImplicitParam(name="taskId", dataType="Long", paramType="query", required=false, value="任务Id"),
    	@ApiImplicitParam(name="status", dataType="String", paramType="query", required=false, value="状态：RUNNING-运行中，FINISH-运行结束"),
    	@ApiImplicitParam(name="runResult", dataType="String", paramType="query", required=false, value="运行结果：SUCCESS-成功，FAIL-失败"),
    	@ApiImplicitParam(name="page", dataType="Int", paramType="query", required=false, value="分页页码，默认1"),
    	@ApiImplicitParam(name="pageSize", dataType="Int", paramType="query", required=false, value="分页每页条数，默认15")
    })
	@GetMapping("/getJobTask/runLog/list")
	public JobRunLogResult getJobRunLogList(@RequestParam(value="taskId", required=false) Long taskId,
										@RequestParam(value="status", required=false) JobRunLogStatus status,
										@RequestParam(value="runResult", required=false) JobRunResult runResult,
										@RequestParam(value="page", required=false, defaultValue="1") Integer page,
										@RequestParam(value="pageSize", required=false, defaultValue="15") Integer pageSize);
	
}

