package com.jweb.message.base;

import lombok.Data;

/**
 * 消息体
 * @author 邓超
 *
 * 2022/08/26 下午3:11:33
 */
@Data
public class MessageBody {
	
	/**
	 * 消息ID
	 */
	private String msgId;
	
	/**
	 * 消息发送者
	 */
	private String sender;

	/**
	 * 消息接收者
	 */
	private String recipient;
	
	/**
	 * 消息内容
	 */
	private String content;
	/**
	 * 第三方平台的消息模板ID
	 */
	private String templateId;

}
