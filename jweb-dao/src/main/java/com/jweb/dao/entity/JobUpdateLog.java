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
@ApiModel("任务修改日志")
@Table(name = "job_update_log")
public class JobUpdateLog extends BaseEntity {

    private static final long serialVersionUID = 1L;

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

	/***修改类型：0-创建任务，1-修改执行时间，2-修改使用状态，3-修改运行状态，4-修改执行器，5-修改其他信息*/
	@Column(name = "type")
	@ApiModelProperty(value="修改类型：0-创建任务，1-修改执行时间，2-修改使用状态，3-修改运行状态，4-修改执行器，5-修改其他信息", dataType="Integer")
	private Integer type;

	/***操作员ID*/
	@Column(name = "admin_user_id")
	@ApiModelProperty(value="操作员ID", dataType="Long")
	private Long adminUserId;

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