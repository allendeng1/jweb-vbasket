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
@RequestMapping(value="apilog")
@Api(tags="APP日志管理接口")
public interface ApiLogApiDoc {

  @ApiOperation(value = "添加APP日志",notes = "添加APP日志")
  @ApiImplicitParams({
    @ApiImplicitParam(name="server_context_path", dataType="String", paramType="form", required=true, value="web服务标识"),
    @ApiImplicitParam(name="ip_address", dataType="String", paramType="form", required=false, value="客户端IP"),
    @ApiImplicitParam(name="user_id", dataType="Long", paramType="form", required=false, value="用户ID"),
    @ApiImplicitParam(name="req_url", dataType="String", paramType="form", required=true, value="请求URL"),
    @ApiImplicitParam(name="req_method", dataType="String", paramType="form", required=true, value="请求方式"),
    @ApiImplicitParam(name="begin_time", dataType="Long", paramType="form", required=true, value="开始时间"),
    @ApiImplicitParam(name="end_time", dataType="Long", paramType="form", required=true, value="结束时间"),
    @ApiImplicitParam(name="cost_time", dataType="Long", paramType="form", required=true, value="耗时(单位：毫秒)"),
    @ApiImplicitParam(name="is_attack", dataType="Boolean", paramType="form", required=true, value="系统防攻击检测状态：true-疑似攻击"),
    @ApiImplicitParam(name="req_param", dataType="String", paramType="form", required=false, value="请求参数"),
    @ApiImplicitParam(name="controller_name", dataType="String", paramType="form", required=true, value="请求处理器名称"),
    @ApiImplicitParam(name="controller_method", dataType="String", paramType="form", required=true, value="请求处理方法名"),
    @ApiImplicitParam(name="result_data", dataType="String", paramType="form", required=false, value="返回结果数据"),
    @ApiImplicitParam(name="ctdate", dataType="Long", paramType="form", required=true, value="创建时间"),
    @ApiImplicitParam(name="mddate", dataType="Long", paramType="form", required=true, value="更新时间")
  })
  @PostMapping(value = "/create")
  public ApiResult addApiLog(
    @RequestParam(value="server_context_path", required=true) String serverContextPath,
    @RequestParam(value="ip_address", required=false) String ipAddress,
    @RequestParam(value="user_id", required=false) Long userId,
    @RequestParam(value="req_url", required=true) String reqUrl,
    @RequestParam(value="req_method", required=true) String reqMethod,
    @RequestParam(value="begin_time", required=true) Long beginTime,
    @RequestParam(value="end_time", required=true) Long endTime,
    @RequestParam(value="cost_time", required=true) Long costTime,
    @RequestParam(value="is_attack", required=true) Boolean isAttack,
    @RequestParam(value="req_param", required=false) String reqParam,
    @RequestParam(value="controller_name", required=true) String controllerName,
    @RequestParam(value="controller_method", required=true) String controllerMethod,
    @RequestParam(value="result_data", required=false) String resultData,
    @RequestParam(value="ctdate", required=true) Long ctdate,
    @RequestParam(value="mddate", required=true) Long mddate);

  @ApiOperation(value = "修改APP日志",notes = "修改APP日志")
  @ApiImplicitParams({
    @ApiImplicitParam(name="id", dataType="Long", paramType="form", required=true, value=""),
    @ApiImplicitParam(name="server_context_path", dataType="String", paramType="form", required=false, value="web服务标识"),
    @ApiImplicitParam(name="ip_address", dataType="String", paramType="form", required=false, value="客户端IP"),
    @ApiImplicitParam(name="user_id", dataType="Long", paramType="form", required=false, value="用户ID"),
    @ApiImplicitParam(name="req_url", dataType="String", paramType="form", required=false, value="请求URL"),
    @ApiImplicitParam(name="req_method", dataType="String", paramType="form", required=false, value="请求方式"),
    @ApiImplicitParam(name="begin_time", dataType="Long", paramType="form", required=false, value="开始时间"),
    @ApiImplicitParam(name="end_time", dataType="Long", paramType="form", required=false, value="结束时间"),
    @ApiImplicitParam(name="cost_time", dataType="Long", paramType="form", required=false, value="耗时(单位：毫秒)"),
    @ApiImplicitParam(name="is_attack", dataType="Boolean", paramType="form", required=false, value="系统防攻击检测状态：true-疑似攻击"),
    @ApiImplicitParam(name="req_param", dataType="String", paramType="form", required=false, value="请求参数"),
    @ApiImplicitParam(name="controller_name", dataType="String", paramType="form", required=false, value="请求处理器名称"),
    @ApiImplicitParam(name="controller_method", dataType="String", paramType="form", required=false, value="请求处理方法名"),
    @ApiImplicitParam(name="result_data", dataType="String", paramType="form", required=false, value="返回结果数据"),
    @ApiImplicitParam(name="ctdate", dataType="Long", paramType="form", required=false, value="创建时间"),
    @ApiImplicitParam(name="mddate", dataType="Long", paramType="form", required=false, value="更新时间")
  })
  @PostMapping(value = "/update")
  public ApiResult editApiLog(
    @RequestParam(value="id", required=true) Long id,
    @RequestParam(value="server_context_path", required=false) String serverContextPath,
    @RequestParam(value="ip_address", required=false) String ipAddress,
    @RequestParam(value="user_id", required=false) Long userId,
    @RequestParam(value="req_url", required=false) String reqUrl,
    @RequestParam(value="req_method", required=false) String reqMethod,
    @RequestParam(value="begin_time", required=false) Long beginTime,
    @RequestParam(value="end_time", required=false) Long endTime,
    @RequestParam(value="cost_time", required=false) Long costTime,
    @RequestParam(value="is_attack", required=false) Boolean isAttack,
    @RequestParam(value="req_param", required=false) String reqParam,
    @RequestParam(value="controller_name", required=false) String controllerName,
    @RequestParam(value="controller_method", required=false) String controllerMethod,
    @RequestParam(value="result_data", required=false) String resultData,
    @RequestParam(value="ctdate", required=false) Long ctdate,
    @RequestParam(value="mddate", required=false) Long mddate);

