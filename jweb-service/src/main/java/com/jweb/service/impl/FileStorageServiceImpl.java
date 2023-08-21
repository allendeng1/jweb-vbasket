package com.jweb.service.impl;

import com.jweb.common.exception.MyException;
import com.jweb.dao.base.PageResult;
import com.jweb.dao.FileStorageDao;
import com.jweb.service.FileStorageService;
import com.jweb.common.util.DataUtil;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import lombok.extern.slf4j.Slf4j;
import com.jweb.dao.entity.FileStorage;

/**
 *
 * @author 邓超
 *
 * 2023/08/20 13:15
*/
@Slf4j
@Service
public class FileStorageServiceImpl extends DataUtil implements FileStorageService {

  @Autowired
  private FileStorageDao fileStorageDao;

  @Override
  public FileStorage saveEntity(FileStorage entity) throws MyException {
    fileStorageDao.insert(entity);
    return entity;
  }

  @Override
  public FileStorage getById(long id) throws MyException {
    return fileStorageDao.selectById(id);
  }

  @Override
  public FileStorage getOneByCondition(FileStorage entity) throws MyException {
    return fileStorageDao.selectOneByExample(entity);
  }

  @Override
  public List<FileStorage> getListByCondition(FileStorage entity) throws MyException {
    return fileStorageDao.selectByExample(entity);
  }

  @Override
  public PageResult<FileStorage> getPageListByCondition(FileStorage entity) throws MyException {
    return fileStorageDao.selectPageResultByExample(entity);
  }

  @Override
  public void deleteById(long id) throws MyException {
    fileStorageDao.deleteById(id);
  }

}
