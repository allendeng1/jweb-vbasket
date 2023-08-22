package com.jweb.service.impl;

import com.jweb.common.exception.MyException;
import com.jweb.dao.base.PageResult;
import com.jweb.dao.MqMessageLogDao;
import com.jweb.service.MqMessageLogService;
import com.jweb.common.util.DataUtil;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import lombok.extern.slf4j.Slf4j;
import com.jweb.dao.entity.MqMessageLog;

/**
 *
 * @author 邓超
 *
 * 2023/08/22 15:26
*/
@Slf4j
@Service
public class MqMessageLogServiceImpl extends DataUtil implements MqMessageLogService {

  @Autowired
  private MqMessageLogDao mqMessageLogDao;

  @Override
  public MqMessageLog saveEntity(MqMessageLog entity) throws MyException {
    mqMessageLogDao.insert(entity);
    return entity;
  }

  @Override
  public MqMessageLog getById(long id) throws MyException {
    return mqMessageLogDao.selectById(id);
  }

  @Override
  public MqMessageLog getOneByCondition(MqMessageLog entity) throws MyException {
    return mqMessageLogDao.selectOneByExample(entity);
  }

  @Override
  public List<MqMessageLog> getListByCondition(MqMessageLog entity) throws MyException {
    return mqMessageLogDao.selectByExample(entity);
  }

  @Override
  public PageResult<MqMessageLog> getPageListByCondition(MqMessageLog entity) throws MyException {
    return mqMessageLogDao.selectPageResultByExample(entity);
  }

  @Override
  public void deleteById(long id) throws MyException {
    mqMessageLogDao.deleteById(id);
  }

}
