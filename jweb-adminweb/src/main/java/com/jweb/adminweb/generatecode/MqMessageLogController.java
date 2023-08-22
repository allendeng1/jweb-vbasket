package com.jweb.adminweb.generatecode;

import com.jweb.common.api.ApiResult;
import com.jweb.dao.base.PageResult;
import com.jweb.common.api.BaseController;
import com.jweb.common.exception.MyException;
import com.jweb.dao.entity.MqMessageLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import com.jweb.adminweb.generatecode.vo.MqMessageLogResult;
import com.jweb.adminweb.generatecode.MqMessageLogApiDoc;
import com.jweb.service.MqMessageLogService;
/**
 *
 * @author 邓超
 *
 * 2023/08/22 15:26
*/
@RestController
public class MqMessageLogController extends BaseController implements MqMessageLogApiDoc {

  @Autowired
  private MqMessageLogService mqMessageLogService;

  @Override
  public ApiResult addMqMessageLog(
    Long messageId,
    String type,
    String excuteMethod,
    String excuteHandler,
    Long startTime,
    Long endTime,
    String resultDesc,
    Long ctdate,
    Long mddate) {
    ApiResult result = new ApiResult();
    try {
      MqMessageLog entity = new MqMessageLog();
      entity.setMessageId(messageId);
      entity.setType(type);
      entity.setExcuteMethod(excuteMethod);
      entity.setExcuteHandler(excuteHandler);
      entity.setStartTime(startTime);
      entity.setEndTime(endTime);
      entity.setResultDesc(resultDesc);
      entity.setCtdate(ctdate);
      entity.setMddate(mddate);
      mqMessageLogService.saveEntity(entity);
    } catch (MyException e) {
      result.bizFail(e);
    }
    return result;
  }

  @Override
  public ApiResult editMqMessageLog(
    Long id,
    Long messageId,
    String type,
    String excuteMethod,
    String excuteHandler,
    Long startTime,
    Long endTime,
    String resultDesc,
    Long ctdate,
    Long mddate) {
    ApiResult result = new ApiResult();
    try {
      MqMessageLog entity = new MqMessageLog();
      entity.setId(id);
      entity.setMessageId(messageId);
      entity.setType(type);
      entity.setExcuteMethod(excuteMethod);
      entity.setExcuteHandler(excuteHandler);
      entity.setStartTime(startTime);
      entity.setEndTime(endTime);
      entity.setResultDesc(resultDesc);
      entity.setCtdate(ctdate);
      entity.setMddate(mddate);
      mqMessageLogService.saveEntity(entity);
    } catch (MyException e) {
      result.bizFail(e);
    }
    return result;
  }

  @Override
  public MqMessageLogResult getMqMessageLogList(
    Long messageId,
    String type,
    String excuteMethod,
    String excuteHandler,
    Long startTime,
    Long endTime,
    String resultDesc,
    Long ctdate,
    Long mddate,
    Integer page,
    Integer limit) {
    MqMessageLogResult result = new MqMessageLogResult();
    try {
      MqMessageLog entity = new MqMessageLog();
      entity.setMessageId(messageId);
      entity.setType(type);
      entity.setExcuteMethod(excuteMethod);
      entity.setExcuteHandler(excuteHandler);
      entity.setStartTime(startTime);
      entity.setEndTime(endTime);
      entity.setResultDesc(resultDesc);
      entity.setCtdate(ctdate);
      entity.setMddate(mddate);
      entity.page(page, limit);
      PageResult<MqMessageLog> pageData = mqMessageLogService.getPageListByCondition(entity);
      result.setData(pageData);
    } catch (MyException e) {
      result.bizFail(e);
    }
    return result;
  }

  @Override
  public ApiResult getMqMessageLog(Long id) {

    ApiResult result = new ApiResult();
    try {
      MqMessageLog data = mqMessageLogService.getById(id);
      result.setData(data);
    } catch (MyException e) {
      result.bizFail(e);
    }
    return result;
  }
}
