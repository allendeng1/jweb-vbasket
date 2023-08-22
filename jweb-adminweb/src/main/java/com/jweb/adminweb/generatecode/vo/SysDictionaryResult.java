package com.jweb.adminweb.generatecode.vo;

import com.jweb.common.api.ApiResult;
import com.jweb.dao.base.PageResult;
import com.jweb.dao.entity.SysDictionary;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
/**
 *
 * @author 邓超
 *
 * 2023/08/22 14:43
*/
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class SysDictionaryResult extends ApiResult {

  private static final long serialVersionUID = 1L;

  public PageResult<SysDictionary> data;
}
