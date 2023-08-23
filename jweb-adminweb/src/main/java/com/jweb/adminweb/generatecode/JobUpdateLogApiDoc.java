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
@RequestMapping(value="jobupdatelog")
@Api(tags="任务修改日志管理接口")
public interface JobUpdateLogApiDoc {

  @ApiOperation(value = "添加任务修改日志",notes = "添加任务修改日志")
  @ApiImplicitParams({
    @ApiImplicitParam(name="task_id", dataType="Long", paramType="form", required=true, value="任务ID"),
    @ApiImplicitParam(name="task_no", dataType="String", paramType="form", required=true, value="任务编号"),
    @ApiImplicitParam(name="task_name", dataType="String", paramType="form", required=true, value="任务名"),
    @ApiImplicitParam(name="type", dataType="Integer", paramType="form", required=true, value="修改类型：0-创建任务，1-修改执行时间，2-修改使用状态，3-修改运行状态，4-修改执行器，5-修改其他信息"),
    @ApiImplicitParam(name="admin_user_id", dataType="Long", paramType="form", required=true, value="操作员ID"),
    @ApiImplicitParam(name="remark", dataType="String", paramType="form", required=false, value="备注"),
    @ApiImplicitParam(name="ctdate", dataType="Long", paramType="form", required=true, value="创建时间"),
    @ApiImplicitParam(name="mddate", dataType="Long", paramType="form", required=true, value="更新时间")
  })
  @PostMapping(value = "/create")
  public ApiResult addJobUpdateLog(
    @RequestParam(value="task_id", required=true) Long taskId,
    @RequestParam(value="task_no", required=true) String taskNo,
    @RequestParam(value="task_name", required=true) String taskName,
    @RequestParam(value="type", required=true) Integer type,
    @RequestParam(value="admin_user_id", required=true) Long adminUserId,
    @RequestParam(value="remark", required=false) String remark,
    @RequestParam(value="ctdate", required=true) Long ctdate,
    @RequestParam(value="mddate", required=true) Long mddate);

  @ApiOperation(value = "修改任务修改日志",notes = "修改任务修改日志")
  @ApiImplicitParams({
    @ApiImplicitParam(name="id", dataType="Long", paramType="form", required=true, value=""),
    @ApiImplicitParam(name="task_id", dataType="Long", paramType="form", required=false, value="任务ID"),
    @ApiImplicitParam(name="task_no", dataType="String", paramType="form", required=false, value="任务编号"),
    @ApiImplicitParam(name="task_name", dataType="String", paramType="form", required=false, value="任务名"),
    @ApiImplicitParam(name="type", dataType="Integer", paramType="form", required=false, value="修改类型：0-创建任务，1-修改执行时间，2-修改使用状态，3-修改运行状态，4-修改执行器，5-修改其他信息"),
    @ApiImplicitParam(name="admin_user_id", dataType="Long", paramType="form", required=false, value="操作员ID"),
    @ApiImplicitParam(name="remark", dataType="String", paramType="form", required=false, value="备注"),
    @ApiImplicitParam(name="ctdate", dataType="Long", paramType="form", required=false, value="创建时间"),
    @ApiImplicitParam(name="mddate", dataType="Long", paramType="form", required=false, value="更新时间")
  })
  @PostMapping(value = "/update")
  public ApiResult editJobUpdateLog(
    @RequestParam(value="id", required=true) Long id,
    @RequestParam(value="task_id", required=false) Long taskId,
    @RequestParam(value="task_no", required=false) String taskNo,
    @RequestParam(value="task_name", required=false) String taskName,
    @RequestParam(value="type", required=false) Integer type,
    @RequestParam(value="admin_user_id", required=false) Long adminUserId,
    @RequestParam(value="remark", required=false) String remark,
    @RequestParam(value="ctdate", required=false) Long ctdate,
    @RequestParam(value="mddate", required=false) Long mddate);

  @ApiOperation(value = "根据条件查询任务修改日志",notes = "根据条件查询任务修改日志")
  @ApiImplicitParams({
    @ApiImplicitParam(name="task_id", dataType="Long", paramType="form", required=false, value="任务ID"),
    @ApiImplicitParam(name="task_no", dataType="String", paramType="form", required=false, value="任务编号"),
    @ApiImplicitParam(name="task_name", dataType="String", paramType="form", required=false, value="任务名"),
    @ApiImplicitParam(name="type", dataType="Integer", paramType="form", required=false, value="修改类型：0-创建任务，1-修改执行时间，2-修改使用状态，3-修改运行状态，4-修改执行器，5-修改其他信息"),
    @ApiImplicitParam(name="admin_user_id", dataType="Long", paramType="form", required=false, value="操作员ID"),
    @ApiImplicitParam(name="remark", dataType="String", paramType="form", required=false, value="备注"),
    @ApiImplicitParam(name="ctdate", dataType="Long", paramType="form", required=false, value="创建时间"),
    @ApiImplicitParam(name="mddate", dataType="Long", paramType="form", required=false, value="更新时间"),
    @ApiImplicitParam(name="page", dataType="Integer", paramType="form", required=false, value="分页页码，默认1"),
    @ApiImplicitParam(name="limit", dataType="Integer", paramType="form", required=false, value="分页每页条数，默认20")
  })
  @PostMapping(value = "/query")
  public ApiResult getJobUpdateLogList(
    @RequestParam(value="task_id", required=false) Long taskId,
    @RequestParam(value="task_no", required=false) String taskNo,
    @RequestParam(value="task_name", required=false) String taskName,
    @RequestParam(value="type", required=false) Integer type,
    @RequestParam(value="admin_user_id", required=false) Long adminUserId,
    @RequestParam(value="remark", required=false) String remark,
    @RequestParam(value="ctdate", required=false) Long ctdate,
    @RequestParam(value="mddate", required=false) Long mddate,
    @RequestParam(value="page", required=false, defaultValue = "1") Integer page,
    @RequestParam(value="limit", required=false, defaultValue = "20") Integer limit);

  @ApiOperation(value = "根据ID查询任务修改日志",notes = "根据ID查询任务修改日志")
  @ApiImplicitParams({
    @ApiImplicitParam(name="id", dataType="Long", paramType="form", required=true, value="ID")
  })
  @PostMapping(value = "/queryById")
  public ApiResult getJobUpdateLog(@RequestParam(value="id", required=true) Long id);

}
