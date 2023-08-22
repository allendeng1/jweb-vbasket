package com.jweb.service.impl;

import com.jweb.common.exception.MyException;
import com.jweb.dao.base.PageResult;
import com.jweb.dao.ApiLogDao;
import com.jweb.service.ApiLogService;
import com.jweb.common.util.DataUtil;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import lombok.extern.slf4j.Slf4j;
import com.jweb.dao.entity.ApiLog;

/**
 *
 * @author 邓超
 *
 * 2023/08/22 11:27
*/
@Slf4j
@Service
public class ApiLogServiceImpl extends DataUtil implements ApiLogService {

  @Autowired
  private ApiLogDao apiLogDao;

  @Override
  public ApiLog saveEntity(ApiLog entity) throws MyException {
    apiLogDao.insert(entity);
    return entity;
  }

  @Override
  public ApiLog getById(long id) throws MyException {
    return apiLogDao.selectById(id);
  }

  @Override
  public ApiLog getOneByCondition(ApiLog entity) throws MyException {
    return apiLogDao.selectOneByExample(entity);
  }

  @Override
  public List<ApiLog> getListByCondition(ApiLog entity) throws MyException {
    return apiLogDao.selectByExample(entity);
  }

  @Override
  public PageResult<ApiLog> getPageListByCondition(ApiLog entity) throws MyException {
    return apiLogDao.selectPageResultByExample(entity);
  }

  @Override
  public void deleteById(long id) throws MyException {
    apiLogDao.deleteById(id);
  }

}
