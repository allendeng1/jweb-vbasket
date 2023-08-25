package com.jweb.adminweb.controller.comm.vo;

import com.jweb.common.api.ApiResult;
import com.jweb.dao.base.PageResult;
import com.jweb.dao.entity.SysConfig;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
/**
 *
 * @author 邓超
 *
 * 2023/08/22 18:02
*/
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class SysConfigResult extends ApiResult {

  private static final long serialVersionUID = 1L;

  public PageResult<SysConfig> data;
}
