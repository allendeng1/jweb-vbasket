package com.jweb.common.exception;
/**
 * 
 * @author 邓超
 *
 * 2022/6/4 12:24
 */
public class MessageException extends MyException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public int bizCode() {
		return BizCode.MESSAGE_EXCEPTION.getCode();
	}
	public MessageException(Integer errCode, String message) {
		super(errCode, message);
	}
	private MessageException(Integer errCode, String message, Throwable e) {
		super(errCode, message, e);
	}
	
	/**
	 * 消息模板不存在
	 * @throws MessageException
	 */
	public static void templateNotExist() throws MessageException{
		throw new MessageException(400, "消息模板不存在");
	}
	/**
	 * 模板消息变量未替换
	 * @throws MessageException
	 */
	public static void templateVarNotConvert() throws MessageException{
		throw new MessageException(401, "模板消息变量未替换");
	}
	/**
	 * 消息发送渠道未注册
	 * @throws MessageException
	 */
	public static void ChannelNotRegister() throws MessageException{
		throw new MessageException(402, "消息发送渠道未注册");
	}
	/**
	 * 无效定时时间
	 * @throws MessageException
	 */
	public static void invalidTimingTime() throws MessageException{
		throw new MessageException(403, "无效定时时间");
	}
	/**
	 * 发送记录不存在
	 * @throws MessageException
	 */
	public static void recordNotExist() throws MessageException{
		throw new MessageException(404, "发送记录不存在");
	}
	/**
	 * 发送消息失败
	 * @throws MessageException
	 */
	public static void sentMessageFail() throws MessageException{
		throw new MessageException(405, "发送消息失败");
	}
	/**
	 * 消息模板变量无效
	 * @throws MessageException
	 */
	public static void templateVarInvalid() throws MessageException{
		throw new MessageException(406, "消息模板变量无效");
	}
	/**
	 * 未指定消息接收者
	 * @throws MessageException
	 */
	public static void missingRecipient() throws MessageException{
		throw new MessageException(407, "未指定消息接收者");
	}
}
