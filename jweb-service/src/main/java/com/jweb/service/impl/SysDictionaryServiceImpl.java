package com.jweb.service.impl;

import com.jweb.common.exception.MyException;
import com.jweb.dao.base.PageResult;
import com.jweb.dao.SysDictionaryDao;
import com.jweb.service.SysDictionaryService;
import com.jweb.common.util.DataUtil;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import lombok.extern.slf4j.Slf4j;
import com.jweb.dao.entity.SysDictionary;

/**
 *
 * @author 邓超
 *
 * 2023/08/22 11:27
*/
@Slf4j
@Service
public class SysDictionaryServiceImpl extends DataUtil implements SysDictionaryService {

  @Autowired
  private SysDictionaryDao sysDictionaryDao;

  @Override
  public SysDictionary saveEntity(SysDictionary entity) throws MyException {
    sysDictionaryDao.insert(entity);
    return entity;
  }

  @Override
  public SysDictionary getById(long id) throws MyException {
    return sysDictionaryDao.selectById(id);
  }

  @Override
  public SysDictionary getOneByCondition(SysDictionary entity) throws MyException {
    return sysDictionaryDao.selectOneByExample(entity);
  }

  @Override
  public List<SysDictionary> getListByCondition(SysDictionary entity) throws MyException {
    return sysDictionaryDao.selectByExample(entity);
  }

  @Override
  public PageResult<SysDictionary> getPageListByCondition(SysDictionary entity) throws MyException {
    return sysDictionaryDao.selectPageResultByExample(entity);
  }

  @Override
  public void deleteById(long id) throws MyException {
    sysDictionaryDao.deleteById(id);
  }

}
