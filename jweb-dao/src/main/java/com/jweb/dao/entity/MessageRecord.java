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
 * 2023/08/22 18:02
*/
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel("消息发送记录")
@Table(name = "message_record")
public class MessageRecord extends BaseEntity {

    private static final long serialVersionUID = 1L;

	/***模板编号*/
	@Column(name = "code")
	@ApiModelProperty(value="模板编号", dataType="String")
	private String code;

	/***发送渠道*/
	@Column(name = "sent_channel")
	@ApiModelProperty(value="发送渠道", dataType="String")
	private String sentChannel;

	/***状态：0-待发送，1-发送中，2-发送成功，3-发送失败*/
	@Column(name = "status")
	@ApiModelProperty(value="状态：0-待发送，1-发送中，2-发送成功，3-发送失败", dataType="Integer")
	private Integer status;

	/***发送者*/
	@Column(name = "sender")
	@ApiModelProperty(value="发送者", dataType="Long")
	private Long sender;

	/***接收者*/
	@Column(name = "receiver")
	@ApiModelProperty(value="接收者", dataType="String")
	private String receiver;

	/***发送方式：1-实时发送，2-定时发送*/
	@Column(name = "sent_way")
	@ApiModelProperty(value="发送方式：1-实时发送，2-定时发送", dataType="Integer")
	private Integer sentWay;

	/***定时发送时间点*/
	@Column(name = "timing_time")
	@ApiModelProperty(value="定时发送时间点", dataType="Long")
	private Long timingTime;

	/***消息内容*/
	@Column(name = "content")
	@ApiModelProperty(value="消息内容", dataType="String")
	private String content;

	/***发送时间*/
	@Column(name = "sent_time")
	@ApiModelProperty(value="发送时间", dataType="Long")
	private Long sentTime;

	/***发送结果*/
	@Column(name = "sent_result")
	@ApiModelProperty(value="发送结果", dataType="String")
	private String sentResult;

	/***第三方平台模板编号*/
	@Column(name = "template_code")
	@ApiModelProperty(value="第三方平台模板编号", dataType="String")
	private String templateCode;

	/***第三方消息标识*/
	@Column(name = "reference")
	@ApiModelProperty(value="第三方消息标识", dataType="String")
	private String reference;

	/***回调时间*/
	@Column(name = "callback_time")
	@ApiModelProperty(value="回调时间", dataType="Long")
	private Long callbackTime;

	/***回调结果*/
	@Column(name = "callback_result")
	@ApiModelProperty(value="回调结果", dataType="String")
	private String callbackResult;

	/***是否已读*/
	@Column(name = "is_read")
	@ApiModelProperty(value="是否已读", dataType="Boolean")
	private Boolean isRead;

	/****/
	@Column(name = "is_delete")
	@ApiModelProperty(value="", dataType="Boolean")
	private Boolean isDelete;

	/****/
	@Column(name = "ctdate")
	@ApiModelProperty(value="", dataType="Long")
	private Long ctdate;

	/****/
	@Column(name = "mddate")
	@ApiModelProperty(value="", dataType="Long")
	private Long mddate;

}