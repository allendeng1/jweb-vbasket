package com.jweb.service.impl;

import com.jweb.common.exception.MyException;
import com.jweb.dao.base.PageResult;
import com.jweb.dao.MessageRecordDao;
import com.jweb.service.MessageRecordService;
import com.jweb.common.util.DataUtil;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import lombok.extern.slf4j.Slf4j;
import com.jweb.dao.entity.MessageRecord;

/**
 *
 * @author 邓超
 *
 * 2023/08/22 11:27
*/
@Slf4j
@Service
public class MessageRecordServiceImpl extends DataUtil implements MessageRecordService {

  @Autowired
  private MessageRecordDao messageRecordDao;

  @Override
  public MessageRecord saveEntity(MessageRecord entity) throws MyException {
    messageRecordDao.insert(entity);
    return entity;
  }

  @Override
  public MessageRecord getById(long id) throws MyException {
    return messageRecordDao.selectById(id);
  }

  @Override
  public MessageRecord getOneByCondition(MessageRecord entity) throws MyException {
    return messageRecordDao.selectOneByExample(entity);
  }

  @Override
  public List<MessageRecord> getListByCondition(MessageRecord entity) throws MyException {
    return messageRecordDao.selectByExample(entity);
  }

  @Override
  public PageResult<MessageRecord> getPageListByCondition(MessageRecord entity) throws MyException {
    return messageRecordDao.selectPageResultByExample(entity);
  }

  @Override
  public void deleteById(long id) throws MyException {
    messageRecordDao.deleteById(id);
  }

}
