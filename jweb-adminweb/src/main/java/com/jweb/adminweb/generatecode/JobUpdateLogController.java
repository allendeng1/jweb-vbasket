package com.jweb.adminweb.generatecode;

import com.jweb.common.api.ApiResult;
import com.jweb.dao.base.PageResult;
import com.jweb.common.api.BaseController;
import com.jweb.common.exception.MyException;
import com.jweb.dao.entity.JobUpdateLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import com.jweb.adminweb.generatecode.vo.JobUpdateLogResult;
import com.jweb.adminweb.generatecode.JobUpdateLogApiDoc;
import com.jweb.service.JobUpdateLogService;
/**
 *
 * @author 邓超
 *
 * 2023/08/22 14:43
*/
@RestController
public class JobUpdateLogController extends BaseController implements JobUpdateLogApiDoc {

  @Autowired
  private JobUpdateLogService jobUpdateLogService;

  @Override
  public ApiResult addJobUpdateLog(
    Long taskId,
    String taskNo,
    String taskName,
    Integer type,
    Long adminUserId,
    String remark,
    Long ctdate,
    Long mddate) {
    ApiResult result = new ApiResult();
    try {
      JobUpdateLog entity = new JobUpdateLog();
      entity.setTaskId(taskId);
      entity.setTaskNo(taskNo);
      entity.setTaskName(taskName);
      entity.setType(type);
      entity.setAdminUserId(adminUserId);
      entity.setRemark(remark);
      entity.setCtdate(ctdate);
      entity.setMddate(mddate);
      jobUpdateLogService.saveEntity(entity);
    } catch (MyException e) {
      result.bizFail(e);
    }
    return result;
  }

  @Override
  public ApiResult editJobUpdateLog(
    Long id,
    Long taskId,
    String taskNo,
    String taskName,
    Integer type,
    Long adminUserId,
    String remark,
    Long ctdate,
    Long mddate) {
    ApiResult result = new ApiResult();
    try {
      JobUpdateLog entity = new JobUpdateLog();
      entity.setId(id);
      entity.setTaskId(taskId);
      entity.setTaskNo(taskNo);
      entity.setTaskName(taskName);
      entity.setType(type);
      entity.setAdminUserId(adminUserId);
      entity.setRemark(remark);
      entity.setCtdate(ctdate);
      entity.setMddate(mddate);
      jobUpdateLogService.saveEntity(entity);
    } catch (MyException e) {
      result.bizFail(e);
    }
    return result;
  }

  @Override
  public JobUpdateLogResult getJobUpdateLogList(
    Long taskId,
    String taskNo,
    String taskName,
    Integer type,
    Long adminUserId,
    String remark,
    Long ctdate,
    Long mddate,
    Integer page,
    Integer limit) {
    JobUpdateLogResult result = new JobUpdateLogResult();
    try {
      JobUpdateLog entity = new JobUpdateLog();
      entity.setTaskId(taskId);
      entity.setTaskNo(taskNo);
      entity.setTaskName(taskName);
      entity.setType(type);
      entity.setAdminUserId(adminUserId);
      entity.setRemark(remark);
      entity.setCtdate(ctdate);
      entity.setMddate(mddate);
      entity.page(page, limit);
      PageResult<JobUpdateLog> pageData = jobUpdateLogService.getPageListByCondition(entity);
      result.setData(pageData);
    } catch (MyException e) {
      result.bizFail(e);
    }
    return result;
  }

  @Override
  public ApiResult getJobUpdateLog(Long id) {

    ApiResult result = new ApiResult();
    try {
      JobUpdateLog data = jobUpdateLogService.getById(id);
      result.setData(data);
    } catch (MyException e) {
      result.bizFail(e);
    }
    return result;
  }
}
