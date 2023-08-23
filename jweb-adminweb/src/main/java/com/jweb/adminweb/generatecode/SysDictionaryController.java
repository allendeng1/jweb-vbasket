package com.jweb.adminweb.generatecode;

import com.jweb.common.api.ApiResult;
import com.jweb.dao.base.PageResult;
import com.jweb.common.api.BaseController;
import com.jweb.common.exception.MyException;
import com.jweb.dao.entity.SysDictionary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import com.jweb.adminweb.generatecode.vo.SysDictionaryResult;
import com.jweb.adminweb.generatecode.SysDictionaryApiDoc;
import com.jweb.service.SysDictionaryService;
/**
 *
 * @author 邓超
 *
 * 2023/08/22 18:02
*/
@RestController
public class SysDictionaryController extends BaseController implements SysDictionaryApiDoc {

  @Autowired
  private SysDictionaryService sysDictionaryService;

  @Override
  public ApiResult addSysDictionary(
    String type,
    String region,
    String name,
    Integer status,
    Integer sortCol,
    Long ctdate,
    Long mddate) {
    ApiResult result = new ApiResult();
    try {
      SysDictionary entity = new SysDictionary();
      entity.setType(type);
      entity.setRegion(region);
      entity.setName(name);
      entity.setStatus(status);
      entity.setSortCol(sortCol);
      entity.setCtdate(ctdate);
      entity.setMddate(mddate);
      sysDictionaryService.saveEntity(entity);
    } catch (MyException e) {
      result.bizFail(e);
    }
    return result;
  }

  @Override
  public ApiResult editSysDictionary(
    Long id,
    String type,
    String region,
    String name,
    Integer status,
    Integer sortCol,
    Long ctdate,
    Long mddate) {
    ApiResult result = new ApiResult();
    try {
      SysDictionary entity = new SysDictionary();
      entity.setId(id);
      entity.setType(type);
      entity.setRegion(region);
      entity.setName(name);
      entity.setStatus(status);
      entity.setSortCol(sortCol);
      entity.setCtdate(ctdate);
      entity.setMddate(mddate);
      sysDictionaryService.saveEntity(entity);
    } catch (MyException e) {
      result.bizFail(e);
    }
    return result;
  }

  @Override
  public SysDictionaryResult getSysDictionaryList(
    String type,
    String region,
    String name,
    Integer status,
    Integer sortCol,
    Long ctdate,
    Long mddate,
    Integer page,
    Integer limit) {
    SysDictionaryResult result = new SysDictionaryResult();
    try {
      SysDictionary entity = new SysDictionary();
      entity.setType(type);
      entity.setRegion(region);
      entity.setName(name);
      entity.setStatus(status);
      entity.setSortCol(sortCol);
      entity.setCtdate(ctdate);
      entity.setMddate(mddate);
      entity.page(page, limit);
      PageResult<SysDictionary> pageData = sysDictionaryService.getPageListByCondition(entity);
      result.setData(pageData);
    } catch (MyException e) {
      result.bizFail(e);
    }
    return result;
  }

  @Override
  public ApiResult getSysDictionary(Long id) {

    ApiResult result = new ApiResult();
    try {
      SysDictionary data = sysDictionaryService.getById(id);
      result.setData(data);
    } catch (MyException e) {
      result.bizFail(e);
    }
    return result;
  }
}
