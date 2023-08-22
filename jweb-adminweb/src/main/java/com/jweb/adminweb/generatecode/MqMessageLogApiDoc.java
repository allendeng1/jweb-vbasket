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
 * 2023/08/22 15:26
*/
@RequestMapping(value="mqmessagelog")
@Api(tags="MQ异步消息消费日志管理接口")
public interface MqMessageLogApiDoc {

  @ApiOperation(value = "添加MQ异步消息消费日志",notes = "添加MQ异步消息消费日志")
  @ApiImplicitParams({
    @ApiImplicitParam(name="message_id", dataType="Long", paramType="form", required=true, value="消息ID"),
    @ApiImplicitParam(name="type", dataType="String", paramType="form", required=true, value="类型"),
    @ApiImplicitParam(name="excute_method", dataType="String", paramType="form", required=true, value="处理方式"),
    @ApiImplicitParam(name="excute_handler", dataType="String", paramType="form", required=true, value="处理器"),
    @ApiImplicitParam(name="start_time", dataType="Long", paramType="form", required=true, value="开始时间"),
    @ApiImplicitParam(name="end_time", dataType="Long", paramType="form", required=true, value="结束时间"),
    @ApiImplicitParam(name="result_desc", dataType="String", paramType="form", required=true, value="处理结果描述"),
    @ApiImplicitParam(name="ctdate", dataType="Long", paramType="form", required=true, value="创建时间"),
    @ApiImplicitParam(name="mddate", dataType="Long", paramType="form", required=true, value="更新时间")
  })
  @PostMapping(value = "/create")
  public ApiResult addMqMessageLog(
    @RequestParam(value="message_id", required=true) Long messageId,
    @RequestParam(value="type", required=true) String type,
    @RequestParam(value="excute_method", required=true) String excuteMethod,
    @RequestParam(value="excute_handler", required=true) String excuteHandler,
    @RequestParam(value="start_time", required=true) Long startTime,
    @RequestParam(value="end_time", required=true) Long endTime,
    @RequestParam(value="result_desc", required=true) String resultDesc,
    @RequestParam(value="ctdate", required=true) Long ctdate,
    @RequestParam(value="mddate", required=true) Long mddate);

  @ApiOperation(value = "修改MQ异步消息消费日志",notes = "修改MQ异步消息消费日志")
  @ApiImplicitParams({
    @ApiImplicitParam(name="id", dataType="Long", paramType="form", required=true, value=""),
    @ApiImplicitParam(name="message_id", dataType="Long", paramType="form", required=false, value="消息ID"),
    @ApiImplicitParam(name="type", dataType="String", paramType="form", required=false, value="类型"),
    @ApiImplicitParam(name="excute_method", dataType="String", paramType="form", required=false, value="处理方式"),
    @ApiImplicitParam(name="excute_handler", dataType="String", paramType="form", required=false, value="处理器"),
    @ApiImplicitParam(name="start_time", dataType="Long", paramType="form", required=false, value="开始时间"),
    @ApiImplicitParam(name="end_time", dataType="Long", paramType="form", required=false, value="结束时间"),
    @ApiImplicitParam(name="result_desc", dataType="String", paramType="form", required=false, value="处理结果描述"),
    @ApiImplicitParam(name="ctdate", dataType="Long", paramType="form", required=false, value="创建时间"),
    @ApiImplicitParam(name="mddate", dataType="Long", paramType="form", required=false, value="更新时间")
  })
  @PostMapping(value = "/update")
  public ApiResult editMqMessageLog(
    @RequestParam(value="id", required=true) Long id,
    @RequestParam(value="message_id", required=false) Long messageId,
    @RequestParam(value="type", required=false) String type,
    @RequestParam(value="excute_method", required=false) String excuteMethod,
    @RequestParam(value="excute_handler", required=false) String excuteHandler,
    @RequestParam(value="start_time", required=false) Long startTime,
    @RequestParam(value="end_time", required=false) Long endTime,
    @RequestParam(value="result_desc", required=false) String resultDesc,
    @RequestParam(value="ctdate", required=false) Long ctdate,
    @RequestParam(value="mddate", required=false) Long mddate);

  @ApiOperation(value = "根据条件查询MQ异步消息消费日志",notes = "根据条件查询MQ异步消息消费日志")
  @ApiImplicitParams({
    @ApiImplicitParam(name="message_id", dataType="Long", paramType="form", required=false, value="消息ID"),
    @ApiImplicitParam(name="type", dataType="String", paramType="form", required=false, value="类型"),
    @ApiImplicitParam(name="excute_method", dataType="String", paramType="form", required=false, value="处理方式"),
    @ApiImplicitParam(name="excute_handler", dataType="String", paramType="form", required=false, value="处理器"),
    @ApiImplicitParam(name="start_time", dataType="Long", paramType="form", required=false, value="开始时间"),
    @ApiImplicitParam(name="end_time", dataType="Long", paramType="form", required=false, value="结束时间"),
    @ApiImplicitParam(name="result_desc", dataType="String", paramType="form", required=false, value="处理结果描述"),
    @ApiImplicitParam(name="ctdate", dataType="Long", paramType="form", required=false, value="创建时间"),
    @ApiImplicitParam(name="mddate", dataType="Long", paramType="form", required=false, value="更新时间"),
    @ApiImplicitParam(name="page", dataType="Integer", paramType="form", required=false, value="分页页码，默认1"),
    @ApiImplicitParam(name="limit", dataType="Integer", paramType="form", required=false, value="分页每页条数，默认20")
  })
  @PostMapping(value = "/query")
  public ApiResult getMqMessageLogList(
    @RequestParam(value="message_id", required=false) Long messageId,
    @RequestParam(value="type", required=false) String type,
    @RequestParam(value="excute_method", required=false) String excuteMethod,
    @RequestParam(value="excute_handler", required=false) String excuteHandler,
    @RequestParam(value="start_time", required=false) Long startTime,
    @RequestParam(value="end_time", required=false) Long endTime,
    @RequestParam(value="result_desc", required=false) String resultDesc,
    @RequestParam(value="ctdate", required=false) Long ctdate,
    @RequestParam(value="mddate", required=false) Long mddate,
    @RequestParam(value="page", required=false, defaultValue = "1") Integer page,
    @RequestParam(value="limit", required=false, defaultValue = "20") Integer limit);

  @ApiOperation(value = "根据ID查询MQ异步消息消费日志",notes = "根据ID查询MQ异步消息消费日志")
  @ApiImplicitParams({
    @ApiImplicitParam(name="id", dataType="Long", paramType="form", required=true, value="ID")
  })
  @PostMapping(value = "/queryById")
  public ApiResult getMqMessageLog(@RequestParam(value="id", required=true) Long id);

}
