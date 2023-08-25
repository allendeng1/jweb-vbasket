package com.jweb.dao.base;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
/**
 * 
 * @author 邓超
 *
 * 2022/6/4 12:24
 */
@Data
@ApiModel("分页列表数据")
public class PageResult<T> implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value="总条数", dataType="int")
	private int totalCount;
	
	@ApiModelProperty(value="总页码", dataType="int")
	private int totalPage;
	
	@ApiModelProperty(value="当前页码", dataType="int")
	private int curPage;

	@ApiModelProperty(value="当前每页条数", dataType="int")
	private int curLimit;
	
	@ApiModelProperty(value="数据", dataType="List")
	private List<T> entitys;

}
