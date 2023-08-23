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
@RequestMapping(value="mqmessage")
@Api(tags="MQ异步消息管理接口")
public interface MqMessageApiDoc {

  @ApiOperation(value = "添加MQ异步消息",notes = "添加MQ异步消息")
  @ApiImplicitParams({
    @ApiImplicitParam(name="channel", dataType="String", paramType="form", required=true, value="消息通道"),
    @ApiImplicitParam(name="topic_name", dataType="String", paramType="form", required=false, value="主题名称"),
    @ApiImplicitParam(name="queue_name", dataType="String", paramType="form", required=true, value="队列名称"),
    @ApiImplicitParam(name="biz_type", dataType="String", paramType="form", required=false, value="业务类型"),
    @ApiImplicitParam(name="status", dataType="Integer", paramType="form", required=true, value="状态：0-未处理，1-已发布，2-处理成功，3-处理失败"),
    @ApiImplicitParam(name="publish_time", dataType="Long", paramType="form", required=false, value="发布时间"),
    @ApiImplicitParam(name="content", dataType="String", paramType="form", required=true, value="消息内容"),
    @ApiImplicitParam(name="is_delete", dataType="Boolean", paramType="form", required=true, value="是否删除"),
    @ApiImplicitParam(name="delete_time", dataType="Long", paramType="form", required=false, value="删除时间"),
    @ApiImplicitParam(name="ctdate", dataType="Long", paramType="form", required=true, value="创建时间"),
    @ApiImplicitParam(name="mddate", dataType="Long", paramType="form", required=true, value="更新时间")
  })
  @PostMapping(value = "/create")
  public ApiResult addMqMessage(
    @RequestParam(value="channel", required=true) String channel,
    @RequestParam(value="topic_name", required=false) String topicName,
    @RequestParam(value="queue_name", required=true) String queueName,
    @RequestParam(value="biz_type", required=false) String bizType,
    @RequestParam(value="status", required=true) Integer status,
    @RequestParam(value="publish_time", required=false) Long publishTime,
    @RequestParam(value="content", required=true) String content,
    @RequestParam(value="is_delete", required=true) Boolean isDelete,
    @RequestParam(value="delete_time", required=false) Long deleteTime,
    @RequestParam(value="ctdate", required=true) Long ctdate,
    @RequestParam(value="mddate", required=true) Long mddate);

  @ApiOperation(value = "修改MQ异步消息",notes = "修改MQ异步消息")
  @ApiImplicitParams({
    @ApiImplicitParam(name="id", dataType="Long", paramType="form", required=true, value=""),
    @ApiImplicitParam(name="channel", dataType="String", paramType="form", required=false, value="消息通道"),
    @ApiImplicitParam(name="topic_name", dataType="String", paramType="form", required=false, value="主题名称"),
    @ApiImplicitParam(name="queue_name", dataType="String", paramType="form", required=false, value="队列名称"),
    @ApiImplicitParam(name="biz_type", dataType="String", paramType="form", required=false, value="业务类型"),
    @ApiImplicitParam(name="status", dataType="Integer", paramType="form", required=false, value="状态：0-未处理，1-已发布，2-处理成功，3-处理失败"),
    @ApiImplicitParam(name="publish_time", dataType="Long", paramType="form", required=false, value="发布时间"),
    @ApiImplicitParam(name="content", dataType="String", paramType="form", required=false, value="消息内容"),
    @ApiImplicitParam(name="is_delete", dataType="Boolean", paramType="form", required=false, value="是否删除"),
    @ApiImplicitParam(name="delete_time", dataType="Long", paramType="form", required=false, value="删除时间"),
    @ApiImplicitParam(name="ctdate", dataType="Long", paramType="form", required=false, value="创建时间"),
    @ApiImplicitParam(name="mddate", dataType="Long", paramType="form", required=false, value="更新时间")
  })
  @PostMapping(value = "/update")
  public ApiResult editMqMessage(
    @RequestParam(value="id", required=true) Long id,
    @RequestParam(value="channel", required=false) String channel,
    @RequestParam(value="topic_name", required=false) String topicName,
    @RequestParam(value="queue_name", required=false) String queueName,
    @RequestParam(value="biz_type", required=false) String bizType,
    @RequestParam(value="status", required=false) Integer status,
    @RequestParam(value="publish_time", required=false) Long publishTime,
    @RequestParam(value="content", required=false) String content,
    @RequestParam(value="is_delete", required=false) Boolean isDelete,
    @RequestParam(value="delete_time", required=false) Long deleteTime,
    @RequestParam(value="ctdate", required=false) Long ctdate,
    @RequestParam(value="mddate", required=false) Long mddate);

  @ApiOperation(value = "根据条件查询MQ异步消息",notes = "根据条件查询MQ异步消息")
  @ApiImplicitParams({
    @ApiImplicitParam(name="channel", dataType="String", paramType="form", required=false, value="消息通道"),
    @ApiImplicitParam(name="topic_name", dataType="String", paramType="form", required=false, value="主题名称"),
    @ApiImplicitParam(name="queue_name", dataType="String", paramType="form", required=false, value="队列名称"),
    @ApiImplicitParam(name="biz_type", dataType="String", paramType="form", required=false, value="业务类型"),
    @ApiImplicitParam(name="status", dataType="Integer", paramType="form", required=false, value="状态：0-未处理，1-已发布，2-处理成功，3-处理失败"),
    @ApiImplicitParam(name="publish_time", dataType="Long", paramType="form", required=false, value="发布时间"),
    @ApiImplicitParam(name="content", dataType="String", paramType="form", required=false, value="消息内容"),
    @ApiImplicitParam(name="is_delete", dataType="Boolean", paramType="form", required=false, value="是否删除"),
    @ApiImplicitParam(name="delete_time", dataType="Long", paramType="form", required=false, value="删除时间"),
    @ApiImplicitParam(name="ctdate", dataType="Long", paramType="form", required=false, value="创建时间"),
    @ApiImplicitParam(name="mddate", dataType="Long", paramType="form", required=false, value="更新时间"),
    @ApiImplicitParam(name="page", dataType="Integer", paramType="form", required=false, value="分页页码，默认1"),
    @ApiImplicitParam(name="limit", dataType="Integer", paramType="form", required=false, value="分页每页条数，默认20")
  })
  @PostMapping(value = "/query")
  public ApiResult getMqMessageList(
    @RequestParam(value="channel", required=false) String channel,
    @RequestParam(value="topic_name", required=false) String topicName,
    @RequestParam(value="queue_name", required=false) String queueName,
    @RequestParam(value="biz_type", required=false) String bizType,
    @RequestParam(value="status", required=false) Integer status,
    @RequestParam(value="publish_time", required=false) Long publishTime,
    @RequestParam(value="content", required=false) String content,
    @RequestParam(value="is_delete", required=false) Boolean isDelete,
    @RequestParam(value="delete_time", required=false) Long deleteTime,
    @RequestParam(value="ctdate", required=false) Long ctdate,
    @RequestParam(value="mddate", required=false) Long mddate,
    @RequestParam(value="page", required=false, defaultValue = "1") Integer page,
    @RequestParam(value="limit", required=false, defaultValue = "20") Integer limit);

  @ApiOperation(value = "根据ID查询MQ异步消息",notes = "根据ID查询MQ异步消息")
  @ApiImplicitParams({
    @ApiImplicitParam(name="id", dataType="Long", paramType="form", required=true, value="ID")
  })
  @PostMapping(value = "/queryById")
  public ApiResult getMqMessage(@RequestParam(value="id", required=true) Long id);

}
