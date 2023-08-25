package com.jweb.mq.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.jweb.common.api.ApiResult;
import com.jweb.common.api.BaseController;
import com.jweb.common.exception.MqException;
import com.jweb.common.exception.MyException;
import com.jweb.common.util.DateTimeUtil;
import com.jweb.common.util.DateTimeUtil.Format;
import com.jweb.dao.base.BaseEntity.Sort;
import com.jweb.dao.base.PageResult;
import com.jweb.dao.constant.DatabaseConstant.MqMsgChannel;
import com.jweb.dao.entity.MqMessage;
import com.jweb.dao.entity.MqMessageLog;
import com.jweb.mq.controller.vo.MqMessageLogResult;
import com.jweb.mq.controller.vo.MqMessageResult;
import com.jweb.mq.message.MessageQueue;
import com.jweb.mq.message.MessageRouter;
import com.jweb.mq.message.MessageTopic;
import com.jweb.mq.message.MessageType;
import com.jweb.mq.service.MqService;
import com.jweb.watchdog.aspect.resubmit.NoResubmit;

import lombok.extern.slf4j.Slf4j;
/**
 * 
 * @author 邓超
 *
 * 2022/6/4 12:24
 */
@Slf4j
@RestController
public class MqController extends BaseController implements MqApiDoc{
	
	@Autowired
	private MessageRouter messageRouter;
	@Autowired
	private MqService mqService;

	@NoResubmit(timeInterval = 3, submittable = true)
	@Override
	public ApiResult publishQueueMessage(MessageType bizType, String content, String label) {
		ApiResult result = new ApiResult();
		try {
			messageRouter.produceRouting(bizType, content, label);
		} catch (MqException e) {
			log.error(e.getMessage(), e);
			result.bizFail(e);
		}

		return result;
	}

	@NoResubmit(timeInterval = 3, submittable = true)
	@Override
	public ApiResult publishTopicMessage(MessageTopic topic, String content) {
		
		ApiResult result = new ApiResult();

		try {
			messageRouter.produceRouting(topic, content);
		} catch (MqException e) {
			log.error(e.getMessage(), e);
			result.bizFail(e);
		}
		
		return result;
	}

	@NoResubmit(timeInterval = 3, submittable = true)
	@Override
	public ApiResult publishRetryMessage(String msgIds) {
		ApiResult result = new ApiResult();

		try {
			mqService.publishRetryMessage(msgIds);
		} catch (MqException e) {
			log.error(e.getMessage(), e);
			result.bizFail(e);
		}
		
		return result;
	}

	@NoResubmit(timeInterval = 3, submittable = true)
	@Override
	public ApiResult deleteMessage(String msgIds) {
		ApiResult result = new ApiResult();

		try {
			mqService.deleteMessage(msgIds);
		} catch (MqException e) {
			log.error(e.getMessage(), e);
			result.bizFail(e);
		}
		
		return result;
	}

	@NoResubmit(timeInterval = 3, submittable = true)
	@Override
	public MqMessageResult messageList(MqMsgChannel channel, MessageTopic topic, MessageQueue queue, MessageType bizType,
			Integer status, String minPublishTime, String maxPublishTime, String content, int page, int pageSize) {
		MqMessageResult result = new MqMessageResult();
		MqMessage query = new MqMessage();
		query.setStatus(status);
		query.andLike("content", content);
		query.sort("publish_time", Sort.DESC);
		query.page(page, pageSize);
		if(isNotNull(channel)){
			query.setChannel(channel.getValue());
		}
		if(isNotNull(topic)){
			query.setTopicName(topic.getName());
		}
		if(isNotNull(queue)){
			query.setQueueName(queue.getName());
		}
		if(isNotNull(bizType)){
			query.setBizType(bizType.getName());
		}

		try {
			Long minTime = DateTimeUtil.stringToTime(minPublishTime, Format.YYYYMMDDHHMMSS_1);
			Long maxTime = DateTimeUtil.stringToTime(maxPublishTime, Format.YYYYMMDDHHMMSS_1);
			query.andGE("publish_time", minTime);
			query.andLE("publish_time", maxTime);
		} catch (MyException e) {
			log.error("", e);
		}
		
		try {
			PageResult<MqMessage> pageResult = mqService.getMqMessageList(query);
			result.setData(pageResult);
		} catch (MqException e) {
			log.error(e.getMessage(), e);
			result.bizFail(e);
		}
		
		return result;
	}

	@NoResubmit(timeInterval = 3, submittable = true)
	@Override
	public MqMessageLogResult messageLogList(Long messageId) {
		MqMessageLogResult result = new MqMessageLogResult();
		try {
			List<MqMessageLog> msglogs = mqService.getMqMessageLog(messageId);
			PageResult<MqMessageLog> pageResult = new PageResult<MqMessageLog>();
			pageResult.setEntitys(msglogs);
			if(isNotNull(msglogs)) {
				pageResult.setTotalCount(msglogs.size());
			}
			result.setData(pageResult);
		} catch (MqException e) {
			log.error(e.getMessage(), e);
			result.bizFail(e);
		}
		return result;
	}

}
