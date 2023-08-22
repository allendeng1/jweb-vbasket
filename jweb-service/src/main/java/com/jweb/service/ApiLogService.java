package com.jweb.service;

import com.jweb.common.exception.MyException;
import java.util.List;
import com.jweb.dao.base.PageResult;
import com.jweb.dao.entity.ApiLog;

/**
 *
 * @author 邓超
 *
 * 2023/08/22 11:27
*/
public interface ApiLogService {

  /**
  * 创建或修改(id != null)
  * @param entity
  * @return
  * @throws MyException
  */
  public ApiLog saveEntity(ApiLog entity) throws MyException;
  /**
  * 根据ID查询数据
  * @param id
  * @return
  * @throws MyException
  */
  public ApiLog getById(long id) throws MyException;
  /**
  * 根据条件查询一条数据
  * @param entity
  * @return
  * @throws MyException
  */
  public ApiLog getOneByCondition(ApiLog entity) throws MyException;
  /**
  * 根据条件查询数据
  * @param entity
  * @return
  * @throws MyException
  */
  public List<ApiLog> getListByCondition(ApiLog entity) throws MyException;
  /**
  * 根据条件查询page数据
  * @param entity
  * @return
  * @throws MyException
  */
  public PageResult<ApiLog> getPageListByCondition(ApiLog entity) throws MyException;
  /**
  * 根据ID删除数据
  * @param id
  * @return
  * @throws MyException
  */
  public void deleteById(long id) throws MyException;
}
