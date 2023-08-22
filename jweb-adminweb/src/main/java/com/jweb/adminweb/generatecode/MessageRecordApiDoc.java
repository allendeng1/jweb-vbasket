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
@RequestMapping(value="messagerecord")
@Api(tags="消息发送记录管理接口")
public interface MessageRecordApiDoc {

  @ApiOperation(value = "添加消息发送记录",notes = "添加消息发送记录")
  @ApiImplicitParams({
    @ApiImplicitParam(name="code", dataType="String", paramType="form", required=true, value="模板编号"),
    @ApiImplicitParam(name="sent_channel", dataType="String", paramType="form", required=true, value="发送渠道"),
    @ApiImplicitParam(name="status", dataType="Integer", paramType="form", required=true, value="状态：0-待发送，1-发送中，2-发送成功，3-发送失败"),
    @ApiImplicitParam(name="sender", dataType="Long", paramType="form", required=true, value="发送者"),
    @ApiImplicitParam(name="receiver", dataType="String", paramType="form", required=true, value="接收者"),
    @ApiImplicitParam(name="sent_way", dataType="Integer", paramType="form", required=true, value="发送方式：1-实时发送，2-定时发送"),
    @ApiImplicitParam(name="timing_time", dataType="Long", paramType="form", required=false, value="定时发送时间点"),
    @ApiImplicitParam(name="content", dataType="String", paramType="form", required=true, value="消息内容"),
    @ApiImplicitParam(name="sent_time", dataType="Long", paramType="form", required=false, value="发送时间"),
    @ApiImplicitParam(name="sent_result", dataType="String", paramType="form", required=false, value="发送结果"),
    @ApiImplicitParam(name="template_code", dataType="String", paramType="form", required=false, value="第三方平台模板编号"),
    @ApiImplicitParam(name="reference", dataType="String", paramType="form", required=false, value="第三方消息标识"),
    @ApiImplicitParam(name="callback_time", dataType="Long", paramType="form", required=false, value="回调时间"),
    @ApiImplicitParam(name="callback_result", dataType="String", paramType="form", required=false, value="回调结果"),
    @ApiImplicitParam(name="is_delete", dataType="Boolean", paramType="form", required=true, value=""),
    @ApiImplicitParam(name="ctdate", dataType="Long", paramType="form", required=true, value=""),
    @ApiImplicitParam(name="mddate", dataType="Long", paramType="form", required=true, value="")
  })
  @PostMapping(value = "/create")
  public ApiResult addMessageRecord(
    @RequestParam(value="code", required=true) String code,
    @RequestParam(value="sent_channel", required=true) String sentChannel,
    @RequestParam(value="status", required=true) Integer status,
    @RequestParam(value="sender", required=true) Long sender,
    @RequestParam(value="receiver", required=true) String receiver,
    @RequestParam(value="sent_way", required=true) Integer sentWay,
    @RequestParam(value="timing_time", required=false) Long timingTime,
    @RequestParam(value="content", required=true) String content,
    @RequestParam(value="sent_time", required=false) Long sentTime,
    @RequestParam(value="sent_result", required=false) String sentResult,
    @RequestParam(value="template_code", required=false) String templateCode,
    @RequestParam(value="reference", required=false) String reference,
    @RequestParam(value="callback_time", required=false) Long callbackTime,
    @RequestParam(value="callback_result", required=false) String callbackResult,
    @RequestParam(value="is_delete", required=true) Boolean isDelete,
    @RequestParam(value="ctdate", required=true) Long ctdate,
    @RequestParam(value="mddate", required=true) Long mddate);

  @ApiOperation(value = "修改消息发送记录",notes = "修改消息发送记录")
  @ApiImplicitParams({
    @ApiImplicitParam(name="id", dataType="Long", paramType="form", required=true, value=""),
    @ApiImplicitParam(name="code", dataType="String", paramType="form", required=false, value="模板编号"),
    @ApiImplicitParam(name="sent_channel", dataType="String", paramType="form", required=false, value="发送渠道"),
    @ApiImplicitParam(name="status", dataType="Integer", paramType="form", required=false, value="状态：0-待发送，1-发送中，2-发送成功，3-发送失败"),
    @ApiImplicitParam(name="sender", dataType="Long", paramType="form", required=false, value="发送者"),
    @ApiImplicitParam(name="receiver", dataType="String", paramType="form", required=false, value="接收者"),
    @ApiImplicitParam(name="sent_way", dataType="Integer", paramType="form", required=false, value="发送方式：1-实时发送，2-定时发送"),
    @ApiImplicitParam(name="timing_time", dataType="Long", paramType="form", required=false, value="定时发送时间点"),
    @ApiImplicitParam(name="content", dataType="String", paramType="form", required=false, value="消息内容"),
    @ApiImplicitParam(name="sent_time", dataType="Long", paramType="form", required=false, value="发送时间"),
    @ApiImplicitParam(name="sent_result", dataType="String", paramType="form", required=false, value="发送结果"),
    @ApiImplicitParam(name="template_code", dataType="String", paramType="form", required=false, value="第三方平台模板编号"),
    @ApiImplicitParam(name="reference", dataType="String", paramType="form", required=false, value="第三方消息标识"),
    @ApiImplicitParam(name="callback_time", dataType="Long", paramType="form", required=false, value="回调时间"),
    @ApiImplicitParam(name="callback_result", dataType="String", paramType="form", required=false, value="回调结果"),
    @ApiImplicitParam(name="is_delete", dataType="Boolean", paramType="form", required=false, value=""),
    @ApiImplicitParam(name="ctdate", dataType="Long", paramType="form", required=false, value=""),
    @ApiImplicitParam(name="mddate", dataType="Long", paramType="form", required=false, value="")
  })
  @PostMapping(value = "/update")
  public ApiResult editMessageRecord(
    @RequestParam(value="id", required=true) Long id,
    @RequestParam(value="code", required=false) String code,
    @RequestParam(value="sent_channel", required=false) String sentChannel,
    @RequestParam(value="status", required=false) Integer status,
    @RequestParam(value="sender", required=false) Long sender,
    @RequestParam(value="receiver", required=false) String receiver,
    @RequestParam(value="sent_way", required=false) Integer sentWay,
    @RequestParam(value="timing_time", required=false) Long timingTime,
    @RequestParam(value="content", required=false) String content,
    @RequestParam(value="sent_time", required=false) Long sentTime,
    @RequestParam(value="sent_result", required=false) String sentResult,
    @RequestParam(value="template_code", required=false) String templateCode,
    @RequestParam(value="reference", required=false) String reference,
    @RequestParam(value="callback_time", required=false) Long callbackTime,
    @RequestParam(value="callback_result", required=false) String callbackResult,
    @RequestParam(value="is_delete", required=false) Boolean isDelete,
    @RequestParam(value="ctdate", required=false) Long ctdate,
    @RequestParam(value="mddate", required=false) Long mddate);

