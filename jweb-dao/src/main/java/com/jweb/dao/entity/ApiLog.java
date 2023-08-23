package com.jweb.dao.entity;

import com.jweb.dao.base.BaseEntity;
import java.lang.Long;
import lombok.Data;
import lombok.ToString;
import io.swagger.annotations.ApiModel;
import java.lang.Boolean;
import javax.persistence.*;
import java.lang.String;
import lombok.EqualsAndHashCode;
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
@ApiModel("APP日志")
@Table(name = "api_log")
public class ApiLog extends BaseEntity {

    private static final long serialVersionUID = 1L;

	/***web服务标识*/
	@Column(name = "server_context_path")
	@ApiModelProperty(value="web服务标识", dataType="String")
	private String serverContextPath;

	/***客户端IP*/
	@Column(name = "ip_address")
	@ApiModelProperty(value="客户端IP", dataType="String")
	private String ipAddress;

	/***用户ID*/
	@Column(name = "user_id")
	@ApiModelProperty(value="用户ID", dataType="Long")
	private Long userId;

	/***请求URL*/
	@Column(name = "req_url")
	@ApiModelProperty(value="请求URL", dataType="String")
	private String reqUrl;

	/***请求方式*/
	@Column(name = "req_method")
	@ApiModelProperty(value="请求方式", dataType="String")
	private String reqMethod;

	/***开始时间*/
	@Column(name = "begin_time")
	@ApiModelProperty(value="开始时间", dataType="Long")
	private Long beginTime;

	/***结束时间*/
	@Column(name = "end_time")
	@ApiModelProperty(value="结束时间", dataType="Long")
	private Long endTime;

	/***耗时(单位：毫秒)*/
	@Column(name = "cost_time")
	@ApiModelProperty(value="耗时(单位：毫秒)", dataType="Long")
	private Long costTime;

	/***系统防攻击检测状态：true-疑似攻击*/
	@Column(name = "is_attack")
	@ApiModelProperty(value="系统防攻击检测状态：true-疑似攻击", dataType="Boolean")
	private Boolean isAttack;

	/***请求参数*/
	@Column(name = "req_param")
	@ApiModelProperty(value="请求参数", dataType="String")
	private String reqParam;

	/***请求处理器名称*/
	@Column(name = "controller_name")
	@ApiModelProperty(value="请求处理器名称", dataType="String")
	private String controllerName;

	/***请求处理方法名*/
	@Column(name = "controller_method")
	@ApiModelProperty(value="请求处理方法名", dataType="String")
	private String controllerMethod;

	/***返回结果数据*/
	@Column(name = "result_data")
	@ApiModelProperty(value="返回结果数据", dataType="String")
	private String resultData;

	/***创建时间*/
	@Column(name = "ctdate")
	@ApiModelProperty(value="创建时间", dataType="Long")
	private Long ctdate;

	/***更新时间*/
	@Column(name = "mddate")
	@ApiModelProperty(value="更新时间", dataType="Long")
	private Long mddate;

}