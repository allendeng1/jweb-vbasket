package com.jweb.service;

import com.jweb.common.exception.MyException;
import java.util.List;
import com.jweb.dao.base.PageResult;
import com.jweb.dao.entity.MqMessage;

/**
 *
 * @author 邓超
 *
 * 2023/08/22 15:26
*/
public interface MqMessageService {

  /**
  * 创建或修改(id != null)
  * @param entity
  * @return
  * @throws MyException
  */
  public MqMessage saveEntity(MqMessage entity) throws MyException;
  /**
  * 根据ID查询数据
  * @param id
  * @return
  * @throws MyException
  */
  public MqMessage getById(long id) throws MyException;
  /**
  * 根据条件查询一条数据
  * @param entity
  * @return
  * @throws MyException
  */
  public MqMessage getOneByCondition(MqMessage entity) throws MyException;
  /**
  * 根据条件查询数据
  * @param entity
  * @return
  * @throws MyException
  */
  public List<MqMessage> getListByCondition(MqMessage entity) throws MyException;
  /**
  * 根据条件查询page数据
  * @param entity
  * @return
  * @throws MyException
  */
  public PageResult<MqMessage> getPageListByCondition(MqMessage entity) throws MyException;
  /**
  * 根据ID删除数据
  * @param id
  * @return
  * @throws MyException
  */
  public void deleteById(long id) throws MyException;
}