  @ApiOperation(value = "根据条件查询APP日志",notes = "根据条件查询APP日志")
  @ApiImplicitParams({
    @ApiImplicitParam(name="server_context_path", dataType="String", paramType="form", required=false, value="web服务标识"),
    @ApiImplicitParam(name="ip_address", dataType="String", paramType="form", required=false, value="客户端IP"),
    @ApiImplicitParam(name="user_id", dataType="Long", paramType="form", required=false, value="用户ID"),
    @ApiImplicitParam(name="req_url", dataType="String", paramType="form", required=false, value="请求URL"),
    @ApiImplicitParam(name="req_method", dataType="String", paramType="form", required=false, value="请求方式"),
    @ApiImplicitParam(name="begin_time", dataType="Long", paramType="form", required=false, value="开始时间"),
    @ApiImplicitParam(name="end_time", dataType="Long", paramType="form", required=false, value="结束时间"),
    @ApiImplicitParam(name="cost_time", dataType="Long", paramType="form", required=false, value="耗时(单位：毫秒)"),
    @ApiImplicitParam(name="is_attack", dataType="Boolean", paramType="form", required=false, value="系统防攻击检测状态：true-疑似攻击"),
    @ApiImplicitParam(name="req_param", dataType="String", paramType="form", required=false, value="请求参数"),
    @ApiImplicitParam(name="controller_name", dataType="String", paramType="form", required=false, value="请求处理器名称"),
    @ApiImplicitParam(name="controller_method", dataType="String", paramType="form", required=false, value="请求处理方法名"),
    @ApiImplicitParam(name="result_data", dataType="String", paramType="form", required=false, value="返回结果数据"),
    @ApiImplicitParam(name="ctdate", dataType="Long", paramType="form", required=false, value="创建时间"),
    @ApiImplicitParam(name="mddate", dataType="Long", paramType="form", required=false, value="更新时间"),
    @ApiImplicitParam(name="page", dataType="Integer", paramType="form", required=false, value="分页页码，默认1"),
    @ApiImplicitParam(name="limit", dataType="Integer", paramType="form", required=false, value="分页每页条数，默认20")
  })
  @PostMapping(value = "/query")
  public ApiResult getApiLogList(
    @RequestParam(value="server_context_path", required=false) String serverContextPath,
    @RequestParam(value="ip_address", required=false) String ipAddress,
    @RequestParam(value="user_id", required=false) Long userId,
    @RequestParam(value="req_url", required=false) String reqUrl,
    @RequestParam(value="req_method", required=false) String reqMethod,
    @RequestParam(value="begin_time", required=false) Long beginTime,
    @RequestParam(value="end_time", required=false) Long endTime,
    @RequestParam(value="cost_time", required=false) Long costTime,
    @RequestParam(value="is_attack", required=false) Boolean isAttack,
    @RequestParam(value="req_param", required=false) String reqParam,
    @RequestParam(value="controller_name", required=false) String controllerName,
    @RequestParam(value="controller_method", required=false) String controllerMethod,
    @RequestParam(value="result_data", required=false) String resultData,
    @RequestParam(value="ctdate", required=false) Long ctdate,
    @RequestParam(value="mddate", required=false) Long mddate,
    @RequestParam(value="page", required=false, defaultValue = "1") Integer page,
    @RequestParam(value="limit", required=false, defaultValue = "20") Integer limit);

  @ApiOperation(value = "根据ID查询APP日志",notes = "根据ID查询APP日志")
  @ApiImplicitParams({
    @ApiImplicitParam(name="id", dataType="Long", paramType="form", required=true, value="ID")
  })
  @PostMapping(value = "/queryById")
  public ApiResult getApiLog(@RequestParam(value="id", required=true) Long id);

}
