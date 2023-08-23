package com.jweb.adminweb.generatecode;

import com.jweb.common.api.ApiResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
/**
 *
 * @author 邓超
 *
 * 2023/08/22 18:02
*/
@RequestMapping(value="jobrunlog")
@Api(tags="任务运行日志管理接口")
public interface JobRunLogApiDoc {

  @ApiOperation(value = "添加任务运行日志",notes = "添加任务运行日志")
  @ApiImplicitParams({
    @ApiImplicitParam(name="run_no", dataType="String", paramType="form", required=true, value="运行编号"),
    @ApiImplicitParam(name="task_id", dataType="Long", paramType="form", required=true, value="任务ID"),
    @ApiImplicitParam(name="task_no", dataType="String", paramType="form", required=true, value="任务编号"),
    @ApiImplicitParam(name="task_name", dataType="String", paramType="form", required=true, value="任务名"),
    @ApiImplicitParam(name="status", dataType="Integer", paramType="form", required=true, value="状态：0-运行中，1-运行结束"),
    @ApiImplicitParam(name="run_result", dataType="Integer", paramType="form", required=false, value="运行结果：0-成功，1-失败"),
    @ApiImplicitParam(name="start_date", dataType="Long", paramType="form", required=true, value="开始时间"),
    @ApiImplicitParam(name="end_date", dataType="Long", paramType="form", required=false, value="结束时间"),
    @ApiImplicitParam(name="remark", dataType="String", paramType="form", required=false, value="备注"),
    @ApiImplicitParam(name="ctdate", dataType="Long", paramType="form", required=true, value="创建时间"),
    @ApiImplicitParam(name="mddate", dataType="Long", paramType="form", required=true, value="更新时间")
  })
  @PostMapping(value = "/create")
  public ApiResult addJobRunLog(
    @RequestParam(value="run_no", required=true) String runNo,
    @RequestParam(value="task_id", required=true) Long taskId,
    @RequestParam(value="task_no", required=true) String taskNo,
    @RequestParam(value="task_name", required=true) String taskName,
    @RequestParam(value="status", required=true) Integer status,
    @RequestParam(value="run_result", required=false) Integer runResult,
    @RequestParam(value="start_date", required=true) Long startDate,
    @RequestParam(value="end_date", required=false) Long endDate,
    @RequestParam(value="remark", required=false) String remark,
    @RequestParam(value="ctdate", required=true) Long ctdate,
    @RequestParam(value="mddate", required=true) Long mddate);

  @ApiOperation(value = "修改任务运行日志",notes = "修改任务运行日志")
  @ApiImplicitParams({
    @ApiImplicitParam(name="id", dataType="Long", paramType="form", required=true, value=""),
    @ApiImplicitParam(name="run_no", dataType="String", paramType="form", required=false, value="运行编号"),
    @ApiImplicitParam(name="task_id", dataType="Long", paramType="form", required=false, value="任务ID"),
    @ApiImplicitParam(name="task_no", dataType="String", paramType="form", required=false, value="任务编号"),
    @ApiImplicitParam(name="task_name", dataType="String", paramType="form", required=false, value="任务名"),
    @ApiImplicitParam(name="status", dataType="Integer", paramType="form", required=false, value="状态：0-运行中，1-运行结束"),
    @ApiImplicitParam(name="run_result", dataType="Integer", paramType="form", required=false, value="运行结果：0-成功，1-失败"),
    @ApiImplicitParam(name="start_date", dataType="Long", paramType="form", required=false, value="开始时间"),
    @ApiImplicitParam(name="end_date", dataType="Long", paramType="form", required=false, value="结束时间"),
    @ApiImplicitParam(name="remark", dataType="String", paramType="form", required=false, value="备注"),
    @ApiImplicitParam(name="ctdate", dataType="Long", paramType="form", required=false, value="创建时间"),
    @ApiImplicitParam(name="mddate", dataType="Long", paramType="form", required=false, value="更新时间")
  })
  @PostMapping(value = "/update")
  public ApiResult editJobRunLog(
    @RequestParam(value="id", required=true) Long id,
    @RequestParam(value="run_no", required=false) String runNo,
    @RequestParam(value="task_id", required=false) Long taskId,
    @RequestParam(value="task_no", required=false) String taskNo,
    @RequestParam(value="task_name", required=false) String taskName,
    @RequestParam(value="status", required=false) Integer status,
    @RequestParam(value="run_result", required=false) Integer runResult,
    @RequestParam(value="start_date", required=false) Long startDate,
    @RequestParam(value="end_date", required=false) Long endDate,
    @RequestParam(value="remark", required=false) String remark,
    @RequestParam(value="ctdate", required=false) Long ctdate,
    @RequestParam(value="mddate", required=false) Long mddate);

  @ApiOperation(value = "根据条件查询任务运行日志",notes = "根据条件查询任务运行日志")
  @ApiImplicitParams({
    @ApiImplicitParam(name="run_no", dataType="String", paramType="form", required=false, value="运行编号"),
    @ApiImplicitParam(name="task_id", dataType="Long", paramType="form", required=false, value="任务ID"),
    @ApiImplicitParam(name="task_no", dataType="String", paramType="form", required=false, value="任务编号"),
    @ApiImplicitParam(name="task_name", dataType="String", paramType="form", required=false, value="任务名"),
    @ApiImplicitParam(name="status", dataType="Integer", paramType="form", required=false, value="状态：0-运行中，1-运行结束"),
    @ApiImplicitParam(name="run_result", dataType="Integer", paramType="form", required=false, value="运行结果：0-成功，1-失败"),
    @ApiImplicitParam(name="start_date", dataType="Long", paramType="form", required=false, value="开始时间"),
    @ApiImplicitParam(name="end_date", dataType="Long", paramType="form", required=false, value="结束时间"),
    @ApiImplicitParam(name="remark", dataType="String", paramType="form", required=false, value="备注"),
    @ApiImplicitParam(name="ctdate", dataType="Long", paramType="form", required=false, value="创建时间"),
    @ApiImplicitParam(name="mddate", dataType="Long", paramType="form", required=false, value="更新时间"),
    @ApiImplicitParam(name="page", dataType="Integer", paramType="form", required=false, value="分页页码，默认1"),
    @ApiImplicitParam(name="limit", dataType="Integer", paramType="form", required=false, value="分页每页条数，默认20")
  })
  @PostMapping(value = "/query")
  public ApiResult getJobRunLogList(
    @RequestParam(value="run_no", required=false) String runNo,
    @RequestParam(value="task_id", required=false) Long taskId,
    @RequestParam(value="task_no", required=false) String taskNo,
    @RequestParam(value="task_name", required=false) String taskName,
    @RequestParam(value="status", required=false) Integer status,
    @RequestParam(value="run_result", required=false) Integer runResult,
    @RequestParam(value="start_date", required=false) Long startDate,
    @RequestParam(value="end_date", required=false) Long endDate,
    @RequestParam(value="remark", required=false) String remark,
    @RequestParam(value="ctdate", required=false) Long ctdate,
    @RequestParam(value="mddate", required=false) Long mddate,
    @RequestParam(value="page", required=false, defaultValue = "1") Integer page,
    @RequestParam(value="limit", required=false, defaultValue = "20") Integer limit);

  @ApiOperation(value = "根据ID查询任务运行日志",notes = "根据ID查询任务运行日志")
  @ApiImplicitParams({
    @ApiImplicitParam(name="id", dataType="Long", paramType="form", required=true, value="ID")
  })
  @PostMapping(value = "/queryById")
  public ApiResult getJobRunLog(@RequestParam(value="id", required=true) Long id);

}
