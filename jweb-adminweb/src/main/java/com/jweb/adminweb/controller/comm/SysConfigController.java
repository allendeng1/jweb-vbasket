package com.jweb.adminweb.controller.comm;

import com.jweb.adminweb.controller.comm.doc.SysConfigApiDoc;
import com.jweb.adminweb.controller.comm.vo.SysConfigResult;
import com.jweb.common.api.ApiResult;
import com.jweb.dao.base.PageResult;
import com.jweb.dao.config.SystemConfig;
import com.jweb.common.api.BaseController;
import com.jweb.common.exception.MyException;
import com.jweb.dao.entity.SysConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import com.jweb.service.SysConfigService;
import com.jweb.watchdog.aspect.ccattack.CCAttackMonitor;
import com.jweb.watchdog.aspect.resubmit.NoResubmit;
/**
 *
 * @author 邓超
 *
 * 2023/08/22 18:02
*/
@RestController
public class SysConfigController extends BaseController implements SysConfigApiDoc {

  @Autowired
  private SystemConfig systemConfig;
  @Autowired
  private SysConfigService sysConfigService;
  @Autowired(required = false)
  private CCAttackMonitor cCAttackMonitor;

  @NoResubmit(timeInterval = 3, submittable = true)
  @Override
  public ApiResult saveSysConfig(
    String propKey,
    String propValue,
    String remark) {
    ApiResult result = new ApiResult();
    try {
      systemConfig.save(propKey, propValue, remark);
      if(propKey.equalsIgnoreCase("attack.cc.ignore.ip")) {
    	  if(isNotNull(cCAttackMonitor)) {
    		  cCAttackMonitor.updateIgnoreIp(propValue);
    	  }
      }
    } catch (MyException e) {
      result.bizFail(e);
    }
    return result;
  }

  @NoResubmit(timeInterval = 3, submittable = true)
  @Override
  public SysConfigResult getSysConfigList(
    Integer status,
    String propKey,
    Integer page,
    Integer limit) {
    SysConfigResult result = new SysConfigResult();
    try {
      SysConfig entity = new SysConfig();
      entity.setStatus(status);
      entity.setPropKey(propKey);
      entity.page(page, limit);
      PageResult<SysConfig> pageData = sysConfigService.getPageListByCondition(entity);
      result.setData(pageData);
    } catch (MyException e) {
      result.bizFail(e);
    }
    return result;
  }

  @NoResubmit(timeInterval = 3, submittable = true)
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
