package com.jweb.adminweb.generatecode;

import com.jweb.common.api.ApiResult;
import com.jweb.dao.base.PageResult;
import com.jweb.common.api.BaseController;
import com.jweb.common.exception.MyException;
import com.jweb.dao.entity.ApiLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import com.jweb.adminweb.generatecode.vo.ApiLogResult;
import com.jweb.adminweb.generatecode.ApiLogApiDoc;
import com.jweb.service.ApiLogService;
/**
 *
 * @author 邓超
 *
 * 2023/08/22 14:43
*/
@RestController
public class ApiLogController extends BaseController implements ApiLogApiDoc {

  @Autowired
  private ApiLogService apiLogService;

  @Override
  public ApiResult addApiLog(
    String serverContextPath,
    String ipAddress,
    Long userId,
    String reqUrl,
    String reqMethod,
    Long beginTime,
    Long endTime,
    Long costTime,
    Boolean isAttack,
    String reqParam,
    String controllerName,
    String controllerMethod,
    String resultData,
    Long ctdate,
    Long mddate) {
    ApiResult result = new ApiResult();
    try {
      ApiLog entity = new ApiLog();
      entity.setServerContextPath(serverContextPath);
      entity.setIpAddress(ipAddress);
      entity.setUserId(userId);
      entity.setReqUrl(reqUrl);
      entity.setReqMethod(reqMethod);
      entity.setBeginTime(beginTime);
      entity.setEndTime(endTime);
      entity.setCostTime(costTime);
      entity.setIsAttack(isAttack);
      entity.setReqParam(reqParam);
      entity.setControllerName(controllerName);
      entity.setControllerMethod(controllerMethod);
      entity.setResultData(resultData);
      entity.setCtdate(ctdate);
      entity.setMddate(mddate);
      apiLogService.saveEntity(entity);
    } catch (MyException e) {
      result.bizFail(e);
    }
    return result;
  }

  @Override
  public ApiResult editApiLog(
    Long id,
    String serverContextPath,
    String ipAddress,
    Long userId,
    String reqUrl,
    String reqMethod,
    Long beginTime,
    Long endTime,
    Long costTime,
    Boolean isAttack,
    String reqParam,
    String controllerName,
    String controllerMethod,
    String resultData,
    Long ctdate,
    Long mddate) {
    ApiResult result = new ApiResult();
    try {
      ApiLog entity = new ApiLog();
      entity.setId(id);
      entity.setServerContextPath(serverContextPath);
      entity.setIpAddress(ipAddress);
      entity.setUserId(userId);
      entity.setReqUrl(reqUrl);
      entity.setReqMethod(reqMethod);
      entity.setBeginTime(beginTime);
      entity.setEndTime(endTime);
      entity.setCostTime(costTime);
      entity.setIsAttack(isAttack);
      entity.setReqParam(reqParam);
      entity.setControllerName(controllerName);
      entity.setControllerMethod(controllerMethod);
      entity.setResultData(resultData);
      entity.setCtdate(ctdate);
      entity.setMddate(mddate);
      apiLogService.saveEntity(entity);
    } catch (MyException e) {
      result.bizFail(e);
    }
    return result;
  }

  @Override
  public ApiLogResult getApiLogList(
    String serverContextPath,
    String ipAddress,
    Long userId,
    String reqUrl,
    String reqMethod,
    Long beginTime,
    Long endTime,
    Long costTime,
    Boolean isAttack,
    String reqParam,
    String controllerName,
    String controllerMethod,
    String resultData,
    Long ctdate,
    Long mddate,
    Integer page,
    Integer limit) {
    ApiLogResult result = new ApiLogResult();
    try {
      ApiLog entity = new ApiLog();
      entity.setServerContextPath(serverContextPath);
      entity.setIpAddress(ipAddress);
      entity.setUserId(userId);
      entity.setReqUrl(reqUrl);
      entity.setReqMethod(reqMethod);
      entity.setBeginTime(beginTime);
      entity.setEndTime(endTime);
      entity.setCostTime(costTime);
      entity.setIsAttack(isAttack);
      entity.setReqParam(reqParam);
      entity.setControllerName(controllerName);
      entity.setControllerMethod(controllerMethod);
      entity.setResultData(resultData);
      entity.setCtdate(ctdate);
      entity.setMddate(mddate);
      entity.page(page, limit);
      PageResult<ApiLog> pageData = apiLogService.getPageListByCondition(entity);
      result.setData(pageData);
    } catch (MyException e) {
      result.bizFail(e);
    }
    return result;
  }

  @Override
  public ApiResult getApiLog(Long id) {

    ApiResult result = new ApiResult();
    try {
      ApiLog data = apiLogService.getById(id);
      result.setData(data);
    } catch (MyException e) {
      result.bizFail(e);
    }
    return result;
  }
}
