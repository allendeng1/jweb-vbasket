package com.jweb.service.impl;

import com.jweb.common.exception.MyException;
import com.jweb.dao.base.PageResult;
import com.jweb.dao.JobRunLogDao;
import com.jweb.service.JobRunLogService;
import com.jweb.common.util.DataUtil;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import lombok.extern.slf4j.Slf4j;
import com.jweb.dao.entity.JobRunLog;

/**
 *
 * @author 邓超
 *
 * 2023/08/22 14:04
*/
@Slf4j
@Service
public class JobRunLogServiceImpl extends DataUtil implements JobRunLogService {

  @Autowired
  private JobRunLogDao jobRunLogDao;

  @Override
  public JobRunLog saveEntity(JobRunLog entity) throws MyException {
    jobRunLogDao.insert(entity);
    return entity;
  }

  @Override
  public JobRunLog getById(long id) throws MyException {
    return jobRunLogDao.selectById(id);
  }

  @Override
  public JobRunLog getOneByCondition(JobRunLog entity) throws MyException {
    return jobRunLogDao.selectOneByExample(entity);
  }

  @Override
  public List<JobRunLog> getListByCondition(JobRunLog entity) throws MyException {
    return jobRunLogDao.selectByExample(entity);
  }

  @Override
  public PageResult<JobRunLog> getPageListByCondition(JobRunLog entity) throws MyException {
    return jobRunLogDao.selectPageResultByExample(entity);
  }

  @Override
  public void deleteById(long id) throws MyException {
    jobRunLogDao.deleteById(id);
  }

}
