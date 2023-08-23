package com.jweb.adminweb.generatecode;

import com.jweb.common.api.ApiResult;
import com.jweb.dao.base.PageResult;
import com.jweb.common.api.BaseController;
import com.jweb.common.exception.MyException;
import com.jweb.dao.entity.JobRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import com.jweb.adminweb.generatecode.vo.JobRuleResult;
import com.jweb.adminweb.generatecode.JobRuleApiDoc;
import com.jweb.service.JobRuleService;
/**
 *
 * @author 邓超
 *
 * 2023/08/22 18:02
*/
@RestController
public class JobRuleController extends BaseController implements JobRuleApiDoc {

  @Autowired
  private JobRuleService jobRuleService;

  @Override
  public ApiResult addJobRule(
    String taskNo,
    String taskName,
    Integer type,
    String taskCron,
    Integer status,
    Integer runStatus,
    String handlerName,
    String taskContent,
    String remark,
    Long ctdate,
    Long mddate) {
    ApiResult result = new ApiResult();
    try {
      JobRule entity = new JobRule();
      entity.setTaskNo(taskNo);
      entity.setTaskName(taskName);
      entity.setType(type);
      entity.setTaskCron(taskCron);
      entity.setStatus(status);
      entity.setRunStatus(runStatus);
      entity.setHandlerName(handlerName);
      entity.setTaskContent(taskContent);
      entity.setRemark(remark);
      entity.setCtdate(ctdate);
      entity.setMddate(mddate);
      jobRuleService.saveEntity(entity);
    } catch (MyException e) {
      result.bizFail(e);
    }
    return result;
  }

  @Override
  public ApiResult editJobRule(
    Long id,
    String taskNo,
    String taskName,
    Integer type,
    String taskCron,
    Integer status,
    Integer runStatus,
    String handlerName,
    String taskContent,
    String remark,
    Long ctdate,
    Long mddate) {
    ApiResult result = new ApiResult();
    try {
      JobRule entity = new JobRule();
      entity.setId(id);
      entity.setTaskNo(taskNo);
      entity.setTaskName(taskName);
      entity.setType(type);
      entity.setTaskCron(taskCron);
      entity.setStatus(status);
      entity.setRunStatus(runStatus);
      entity.setHandlerName(handlerName);
      entity.setTaskContent(taskContent);
      entity.setRemark(remark);
      entity.setCtdate(ctdate);
      entity.setMddate(mddate);
      jobRuleService.saveEntity(entity);
    } catch (MyException e) {
      result.bizFail(e);
    }
    return result;
  }

  @Override
  public JobRuleResult getJobRuleList(
    String taskNo,
    String taskName,
    Integer type,
    String taskCron,
    Integer status,
    Integer runStatus,
    String handlerName,
    String taskContent,
    String remark,
    Long ctdate,
    Long mddate,
    Integer page,
    Integer limit) {
    JobRuleResult result = new JobRuleResult();
    try {
      JobRule entity = new JobRule();
      entity.setTaskNo(taskNo);
      entity.setTaskName(taskName);
      entity.setType(type);
      entity.setTaskCron(taskCron);
      entity.setStatus(status);
      entity.setRunStatus(runStatus);
      entity.setHandlerName(handlerName);
      entity.setTaskContent(taskContent);
      entity.setRemark(remark);
      entity.setCtdate(ctdate);
      entity.setMddate(mddate);
      entity.page(page, limit);
      PageResult<JobRule> pageData = jobRuleService.getPageListByCondition(entity);
      result.setData(pageData);
    } catch (MyException e) {
      result.bizFail(e);
    }
    return result;
  }

  @Override
  public ApiResult getJobRule(Long id) {

    ApiResult result = new ApiResult();
    try {
      JobRule data = jobRuleService.getById(id);
      result.setData(data);
    } catch (MyException e) {
      result.bizFail(e);
    }
    return result;
  }
}
