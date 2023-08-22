package com.jweb.adminweb.generatecode;

import com.jweb.common.api.ApiResult;
import com.jweb.dao.base.PageResult;
import com.jweb.common.api.BaseController;
import com.jweb.common.exception.MyException;
import com.jweb.dao.entity.MqMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import com.jweb.adminweb.generatecode.vo.MqMessageResult;
import com.jweb.adminweb.generatecode.MqMessageApiDoc;
import com.jweb.service.MqMessageService;
/**
 *
 * @author 邓超
 *
 * 2023/08/22 15:26
*/
@RestController
public class MqMessageController extends BaseController implements MqMessageApiDoc {

  @Autowired
  private MqMessageService mqMessageService;

  @Override
  public ApiResult addMqMessage(
    String channel,
    String topicName,
    String queueName,
    String bizType,
    Integer status,
    Long publishTime,
    String content,
    Boolean isDelete,
    Long deleteTime,
    Long ctdate,
    Long mddate) {
    ApiResult result = new ApiResult();
    try {
      MqMessage entity = new MqMessage();
      entity.setChannel(channel);
      entity.setTopicName(topicName);
      entity.setQueueName(queueName);
      entity.setBizType(bizType);
      entity.setStatus(status);
      entity.setPublishTime(publishTime);
      entity.setContent(content);
      entity.setIsDelete(isDelete);
      entity.setDeleteTime(deleteTime);
      entity.setCtdate(ctdate);
      entity.setMddate(mddate);
      mqMessageService.saveEntity(entity);
    } catch (MyException e) {
      result.bizFail(e);
    }
    return result;
  }

  @Override
  public ApiResult editMqMessage(
    Long id,
    String channel,
    String topicName,
    String queueName,
    String bizType,
    Integer status,
    Long publishTime,
    String content,
    Boolean isDelete,
    Long deleteTime,
    Long ctdate,
    Long mddate) {
    ApiResult result = new ApiResult();
    try {
      MqMessage entity = new MqMessage();
      entity.setId(id);
      entity.setChannel(channel);
      entity.setTopicName(topicName);
      entity.setQueueName(queueName);
      entity.setBizType(bizType);
      entity.setStatus(status);
      entity.setPublishTime(publishTime);
      entity.setContent(content);
      entity.setIsDelete(isDelete);
      entity.setDeleteTime(deleteTime);
      entity.setCtdate(ctdate);
      entity.setMddate(mddate);
      mqMessageService.saveEntity(entity);
    } catch (MyException e) {
      result.bizFail(e);
    }
    return result;
  }

  @Override
  public MqMessageResult getMqMessageList(
    String channel,
    String topicName,
    String queueName,
    String bizType,
    Integer status,
    Long publishTime,
    String content,
    Boolean isDelete,
    Long deleteTime,
    Long ctdate,
    Long mddate,
    Integer page,
    Integer limit) {
    MqMessageResult result = new MqMessageResult();
    try {
      MqMessage entity = new MqMessage();
      entity.setChannel(channel);
      entity.setTopicName(topicName);
      entity.setQueueName(queueName);
      entity.setBizType(bizType);
      entity.setStatus(status);
      entity.setPublishTime(publishTime);
      entity.setContent(content);
      entity.setIsDelete(isDelete);
      entity.setDeleteTime(deleteTime);
      entity.setCtdate(ctdate);
      entity.setMddate(mddate);
      entity.page(page, limit);
      PageResult<MqMessage> pageData = mqMessageService.getPageListByCondition(entity);
      result.setData(pageData);
    } catch (MyException e) {
      result.bizFail(e);
    }
    return result;
  }

  @Override
  public ApiResult getMqMessage(Long id) {

    ApiResult result = new ApiResult();
    try {
      MqMessage data = mqMessageService.getById(id);
      result.setData(data);
    } catch (MyException e) {
      result.bizFail(e);
    }
    return result;
  }
}