  @ApiOperation(value = "根据条件查询消息发送记录",notes = "根据条件查询消息发送记录")
  @ApiImplicitParams({
    @ApiImplicitParam(name="code", dataType="String", paramType="form", required=false, value="模板编号"),
    @ApiImplicitParam(name="sent_channel", dataType="String", paramType="form", required=false, value="发送渠道"),
    @ApiImplicitParam(name="status", dataType="Integer", paramType="form", required=false, value="状态：0-待发送，1-发送中，2-发送成功，3-发送失败"),
    @ApiImplicitParam(name="sender", dataType="Long", paramType="form", required=false, value="发送者"),
    @ApiImplicitParam(name="receiver", dataType="String", paramType="form", required=false, value="接收者"),
    @ApiImplicitParam(name="sent_way", dataType="Integer", paramType="form", required=false, value="发送方式：1-实时发送，2-定时发送"),
    @ApiImplicitParam(name="timing_time", dataType="Long", paramType="form", required=false, value="定时发送时间点"),
    @ApiImplicitParam(name="content", dataType="String", paramType="form", required=false, value="消息内容"),
    @ApiImplicitParam(name="sent_time", dataType="Long", paramType="form", required=false, value="发送时间"),
    @ApiImplicitParam(name="sent_result", dataType="String", paramType="form", required=false, value="发送结果"),
    @ApiImplicitParam(name="template_code", dataType="String", paramType="form", required=false, value="第三方平台模板编号"),
    @ApiImplicitParam(name="reference", dataType="String", paramType="form", required=false, value="第三方消息标识"),
    @ApiImplicitParam(name="callback_time", dataType="Long", paramType="form", required=false, value="回调时间"),
    @ApiImplicitParam(name="callback_result", dataType="String", paramType="form", required=false, value="回调结果"),
    @ApiImplicitParam(name="is_delete", dataType="Boolean", paramType="form", required=false, value=""),
    @ApiImplicitParam(name="ctdate", dataType="Long", paramType="form", required=false, value=""),
    @ApiImplicitParam(name="mddate", dataType="Long", paramType="form", required=false, value=""),
    @ApiImplicitParam(name="page", dataType="Integer", paramType="form", required=false, value="分页页码，默认1"),
    @ApiImplicitParam(name="limit", dataType="Integer", paramType="form", required=false, value="分页每页条数，默认20")
  })
  @PostMapping(value = "/query")
  public ApiResult getMessageRecordList(
    @RequestParam(value="code", required=false) String code,
    @RequestParam(value="sent_channel", required=false) String sentChannel,
    @RequestParam(value="status", required=false) Integer status,
    @RequestParam(value="sender", required=false) Long sender,
    @RequestParam(value="receiver", required=false) String receiver,
    @RequestParam(value="sent_way", required=false) Integer sentWay,
    @RequestParam(value="timing_time", required=false) Long timingTime,
    @RequestParam(value="content", required=false) String content,
    @RequestParam(value="sent_time", required=false) Long sentTime,
    @RequestParam(value="sent_result", required=false) String sentResult,
    @RequestParam(value="template_code", required=false) String templateCode,
    @RequestParam(value="reference", required=false) String reference,
    @RequestParam(value="callback_time", required=false) Long callbackTime,
    @RequestParam(value="callback_result", required=false) String callbackResult,
    @RequestParam(value="is_delete", required=false) Boolean isDelete,
    @RequestParam(value="ctdate", required=false) Long ctdate,
    @RequestParam(value="mddate", required=false) Long mddate,
    @RequestParam(value="page", required=false, defaultValue = "1") Integer page,
    @RequestParam(value="limit", required=false, defaultValue = "20") Integer limit);

  @ApiOperation(value = "根据ID查询消息发送记录",notes = "根据ID查询消息发送记录")
  @ApiImplicitParams({
    @ApiImplicitParam(name="id", dataType="Long", paramType="form", required=true, value="ID")
  })
  @PostMapping(value = "/queryById")
  public ApiResult getMessageRecord(@RequestParam(value="id", required=true) Long id);

}
