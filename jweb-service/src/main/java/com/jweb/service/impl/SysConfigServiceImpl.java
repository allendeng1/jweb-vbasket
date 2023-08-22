package com.jweb.service.impl;

import com.jweb.common.exception.MyException;
import com.jweb.dao.base.PageResult;
import com.jweb.dao.SysConfigDao;
import com.jweb.service.SysConfigService;
import com.jweb.common.util.DataUtil;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import lombok.extern.slf4j.Slf4j;
import com.jweb.dao.entity.SysConfig;

/**
 *
 * @author 邓超
 *
 * 2023/08/22 11:27
*/
@Slf4j
@Service
public class SysConfigServiceImpl extends DataUtil implements SysConfigService {

  @Autowired
  private SysConfigDao sysConfigDao;

  @Override
  public SysConfig saveEntity(SysConfig entity) throws MyException {
    sysConfigDao.insert(entity);
    return entity;
  }

  @Override
  public SysConfig getById(long id) throws MyException {
    return sysConfigDao.selectById(id);
  }

  @Override
  public SysConfig getOneByCondition(SysConfig entity) throws MyException {
    return sysConfigDao.selectOneByExample(entity);
  }

  @Override
  public List<SysConfig> getListByCondition(SysConfig entity) throws MyException {
    return sysConfigDao.selectByExample(entity);
  }

  @Override
  public PageResult<SysConfig> getPageListByCondition(SysConfig entity) throws MyException {
    return sysConfigDao.selectPageResultByExample(entity);
  }

  @Override
  public void deleteById(long id) throws MyException {
    sysConfigDao.deleteById(id);
  }

}
