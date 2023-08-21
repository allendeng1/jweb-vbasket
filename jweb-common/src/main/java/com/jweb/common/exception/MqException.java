package com.jweb.common.exception;
/**
 * 
 * @author 邓超
 *
 * 2022/6/4 12:24
 */
public class MqException extends MyException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public int bizCode() {
		return BizCode.MQ_EXCEPTION.getCode();
	}
	public MqException(Integer errCode, String message) {
		super(errCode, message);
	}
	private MqException(Integer errCode, String message, Throwable e) {
		super(errCode, message, e);
	}
	
	/**
	 * MQ消息不存在
	 * @throws MessageException
	 */
	public static void messageNotExist() throws MqException{
		throw new MqException(700, "消息不存在");
	}
	/**
	 * MQ消息状态异常
	 * @throws MessageException
	 */
	public static void messageStatusError() throws MqException{
		throw new MqException(701, "消息状态异常");
	}
}
