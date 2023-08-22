package com.jweb.adminweb.generatecode;

import com.jweb.common.api.ApiResult;
import com.jweb.dao.base.PageResult;
import com.jweb.common.api.BaseController;
import com.jweb.common.exception.MyException;
import com.jweb.dao.entity.MessageRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import com.jweb.adminweb.generatecode.vo.MessageRecordResult;
import com.jweb.adminweb.generatecode.MessageRecordApiDoc;
import com.jweb.service.MessageRecordService;
/**
 *
 * @author 邓超
 *
 * 2023/08/22 14:43
*/
@RestController
public class MessageRecordController extends BaseController implements MessageRecordApiDoc {

  @Autowired
  private MessageRecordService messageRecordService;

  @Override
  public ApiResult addMessageRecord(
    String code,
    String sentChannel,
    Integer status,
    Long sender,
    String receiver,
    Integer sentWay,
    Long timingTime,
    String content,
    Long sentTime,
    String sentResult,
    String templateCode,
    String reference,
    Long callbackTime,
    String callbackResult,
    Boolean isDelete,
    Long ctdate,
    Long mddate) {
    ApiResult result = new ApiResult();
    try {
      MessageRecord entity = new MessageRecord();
      entity.setCode(code);
      entity.setSentChannel(sentChannel);
      entity.setStatus(status);
      entity.setSender(sender);
      entity.setReceiver(receiver);
      entity.setSentWay(sentWay);
      entity.setTimingTime(timingTime);
      entity.setContent(content);
      entity.setSentTime(sentTime);
      entity.setSentResult(sentResult);
      entity.setTemplateCode(templateCode);
      entity.setReference(reference);
      entity.setCallbackTime(callbackTime);
      entity.setCallbackResult(callbackResult);
      entity.setIsDelete(isDelete);
      entity.setCtdate(ctdate);
      entity.setMddate(mddate);
      messageRecordService.saveEntity(entity);
    } catch (MyException e) {
      result.bizFail(e);
    }
    return result;
  }

  @Override
  public ApiResult editMessageRecord(
    Long id,
    String code,
    String sentChannel,
    Integer status,
    Long sender,
    String receiver,
    Integer sentWay,
    Long timingTime,
    String content,
    Long sentTime,
    String sentResult,
    String templateCode,
    String reference,
    Long callbackTime,
    String callbackResult,
    Boolean isDelete,
    Long ctdate,
    Long mddate) {
    ApiResult result = new ApiResult();
    try {
      MessageRecord entity = new MessageRecord();
      entity.setId(id);
      entity.setCode(code);
      entity.setSentChannel(sentChannel);
      entity.setStatus(status);
      entity.setSender(sender);
      entity.setReceiver(receiver);
      entity.setSentWay(sentWay);
      entity.setTimingTime(timingTime);
      entity.setContent(content);
      entity.setSentTime(sentTime);
      entity.setSentResult(sentResult);
      entity.setTemplateCode(templateCode);
      entity.setReference(reference);
      entity.setCallbackTime(callbackTime);
      entity.setCallbackResult(callbackResult);
      entity.setIsDelete(isDelete);
      entity.setCtdate(ctdate);
      entity.setMddate(mddate);
      messageRecordService.saveEntity(entity);
    } catch (MyException e) {
      result.bizFail(e);
    }
    return result;
  }

  @Override
  public MessageRecordResult getMessageRecordList(
    String code,
    String sentChannel,
    Integer status,
    Long sender,
    String receiver,
    Integer sentWay,
    Long timingTime,
    String content,
    Long sentTime,
    String sentResult,
    String templateCode,
    String reference,
    Long callbackTime,
    String callbackResult,
    Boolean isDelete,
    Long ctdate,
    Long mddate,
    Integer page,
    Integer limit) {
    MessageRecordResult result = new MessageRecordResult();
    try {
      MessageRecord entity = new MessageRecord();
      entity.setCode(code);
      entity.setSentChannel(sentChannel);
      entity.setStatus(status);
      entity.setSender(sender);
      entity.setReceiver(receiver);
      entity.setSentWay(sentWay);
      entity.setTimingTime(timingTime);
      entity.setContent(content);
      entity.setSentTime(sentTime);
      entity.setSentResult(sentResult);
      entity.setTemplateCode(templateCode);
      entity.setReference(reference);
      entity.setCallbackTime(callbackTime);
      entity.setCallbackResult(callbackResult);
      entity.setIsDelete(isDelete);
      entity.setCtdate(ctdate);
      entity.setMddate(mddate);
      entity.page(page, limit);
      PageResult<MessageRecord> pageData = messageRecordService.getPageListByCondition(entity);
      result.setData(pageData);
    } catch (MyException e) {
      result.bizFail(e);
    }
    return result;
  }

  @Override
  public ApiResult getMessageRecord(Long id) {

    ApiResult result = new ApiResult();
    try {
      MessageRecord data = messageRecordService.getById(id);
      result.setData(data);
    } catch (MyException e) {
      result.bizFail(e);
    }
    return result;
  }
}
