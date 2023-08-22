package com.jweb.message.base;

import com.jweb.dao.constant.DatabaseConstant;

import lombok.Data;

/**
 * 消息发送结果
 * @author 邓超
 *
 * 2022/08/26 下午3:00:27
 */
@Data
public class MessageResult {
	
	/**
	 * 发送状态
	 */
	private DatabaseConstant.MessageSentStatus status;
	/**
	 * 失败code值，用于区分失败原因
	 */
	private String errCode;
	/**
	 * 失败原因
	 */
	private String errMsg;
	/**
	 * 第三方消息标识
	 */
	private String reference;
	/**
	 * 结果数据
	 */
	private String resultData;
	
	
	
}
