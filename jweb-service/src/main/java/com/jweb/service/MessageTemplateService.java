package com.jweb.service;

import com.jweb.common.exception.MyException;
import java.util.List;
import com.jweb.dao.base.PageResult;
import com.jweb.dao.entity.MessageTemplate;

/**
 *
 * @author 邓超
 *
 * 2023/08/22 11:27
*/
public interface MessageTemplateService {

  /**
  * 创建或修改(id != null)
  * @param entity
  * @return
  * @throws MyException
  */
  public MessageTemplate saveEntity(MessageTemplate entity) throws MyException;
  /**
  * 根据ID查询数据
  * @param id
  * @return
  * @throws MyException
  */
  public MessageTemplate getById(long id) throws MyException;
  /**
  * 根据条件查询一条数据
  * @param entity
  * @return
  * @throws MyException
  */
  public MessageTemplate getOneByCondition(MessageTemplate entity) throws MyException;
  /**
  * 根据条件查询数据
  * @param entity
  * @return
  * @throws MyException
  */
  public List<MessageTemplate> getListByCondition(MessageTemplate entity) throws MyException;
  /**
  * 根据条件查询page数据
  * @param entity
  * @return
  * @throws MyException
  */
  public PageResult<MessageTemplate> getPageListByCondition(MessageTemplate entity) throws MyException;
  /**
  * 根据ID删除数据
  * @param id
  * @return
  * @throws MyException
  */
  public void deleteById(long id) throws MyException;
}
