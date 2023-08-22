package com.jweb.schedule.controller.vo;


import com.jweb.common.api.ApiResult;
import com.jweb.dao.base.PageResult;
import com.jweb.dao.entity.JobRunLog;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
/**
 * 
 * @author 邓超
 *
 * 2022/6/4 12:24
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class JobRunLogResult extends ApiResult{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public PageResult<JobRunLog> data;
}
