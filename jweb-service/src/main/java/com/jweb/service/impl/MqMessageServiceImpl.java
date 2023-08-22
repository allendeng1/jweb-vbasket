package com.jweb.service.impl;

import com.jweb.common.exception.MyException;
import com.jweb.dao.base.PageResult;
import com.jweb.dao.MqMessageDao;
import com.jweb.service.MqMessageService;
import com.jweb.common.util.DataUtil;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import lombok.extern.slf4j.Slf4j;
import com.jweb.dao.entity.MqMessage;

/**
 *
 * @author 邓超
 *
 * 2023/08/22 15:26
*/
@Slf4j
@Service
public class MqMessageServiceImpl extends DataUtil implements MqMessageService {

  @Autowired
  private MqMessageDao mqMessageDao;

  @Override
  public MqMessage saveEntity(MqMessage entity) throws MyException {
    mqMessageDao.insert(entity);
    return entity;
  }

  @Override
  public MqMessage getById(long id) throws MyException {
    return mqMessageDao.selectById(id);
  }

  @Override
  public MqMessage getOneByCondition(MqMessage entity) throws MyException {
    return mqMessageDao.selectOneByExample(entity);
  }

  @Override
  public List<MqMessage> getListByCondition(MqMessage entity) throws MyException {
    return mqMessageDao.selectByExample(entity);
  }

  @Override
  public PageResult<MqMessage> getPageListByCondition(MqMessage entity) throws MyException {
    return mqMessageDao.selectPageResultByExample(entity);
  }

  @Override
  public void deleteById(long id) throws MyException {
    mqMessageDao.deleteById(id);
  }

}
