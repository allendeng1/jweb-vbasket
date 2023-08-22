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
@ApiModel("任务")
@Table(name = "job_rule")
public class JobRule extends BaseEntity {

    private static final long serialVersionUID = 1L;

	/***任务编号*/
	@Column(name = "task_no")
	@ApiModelProperty(value="任务编号", dataType="String")
	private String taskNo;

	/***任务名*/
	@Column(name = "task_name")
	@ApiModelProperty(value="任务名", dataType="String")
	private String taskName;

	/***使用状态：1-固定任务，2-临时任务*/
	@Column(name = "type")
	@ApiModelProperty(value="使用状态：1-固定任务，2-临时任务", dataType="Integer")
	private Integer type;

	/***定时表达式*/
	@Column(name = "task_cron")
	@ApiModelProperty(value="定时表达式", dataType="String")
	private String taskCron;

	/***使用状态：0-使用中，1-已废弃*/
	@Column(name = "status")
	@ApiModelProperty(value="使用状态：0-使用中，1-已废弃", dataType="Integer")
	private Integer status;

	/***运行状态：0-未运行，1-运行中，2-已执行*/
	@Column(name = "run_status")
	@ApiModelProperty(value="运行状态：0-未运行，1-运行中，2-已执行", dataType="Integer")
	private Integer runStatus;

	/***任务执行器类完整包名*/
	@Column(name = "handler_name")
	@ApiModelProperty(value="任务执行器类完整包名", dataType="String")
	private String handlerName;

	/***任务内容*/
	@Column(name = "task_content")
	@ApiModelProperty(value="任务内容", dataType="String")
	private String taskContent;

	/***备注*/
	@Column(name = "remark")
	@ApiModelProperty(value="备注", dataType="String")
	private String remark;

	/***创建时间*/
	@Column(name = "ctdate")
	@ApiModelProperty(value="创建时间", dataType="Long")
	private Long ctdate;

	/***更新时间*/
	@Column(name = "mddate")
	@ApiModelProperty(value="更新时间", dataType="Long")
	private Long mddate;

}