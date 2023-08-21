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
 * 2023/08/20 13:25
*/
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel("消息模板")
@Table(name = "message_template")
public class MessageTemplate extends BaseEntity {

    private static final long serialVersionUID = 1L;

	/***app Id*/
	@Column(name = "app_id")
	@ApiModelProperty(value="app Id", dataType="Long")
	private Long appId;

	/***模板编号*/
	@Column(name = "code")
	@ApiModelProperty(value="模板编号", dataType="String")
	private String code;

	/***发送渠道*/
	@Column(name = "sent_channel")
	@ApiModelProperty(value="发送渠道", dataType="String")
	private String sentChannel;

	/***第三方平台模板编号*/
	@Column(name = "template_code")
	@ApiModelProperty(value="第三方平台模板编号", dataType="String")
	private String templateCode;

	/***消息内容*/
	@Column(name = "content")
	@ApiModelProperty(value="消息内容", dataType="String")
	private String content;

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