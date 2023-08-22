package com.jweb.mq.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.jweb.common.api.ApiResult;
import com.jweb.dao.constant.DatabaseConstant.MqMsgChannel;
import com.jweb.mq.controller.vo.MqMessageLogResult;
import com.jweb.mq.controller.vo.MqMessageResult;
import com.jweb.mq.message.MessageQueue;
import com.jweb.mq.message.MessageTopic;
import com.jweb.mq.message.MessageType;

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
@RequestMapping(value="mq")
@Api(tags="异步消息接口")
public interface MqApiDoc {

	@ApiOperation(value = "发布队列消息",notes = "发布队列消息")
	@ApiImplicitParams({
    	@ApiImplicitParam(name="bizType", dataType="String", paramType="form", required=true, value="业务类型"),
    	@ApiImplicitParam(name="content", dataType="String", paramType="form", required=true, value="消息内容"),
    	@ApiImplicitParam(name="label", dataType="String", paramType="form", required=false, value="消息队列标签：VIP-VIP队列，ORDER-顺序队列，INDEX_DOC-搜索引擎索引同步队列，GENERAL-普通队列")
    })
	@PostMapping("/publish/queue/message")
	public ApiResult publishQueueMessage(@RequestParam(value="bizType", required=true) MessageType bizType,
			                 @RequestParam(value="content", required=true) String content,
			                 @RequestParam(value="label", required=false, defaultValue="GENERAL") String label);
	
	@ApiOperation(value = "发布主题消息",notes = "发布主题消息")
	@ApiImplicitParams({
    	@ApiImplicitParam(name="topic", dataType="String", paramType="form", required=true, value="主题名称"),
    	@ApiImplicitParam(name="content", dataType="String", paramType="form", required=true, value="消息内容")
    })
	@PostMapping("/publish/topic/message")
	public ApiResult publishTopicMessage(@RequestParam(value="topic", required=true) MessageTopic topic,
			                 @RequestParam(value="content", required=true) String content);
	
	@ApiOperation(value = "重新发布消息",notes = "重新发布未发布成功或执行失败的消息")
	@ApiImplicitParams({
    	@ApiImplicitParam(name="msgIds", dataType="String", paramType="form", required=true, value="消息ID集，以逗号分割")
    })
	@PostMapping("/publish/retry/message")
	public ApiResult publishRetryMessage(@RequestParam(value="msgIds", required=true) String msgIds);
	
	@ApiOperation(value = "删除消息",notes = "删除消息")
	@ApiImplicitParams({
    	@ApiImplicitParam(name="msgIds", dataType="String", paramType="form", required=true, value="消息ID集，以逗号分割")
    })
	@PostMapping("/batch/delete/message")
	public ApiResult deleteMessage(@RequestParam(value="msgIds", required=true) String msgIds);
	
	@ApiOperation(value = "查询消息列表",notes = "查询消息列表")
	@ApiImplicitParams({
    	@ApiImplicitParam(name="channel", dataType="String", paramType="query", required=false, value="消息渠道"),
    	@ApiImplicitParam(name="topic", dataType="String", paramType="query", required=false, value="消息主题名称"),
    	@ApiImplicitParam(name="queue", dataType="String", paramType="query", required=false, value="消息队列名称"),
    	@ApiImplicitParam(name="bizType", dataType="String", paramType="query", required=false, value="消息业务类型"),
    	@ApiImplicitParam(name="status", dataType="int", paramType="query", required=false, value="状态:0-未处理，1-已发布，2-处理成功，3-处理失败"),
    	@ApiImplicitParam(name="minPublishTime", dataType="String", paramType="query", required=false, value="发布时间最小值(格式:yyyy-MM-dd HH:mm:ss)"),
    	@ApiImplicitParam(name="maxPublishTime", dataType="String", paramType="query", required=false, value="发布时间最大值(格式:yyyy-MM-dd HH:mm:ss)"),
    	@ApiImplicitParam(name="content", dataType="String", paramType="query", required=false, value="消息内容(模糊匹配)"),
    	@ApiImplicitParam(name="page", dataType="int", paramType="query", required=false, value="分页页码,默认1"),
    	@ApiImplicitParam(name="pageSize", dataType="int", paramType="query", required=false, value="分页每页条数,默认15")
    })
	@GetMapping("/messageList")
	public MqMessageResult messageList(@RequestParam(value="channel", required=false) MqMsgChannel channel,
								 @RequestParam(value="topic", required=false) MessageTopic topic,
								 @RequestParam(value="queue", required=false) MessageQueue queue,
								 @RequestParam(value="bizType", required=false) MessageType bizType,
								 @RequestParam(value="status", required=false) Integer status,
								 @RequestParam(value="minPublishTime", required=false) String minPublishTime,
								 @RequestParam(value="maxPublishTime", required=false) String maxPublishTime,
								 @RequestParam(value="content", required=false) String content,
								 @RequestParam(value="page", required=false, defaultValue="1") int page,
								 @RequestParam(value="pageSize", required=false , defaultValue="15") int pageSize);
	
	@ApiOperation(value = "查询消息日志列表",notes = "查询消息日志列表")
	@ApiImplicitParams({
    	@ApiImplicitParam(name="messageId", dataType="Long", paramType="query", required=true, value="消息ID")
    })
	@GetMapping("/message/logList")
	public MqMessageLogResult messageLogList(@RequestParam(value="messageId", required=true) Long messageId);
	
}
