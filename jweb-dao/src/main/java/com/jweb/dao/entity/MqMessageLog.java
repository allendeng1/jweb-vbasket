package com.jweb.dao.entity;

import com.jweb.dao.base.BaseEntity;
import java.lang.Long;
import lombok.Data;
import lombok.ToString;
import io.swagger.annotations.ApiModel;
import javax.persistence.*;
import java.lang.String;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModelProperty;

/**
 *
 * @author 邓超
 *
 * 2023/08/22 15:26
*/
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel("MQ异步消息消费日志")
@Table(name = "mq_message_log")
public class MqMessageLog extends BaseEntity {

    private static final long serialVersionUID = 1L;

	/***消息ID*/
	@Column(name = "message_id")
	@ApiModelProperty(value="消息ID", dataType="Long")
	private Long messageId;

	/***类型*/
	@Column(name = "type")
	@ApiModelProperty(value="类型", dataType="String")
	private String type;

	/***处理方式*/
	@Column(name = "excute_method")
	@ApiModelProperty(value="处理方式", dataType="String")
	private String excuteMethod;

	/***处理器*/
	@Column(name = "excute_handler")
	@ApiModelProperty(value="处理器", dataType="String")
	private String excuteHandler;

	/***开始时间*/
	@Column(name = "start_time")
	@ApiModelProperty(value="开始时间", dataType="Long")
	private Long startTime;

	/***结束时间*/
	@Column(name = "end_time")
	@ApiModelProperty(value="结束时间", dataType="Long")
	private Long endTime;

	/***处理结果描述*/
	@Column(name = "result_desc")
	@ApiModelProperty(value="处理结果描述", dataType="String")
	private String resultDesc;

	/***创建时间*/
	@Column(name = "ctdate")
	@ApiModelProperty(value="创建时间", dataType="Long")
	private Long ctdate;

	/***更新时间*/
	@Column(name = "mddate")
	@ApiModelProperty(value="更新时间", dataType="Long")
	private Long mddate;

}