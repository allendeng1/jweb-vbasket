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
 * 2023/08/22 14:43
*/
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel("系统配置")
@Table(name = "sys_config")
public class SysConfig extends BaseEntity {

    private static final long serialVersionUID = 1L;

	/***状态：0-使用中，1-已废弃*/
	@Column(name = "status")
	@ApiModelProperty(value="状态：0-使用中，1-已废弃", dataType="Integer")
	private Integer status;

	/***配置KEY*/
	@Column(name = "prop_key")
	@ApiModelProperty(value="配置KEY", dataType="String")
	private String propKey;

	/***配置值*/
	@Column(name = "prop_value")
	@ApiModelProperty(value="配置值", dataType="String")
	private String propValue;

	/***配置描述说明*/
	@Column(name = "remark")
	@ApiModelProperty(value="配置描述说明", dataType="String")
	private String remark;

	/****/
	@Column(name = "ctdate")
	@ApiModelProperty(value="", dataType="Long")
	private Long ctdate;

	/****/
	@Column(name = "mddate")
	@ApiModelProperty(value="", dataType="Long")
	private Long mddate;

}