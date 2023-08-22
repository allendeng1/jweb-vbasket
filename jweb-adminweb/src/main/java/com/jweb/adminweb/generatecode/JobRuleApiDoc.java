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
 * 2023/08/22 14:43
*/
@RequestMapping(value="jobrule")
@Api(tags="任务管理接口")
public interface JobRuleApiDoc {

  @ApiOperation(value = "添加任务",notes = "添加任务")
  @ApiImplicitParams({
    @ApiImplicitParam(name="task_no", dataType="String", paramType="form", required=true, value="任务编号"),
    @ApiImplicitParam(name="task_name", dataType="String", paramType="form", required=true, value="任务名"),
    @ApiImplicitParam(name="type", dataType="Integer", paramType="form", required=true, value="使用状态：1-固定任务，2-临时任务"),
    @ApiImplicitParam(name="task_cron", dataType="String", paramType="form", required=true, value="定时表达式"),
    @ApiImplicitParam(name="status", dataType="Integer", paramType="form", required=true, value="使用状态：0-使用中，1-已废弃"),
    @ApiImplicitParam(name="run_status", dataType="Integer", paramType="form", required=true, value="运行状态：0-未运行，1-运行中，2-已执行"),
    @ApiImplicitParam(name="handler_name", dataType="String", paramType="form", required=true, value="任务执行器类完整包名"),
    @ApiImplicitParam(name="task_content", dataType="String", paramType="form", required=false, value="任务内容"),
    @ApiImplicitParam(name="remark", dataType="String", paramType="form", required=false, value="备注"),
    @ApiImplicitParam(name="ctdate", dataType="Long", paramType="form", required=true, value="创建时间"),
    @ApiImplicitParam(name="mddate", dataType="Long", paramType="form", required=true, value="更新时间")
  })
  @PostMapping(value = "/create")
  public ApiResult addJobRule(
    @RequestParam(value="task_no", required=true) String taskNo,
    @RequestParam(value="task_name", required=true) String taskName,
    @RequestParam(value="type", required=true) Integer type,
    @RequestParam(value="task_cron", required=true) String taskCron,
    @RequestParam(value="status", required=true) Integer status,
    @RequestParam(value="run_status", required=true) Integer runStatus,
    @RequestParam(value="handler_name", required=true) String handlerName,
    @RequestParam(value="task_content", required=false) String taskContent,
    @RequestParam(value="remark", required=false) String remark,
    @RequestParam(value="ctdate", required=true) Long ctdate,
    @RequestParam(value="mddate", required=true) Long mddate);

  @ApiOperation(value = "修改任务",notes = "修改任务")
  @ApiImplicitParams({
    @ApiImplicitParam(name="id", dataType="Long", paramType="form", required=true, value=""),
    @ApiImplicitParam(name="task_no", dataType="String", paramType="form", required=false, value="任务编号"),
    @ApiImplicitParam(name="task_name", dataType="String", paramType="form", required=false, value="任务名"),
    @ApiImplicitParam(name="type", dataType="Integer", paramType="form", required=false, value="使用状态：1-固定任务，2-临时任务"),
    @ApiImplicitParam(name="task_cron", dataType="String", paramType="form", required=false, value="定时表达式"),
    @ApiImplicitParam(name="status", dataType="Integer", paramType="form", required=false, value="使用状态：0-使用中，1-已废弃"),
    @ApiImplicitParam(name="run_status", dataType="Integer", paramType="form", required=false, value="运行状态：0-未运行，1-运行中，2-已执行"),
    @ApiImplicitParam(name="handler_name", dataType="String", paramType="form", required=false, value="任务执行器类完整包名"),
    @ApiImplicitParam(name="task_content", dataType="String", paramType="form", required=false, value="任务内容"),
    @ApiImplicitParam(name="remark", dataType="String", paramType="form", required=false, value="备注"),
    @ApiImplicitParam(name="ctdate", dataType="Long", paramType="form", required=false, value="创建时间"),
    @ApiImplicitParam(name="mddate", dataType="Long", paramType="form", required=false, value="更新时间")
  })
  @PostMapping(value = "/update")
  public ApiResult editJobRule(
    @RequestParam(value="id", required=true) Long id,
    @RequestParam(value="task_no", required=false) String taskNo,
    @RequestParam(value="task_name", required=false) String taskName,
    @RequestParam(value="type", required=false) Integer type,
    @RequestParam(value="task_cron", required=false) String taskCron,
    @RequestParam(value="status", required=false) Integer status,
    @RequestParam(value="run_status", required=false) Integer runStatus,
    @RequestParam(value="handler_name", required=false) String handlerName,
    @RequestParam(value="task_content", required=false) String taskContent,
    @RequestParam(value="remark", required=false) String remark,
    @RequestParam(value="ctdate", required=false) Long ctdate,
    @RequestParam(value="mddate", required=false) Long mddate);

