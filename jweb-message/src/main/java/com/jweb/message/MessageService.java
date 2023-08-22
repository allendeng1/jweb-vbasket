package com.jweb.message;

import com.alibaba.fastjson.JSONObject;
import com.jweb.common.exception.MyException;
import com.jweb.dao.base.PageResult;
import com.jweb.dao.entity.MessageRecord;
import com.jweb.dao.entity.MessageTemplate;
import com.jweb.message.base.MessageChannel;
import com.jweb.message.base.MessageType;
import com.jweb.message.template.TemplateParam;

/**
 * 消息接口定义
 * @author 邓超
 *
 * 2022/09/02 下午5:53:54
 */
public interface MessageService {

	/**
	 * 发送实时消息
	 * @param type  消息类型 
	 * @param recipient 消息接收者
	 * @param param 消息模板参数
	 * @throws MyException
	 */
	void sentMessage(MessageType type, String recipient, TemplateParam param)throws MyException;
	
	/**
	 * 发送实时消息
	 * @param operatorId  操作人ID
	 * @param type  消息类型 
	 * @param recipient 消息接收者
	 * @param param 消息模板参数
	 * @throws MyException
	 */
	void sentMessage(long operatorId, MessageType type, String recipient, TemplateParam param)throws MyException;
	
	/**
	 * 发送定时消息
	 * @param type  消息类型 
	 * @param recipient 消息接收者
	 * @param timingTime 消息定时发送时间，单位毫秒，须在10分钟之后
	 * @param param 消息模板参数
	 * @throws MyException
	 */
	void sentMessage(MessageType type, String recipient, long timingTime, TemplateParam param)throws MyException;
	
	/**
	 * 发送定时消息
	 * @param operatorId  操作人ID
	 * @param type  消息类型 
	 * @param recipient 消息接收者
	 * @param timingTime 消息定时发送时间，单位毫秒，须在10分钟之后
	 * @param param 消息模板参数
	 * @throws MyException
	 */
	void sentMessage(long operatorId, MessageType type, String recipient, long timingTime, TemplateParam param)throws MyException;
	
	/**
	 * 发送结果callback
	 * @param channel 发送渠道
	 * @param jsonParam 第三方callback data
	 * @throws MyException
	 */
	void callback(MessageChannel channel, JSONObject jsonParam)throws MyException;
	
	/**
	 * 定时消息发送
	 * @param timingTime 定时时间点
	 * @throws MyException
	 */
	void sentTimingMessage(long timingTime)throws MyException;
	/**
	 * 查询模板列表
	 * @param query
	 * @return
	 * @throws MyException
	 */
	public PageResult<MessageTemplate> getMessageTemplateList(MessageTemplate query) throws MyException;
	
	/**
	 * 修改消息模板
	 * @param operatorId 操作人ID
	 * @param id 消息模板ID
	 * @param channel 发送渠道
	 * @param templateCode 第三方平台模板编号
	 * @param content 消息内容
	 * @throws MyException
	 */
	void updateMessageTemplate(long operatorId, long id, MessageChannel channel, String templateCode, String content)throws MyException;
	
	/**
	 * 一键切换消息渠道
	 * @param operatorId 操作人ID
	 * @param ids 消息模板ID，多个以逗号分隔，不传切换所有消息模板
	 * @param channel 发送渠道
	 * @throws MyException
	 */
	void updateForToggleChannel(long operatorId, String ids, MessageChannel channel)throws MyException;
	
	/**
	 * 查询消息发送记录列表
	 * @param query
	 * @return
	 * @throws MyException
	 */
	public PageResult<MessageRecord> getMessageRecordList(MessageRecord query) throws MyException;
}
