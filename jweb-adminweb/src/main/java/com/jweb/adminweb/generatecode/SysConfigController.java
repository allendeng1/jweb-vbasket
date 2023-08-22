package com.jweb.adminweb.generatecode;

import com.jweb.common.api.ApiResult;
import com.jweb.dao.base.PageResult;
import com.jweb.common.api.BaseController;
import com.jweb.common.exception.MyException;
import com.jweb.dao.entity.SysConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import com.jweb.adminweb.generatecode.vo.SysConfigResult;
import com.jweb.adminweb.generatecode.SysConfigApiDoc;
import com.jweb.service.SysConfigService;
/**
 *
 * @author 邓超
 *
 * 2023/08/22 14:43
*/
@RestController
public class SysConfigController extends BaseController implements SysConfigApiDoc {

  @Autowired
  private SysConfigService sysConfigService;

  @Override
  public ApiResult addSysConfig(
    Integer status,
    String propKey,
    String propValue,
    String remark,
    Long ctdate,
    Long mddate) {
    ApiResult result = new ApiResult();
    try {
      SysConfig entity = new SysConfig();
      entity.setStatus(status);
      entity.setPropKey(propKey);
      entity.setPropValue(propValue);
      entity.setRemark(remark);
      entity.setCtdate(ctdate);
      entity.setMddate(mddate);
      sysConfigService.saveEntity(entity);
    } catch (MyException e) {
      result.bizFail(e);
    }
    return result;
  }

  @Override
  public ApiResult editSysConfig(
    Long id,
    Integer status,
    String propKey,
    String propValue,
    String remark,
    Long ctdate,
    Long mddate) {
    ApiResult result = new ApiResult();
    try {
      SysConfig entity = new SysConfig();
      entity.setId(id);
      entity.setStatus(status);
      entity.setPropKey(propKey);
      entity.setPropValue(propValue);
      entity.setRemark(remark);
      entity.setCtdate(ctdate);
      entity.setMddate(mddate);
      sysConfigService.saveEntity(entity);
    } catch (MyException e) {
      result.bizFail(e);
    }
    return result;
  }

  @Override
  public SysConfigResult getSysConfigList(
    Integer status,
    String propKey,
    String propValue,
    String remark,
    Long ctdate,
    Long mddate,
    Integer page,
    Integer limit) {
    SysConfigResult result = new SysConfigResult();
    try {
      SysConfig entity = new SysConfig();
      entity.setStatus(status);
      entity.setPropKey(propKey);
      entity.setPropValue(propValue);
      entity.setRemark(remark);
      entity.setCtdate(ctdate);
      entity.setMddate(mddate);
      entity.page(page, limit);
      PageResult<SysConfig> pageData = sysConfigService.getPageListByCondition(entity);
      result.setData(pageData);
    } catch (MyException e) {
      result.bizFail(e);
    }
    return result;
  }

  @Override
  public ApiResult getSysConfig(Long id) {

    ApiResult result = new ApiResult();
    try {
      SysConfig data = sysConfigService.getById(id);
      result.setData(data);
    } catch (MyException e) {
      result.bizFail(e);
    }
    return result;
  }
}