  @ApiOperation(value = "根据条件查询任务",notes = "根据条件查询任务")
  @ApiImplicitParams({
    @ApiImplicitParam(name="task_no", dataType="String", paramType="form", required=false, value="任务编号"),
    @ApiImplicitParam(name="task_name", dataType="String", paramType="form", required=false, value="任务名"),
    @ApiImplicitParam(name="type", dataType="Integer", paramType="form", required=false, value="使用状态：1-固定任务，2-临时任务"),
    @ApiImplicitParam(name="task_cron", dataType="String", paramType="form", required=false, value="定时表达式"),
    @ApiImplicitParam(name="status", dataType="Integer", paramType="form", required=false, value="使用状态：0-使用中，1-已废弃"),
    @ApiImplicitParam(name="run_status", dataType="Integer", paramType="form", required=false, value="运行状态：0-未运行，1-运行中，2-已执行"),
    @ApiImplicitParam(name="handler_name", dataType="String", paramType="form", required=false, value="任务执行器类完整包名"),
    @ApiImplicitParam(name="task_content", dataType="String", paramType="form", required=false, value="任务内容"),
    @ApiImplicitParam(name="remark", dataType="String", paramType="form", required=false, value="备注"),
    @ApiImplicitParam(name="ctdate", dataType="Long", paramType="form", required=false, value="创建时间"),
    @ApiImplicitParam(name="mddate", dataType="Long", paramType="form", required=false, value="更新时间"),
    @ApiImplicitParam(name="page", dataType="Integer", paramType="form", required=false, value="分页页码，默认1"),
    @ApiImplicitParam(name="limit", dataType="Integer", paramType="form", required=false, value="分页每页条数，默认20")
  })
  @PostMapping(value = "/query")
  public ApiResult getJobRuleList(
    @RequestParam(value="task_no", required=false) String taskNo,
    @RequestParam(value="task_name", required=false) String taskName,
    @RequestParam(value="type", required=false) Integer type,
    @RequestParam(value="task_cron", required=false) String taskCron,
    @RequestParam(value="status", required=false) Integer status,
    @RequestParam(value="run_status", required=false) Integer runStatus,
    @RequestParam(value="handler_name", required=false) String handlerName,
    @RequestParam(value="task_content", required=false) String taskContent,
    @RequestParam(value="remark", required=false) String remark,
    @RequestParam(value="ctdate", required=false) Long ctdate,
    @RequestParam(value="mddate", required=false) Long mddate,
    @RequestParam(value="page", required=false, defaultValue = "1") Integer page,
    @RequestParam(value="limit", required=false, defaultValue = "20") Integer limit);

  @ApiOperation(value = "根据ID查询任务",notes = "根据ID查询任务")
  @ApiImplicitParams({
    @ApiImplicitParam(name="id", dataType="Long", paramType="form", required=true, value="ID")
  })
  @PostMapping(value = "/queryById")
  public ApiResult getJobRule(@RequestParam(value="id", required=true) Long id);

}
