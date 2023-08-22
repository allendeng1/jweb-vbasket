package com.jweb.service.impl;

import com.jweb.common.exception.MyException;
import com.jweb.dao.base.PageResult;
import com.jweb.dao.JobUpdateLogDao;
import com.jweb.service.JobUpdateLogService;
import com.jweb.common.util.DataUtil;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import lombok.extern.slf4j.Slf4j;
import com.jweb.dao.entity.JobUpdateLog;

/**
 *
 * @author 邓超
 *
 * 2023/08/22 14:05
*/
@Slf4j
@Service
public class JobUpdateLogServiceImpl extends DataUtil implements JobUpdateLogService {

  @Autowired
  private JobUpdateLogDao jobUpdateLogDao;

  @Override
  public JobUpdateLog saveEntity(JobUpdateLog entity) throws MyException {
    jobUpdateLogDao.insert(entity);
    return entity;
  }

  @Override
  public JobUpdateLog getById(long id) throws MyException {
    return jobUpdateLogDao.selectById(id);
  }

  @Override
  public JobUpdateLog getOneByCondition(JobUpdateLog entity) throws MyException {
    return jobUpdateLogDao.selectOneByExample(entity);
  }

  @Override
  public List<JobUpdateLog> getListByCondition(JobUpdateLog entity) throws MyException {
    return jobUpdateLogDao.selectByExample(entity);
  }

  @Override
  public PageResult<JobUpdateLog> getPageListByCondition(JobUpdateLog entity) throws MyException {
    return jobUpdateLogDao.selectPageResultByExample(entity);
  }

  @Override
  public void deleteById(long id) throws MyException {
    jobUpdateLogDao.deleteById(id);
  }

}
