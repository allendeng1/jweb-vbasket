package com.jweb.adminweb.generatecode.vo;

import com.jweb.common.api.ApiResult;
import com.jweb.dao.base.PageResult;
import com.jweb.dao.entity.MessageRecord;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
/**
 *
 * @author 邓超
 *
 * 2023/08/21 11:51
*/
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class MessageRecordResult extends ApiResult {

  private static final long serialVersionUID = 1L;

  public PageResult<MessageRecord> data;
}
