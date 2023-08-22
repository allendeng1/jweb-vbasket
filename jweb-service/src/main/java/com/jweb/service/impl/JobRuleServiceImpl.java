package com.jweb.service.impl;

import com.jweb.common.exception.MyException;
import com.jweb.dao.base.PageResult;
import com.jweb.dao.JobRuleDao;
import com.jweb.service.JobRuleService;
import com.jweb.common.util.DataUtil;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import lombok.extern.slf4j.Slf4j;
import com.jweb.dao.entity.JobRule;

/**
 *
 * @author 邓超
 *
 * 2023/08/22 14:04
*/
@Slf4j
@Service
public class JobRuleServiceImpl extends DataUtil implements JobRuleService {

  @Autowired
  private JobRuleDao jobRuleDao;

  @Override
  public JobRule saveEntity(JobRule entity) throws MyException {
    jobRuleDao.insert(entity);
    return entity;
  }

  @Override
  public JobRule getById(long id) throws MyException {
    return jobRuleDao.selectById(id);
  }

  @Override
  public JobRule getOneByCondition(JobRule entity) throws MyException {
    return jobRuleDao.selectOneByExample(entity);
  }

  @Override
  public List<JobRule> getListByCondition(JobRule entity) throws MyException {
    return jobRuleDao.selectByExample(entity);
  }

  @Override
  public PageResult<JobRule> getPageListByCondition(JobRule entity) throws MyException {
    return jobRuleDao.selectPageResultByExample(entity);
  }

  @Override
  public void deleteById(long id) throws MyException {
    jobRuleDao.deleteById(id);
  }

}
