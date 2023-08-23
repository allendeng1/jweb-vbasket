package com.jweb.dao.entity;

import com.jweb.dao.base.BaseEntity;
import java.lang.Long;
import lombok.Data;
import lombok.ToString;
import io.swagger.annotations.ApiModel;
import javax.persistence.*;
import java.lang.String;
import lombok.EqualsAndHashCode;
import java.lang.Integer;
import io.swagger.annotations.ApiModelProperty;

/**
 *
 * @author 邓超
 *
 * 2023/08/22 18:02
*/
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel("系统字典")
@Table(name = "sys_dictionary")
public class SysDictionary extends BaseEntity {

    private static final long serialVersionUID = 1L;

	/***类型*/
	@Column(name = "type")
	@ApiModelProperty(value="类型", dataType="String")
	private String type;

	/***地区*/
	@Column(name = "region")
	@ApiModelProperty(value="地区", dataType="String")
	private String region;

	/***名称*/
	@Column(name = "name")
	@ApiModelProperty(value="名称", dataType="String")
	private String name;

	/***状态：1-正常，2-停用*/
	@Column(name = "status")
	@ApiModelProperty(value="状态：1-正常，2-停用", dataType="Integer")
	private Integer status;

	/***排序列*/
	@Column(name = "sort_col")
	@ApiModelProperty(value="排序列", dataType="Integer")
	private Integer sortCol;

	/***创建时间*/
	@Column(name = "ctdate")
	@ApiModelProperty(value="创建时间", dataType="Long")
	private Long ctdate;

	/***更新时间*/
	@Column(name = "mddate")
	@ApiModelProperty(value="更新时间", dataType="Long")
	private Long mddate;

}