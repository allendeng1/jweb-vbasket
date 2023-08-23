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
@ApiModel("任务运行日志")
@Table(name = "job_run_log")
public class JobRunLog extends BaseEntity {

    private static final long serialVersionUID = 1L;

	/***运行编号*/
	@Column(name = "run_no")
	@ApiModelProperty(value="运行编号", dataType="String")
	private String runNo;

	/***任务ID*/
	@Column(name = "task_id")
	@ApiModelProperty(value="任务ID", dataType="Long")
	private Long taskId;

	/***任务编号*/
	@Column(name = "task_no")
	@ApiModelProperty(value="任务编号", dataType="String")
	private String taskNo;

	/***任务名*/
	@Column(name = "task_name")
	@ApiModelProperty(value="任务名", dataType="String")
	private String taskName;

	/***状态：0-运行中，1-运行结束*/
	@Column(name = "status")
	@ApiModelProperty(value="状态：0-运行中，1-运行结束", dataType="Integer")
	private Integer status;

	/***运行结果：0-成功，1-失败*/
	@Column(name = "run_result")
	@ApiModelProperty(value="运行结果：0-成功，1-失败", dataType="Integer")
	private Integer runResult;

	/***开始时间*/
	@Column(name = "start_date")
	@ApiModelProperty(value="开始时间", dataType="Long")
	private Long startDate;

	/***结束时间*/
	@Column(name = "end_date")
	@ApiModelProperty(value="结束时间", dataType="Long")
	private Long endDate;

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