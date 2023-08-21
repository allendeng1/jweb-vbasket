package com.jweb.service.impl;

import com.jweb.common.exception.MyException;
import com.jweb.dao.base.PageResult;
import com.jweb.dao.MessageTemplateDao;
import com.jweb.service.MessageTemplateService;
import com.jweb.common.util.DataUtil;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import lombok.extern.slf4j.Slf4j;
import com.jweb.dao.entity.MessageTemplate;

/**
 *
 * @author 邓超
 *
 * 2023/08/20 13:15
*/
@Slf4j
@Service
public class MessageTemplateServiceImpl extends DataUtil implements MessageTemplateService {

  @Autowired
  private MessageTemplateDao messageTemplateDao;

  @Override
  public MessageTemplate saveEntity(MessageTemplate entity) throws MyException {
    messageTemplateDao.insert(entity);
    return entity;
  }

  @Override
  public MessageTemplate getById(long id) throws MyException {
    return messageTemplateDao.selectById(id);
  }

  @Override
  public MessageTemplate getOneByCondition(MessageTemplate entity) throws MyException {
    return messageTemplateDao.selectOneByExample(entity);
  }

  @Override
  public List<MessageTemplate> getListByCondition(MessageTemplate entity) throws MyException {
    return messageTemplateDao.selectByExample(entity);
  }

  @Override
  public PageResult<MessageTemplate> getPageListByCondition(MessageTemplate entity) throws MyException {
    return messageTemplateDao.selectPageResultByExample(entity);
  }

  @Override
  public void deleteById(long id) throws MyException {
    messageTemplateDao.deleteById(id);
  }

}
