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
import java.lang.Integer;
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
@ApiModel("MQ异步消息")
@Table(name = "mq_message")
public class MqMessage extends BaseEntity {

    private static final long serialVersionUID = 1L;

	/***消息通道*/
	@Column(name = "channel")
	@ApiModelProperty(value="消息通道", dataType="String")
	private String channel;

	/***主题名称*/
	@Column(name = "topic_name")
	@ApiModelProperty(value="主题名称", dataType="String")
	private String topicName;

	/***队列名称*/
	@Column(name = "queue_name")
	@ApiModelProperty(value="队列名称", dataType="String")
	private String queueName;

	/***业务类型*/
	@Column(name = "biz_type")
	@ApiModelProperty(value="业务类型", dataType="String")
	private String bizType;

	/***状态：0-未处理，1-已发布，2-处理成功，3-处理失败*/
	@Column(name = "status")
	@ApiModelProperty(value="状态：0-未处理，1-已发布，2-处理成功，3-处理失败", dataType="Integer")
	private Integer status;

	/***发布时间*/
	@Column(name = "publish_time")
	@ApiModelProperty(value="发布时间", dataType="Long")
	private Long publishTime;

	/***消息内容*/
	@Column(name = "content")
	@ApiModelProperty(value="消息内容", dataType="String")
	private String content;

	/***是否删除*/
	@Column(name = "is_delete")
	@ApiModelProperty(value="是否删除", dataType="Boolean")
	private Boolean isDelete;

	/***删除时间*/
	@Column(name = "delete_time")
	@ApiModelProperty(value="删除时间", dataType="Long")
	private Long deleteTime;

	/***创建时间*/
	@Column(name = "ctdate")
	@ApiModelProperty(value="创建时间", dataType="Long")
	private Long ctdate;

	/***更新时间*/
	@Column(name = "mddate")
	@ApiModelProperty(value="更新时间", dataType="Long")
	private Long mddate;

}