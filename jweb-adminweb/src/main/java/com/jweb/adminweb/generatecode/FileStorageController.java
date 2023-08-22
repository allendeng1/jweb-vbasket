package com.jweb.adminweb.generatecode;

import com.jweb.common.api.ApiResult;
import com.jweb.dao.base.PageResult;
import com.jweb.common.api.BaseController;
import com.jweb.common.exception.MyException;
import com.jweb.dao.entity.FileStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import com.jweb.adminweb.generatecode.vo.FileStorageResult;
import com.jweb.adminweb.generatecode.FileStorageApiDoc;
import com.jweb.service.FileStorageService;
/**
 *
 * @author 邓超
 *
 * 2023/08/22 14:43
*/
@RestController
public class FileStorageController extends BaseController implements FileStorageApiDoc {

  @Autowired
  private FileStorageService fileStorageService;

  @Override
  public ApiResult addFileStorage(
    String storageSite,
    String owner,
    Integer type,
    String storagePath,
    String fileType,
    String fileName,
    Long fileSize,
    Boolean isDelete,
    Long ctdate,
    Long mddate) {
    ApiResult result = new ApiResult();
    try {
      FileStorage entity = new FileStorage();
      entity.setStorageSite(storageSite);
      entity.setOwner(owner);
      entity.setType(type);
      entity.setStoragePath(storagePath);
      entity.setFileType(fileType);
      entity.setFileName(fileName);
      entity.setFileSize(fileSize);
      entity.setIsDelete(isDelete);
      entity.setCtdate(ctdate);
      entity.setMddate(mddate);
      fileStorageService.saveEntity(entity);
    } catch (MyException e) {
      result.bizFail(e);
    }
    return result;
  }

  @Override
  public ApiResult editFileStorage(
    Long id,
    String storageSite,
    String owner,
    Integer type,
    String storagePath,
    String fileType,
    String fileName,
    Long fileSize,
    Boolean isDelete,
    Long ctdate,
    Long mddate) {
    ApiResult result = new ApiResult();
    try {
      FileStorage entity = new FileStorage();
      entity.setId(id);
      entity.setStorageSite(storageSite);
      entity.setOwner(owner);
      entity.setType(type);
      entity.setStoragePath(storagePath);
      entity.setFileType(fileType);
      entity.setFileName(fileName);
      entity.setFileSize(fileSize);
      entity.setIsDelete(isDelete);
      entity.setCtdate(ctdate);
      entity.setMddate(mddate);
      fileStorageService.saveEntity(entity);
    } catch (MyException e) {
      result.bizFail(e);
    }
    return result;
  }

  @Override
  public FileStorageResult getFileStorageList(
    String storageSite,
    String owner,
    Integer type,
    String storagePath,
    String fileType,
    String fileName,
    Long fileSize,
    Boolean isDelete,
    Long ctdate,
    Long mddate,
    Integer page,
    Integer limit) {
    FileStorageResult result = new FileStorageResult();
    try {
      FileStorage entity = new FileStorage();
      entity.setStorageSite(storageSite);
      entity.setOwner(owner);
      entity.setType(type);
      entity.setStoragePath(storagePath);
      entity.setFileType(fileType);
      entity.setFileName(fileName);
      entity.setFileSize(fileSize);
      entity.setIsDelete(isDelete);
      entity.setCtdate(ctdate);
      entity.setMddate(mddate);
      entity.page(page, limit);
      PageResult<FileStorage> pageData = fileStorageService.getPageListByCondition(entity);
      result.setData(pageData);
    } catch (MyException e) {
      result.bizFail(e);
    }
    return result;
  }

  @Override
  public ApiResult getFileStorage(Long id) {

    ApiResult result = new ApiResult();
    try {
      FileStorage data = fileStorageService.getById(id);
      result.setData(data);
    } catch (MyException e) {
      result.bizFail(e);
    }
    return result;
  }
}
