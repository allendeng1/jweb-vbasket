package com.jweb.adminweb.generatecode;

import com.jweb.common.api.ApiResult;
import com.jweb.dao.base.PageResult;
import com.jweb.common.api.BaseController;
import com.jweb.common.exception.MyException;
import com.jweb.dao.entity.JobRunLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import com.jweb.adminweb.generatecode.vo.JobRunLogResult;
import com.jweb.adminweb.generatecode.JobRunLogApiDoc;
import com.jweb.service.JobRunLogService;
/**
 *
 * @author 邓超
 *
 * 2023/08/22 14:43
*/
@RestController
public class JobRunLogController extends BaseController implements JobRunLogApiDoc {

  @Autowired
  private JobRunLogService jobRunLogService;

  @Override
  public ApiResult addJobRunLog(
    String runNo,
    Long taskId,
    String taskNo,
    String taskName,
    Integer status,
    Integer runResult,
    Long startDate,
    Long endDate,
    String remark,
    Long ctdate,
    Long mddate) {
    ApiResult result = new ApiResult();
    try {
      JobRunLog entity = new JobRunLog();
      entity.setRunNo(runNo);
      entity.setTaskId(taskId);
      entity.setTaskNo(taskNo);
      entity.setTaskName(taskName);
      entity.setStatus(status);
      entity.setRunResult(runResult);
      entity.setStartDate(startDate);
      entity.setEndDate(endDate);
      entity.setRemark(remark);
      entity.setCtdate(ctdate);
      entity.setMddate(mddate);
      jobRunLogService.saveEntity(entity);
    } catch (MyException e) {
      result.bizFail(e);
    }
    return result;
  }

  @Override
  public ApiResult editJobRunLog(
    Long id,
    String runNo,
    Long taskId,
    String taskNo,
    String taskName,
    Integer status,
    Integer runResult,
    Long startDate,
    Long endDate,
    String remark,
    Long ctdate,
    Long mddate) {
    ApiResult result = new ApiResult();
    try {
      JobRunLog entity = new JobRunLog();
      entity.setId(id);
      entity.setRunNo(runNo);
      entity.setTaskId(taskId);
      entity.setTaskNo(taskNo);
      entity.setTaskName(taskName);
      entity.setStatus(status);
      entity.setRunResult(runResult);
      entity.setStartDate(startDate);
      entity.setEndDate(endDate);
      entity.setRemark(remark);
      entity.setCtdate(ctdate);
      entity.setMddate(mddate);
      jobRunLogService.saveEntity(entity);
    } catch (MyException e) {
      result.bizFail(e);
    }
    return result;
  }

  @Override
  public JobRunLogResult getJobRunLogList(
    String runNo,
    Long taskId,
    String taskNo,
    String taskName,
    Integer status,
    Integer runResult,
    Long startDate,
    Long endDate,
    String remark,
    Long ctdate,
    Long mddate,
    Integer page,
    Integer limit) {
    JobRunLogResult result = new JobRunLogResult();
    try {
      JobRunLog entity = new JobRunLog();
      entity.setRunNo(runNo);
      entity.setTaskId(taskId);
      entity.setTaskNo(taskNo);
      entity.setTaskName(taskName);
      entity.setStatus(status);
      entity.setRunResult(runResult);
      entity.setStartDate(startDate);
      entity.setEndDate(endDate);
      entity.setRemark(remark);
      entity.setCtdate(ctdate);
      entity.setMddate(mddate);
      entity.page(page, limit);
      PageResult<JobRunLog> pageData = jobRunLogService.getPageListByCondition(entity);
      result.setData(pageData);
    } catch (MyException e) {
      result.bizFail(e);
    }
    return result;
  }

  @Override
  public ApiResult getJobRunLog(Long id) {

    ApiResult result = new ApiResult();
    try {
      JobRunLog data = jobRunLogService.getById(id);
      result.setData(data);
    } catch (MyException e) {
      result.bizFail(e);
    }
    return result;
  }
}
