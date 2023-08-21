package com.jweb.common.exception;
/**
 * 
 * @author 邓超
 *
 * 2022/6/4 12:24
 */
public class AuthException extends MyException{

	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public int bizCode() {
		return BizCode.AUTH_EXCEPTION.getCode();
	}
	public AuthException(Integer errCode, String message) {
		super(errCode, message);
	}
	private AuthException(Integer errCode, String message, Throwable e) {
		super(errCode, message, e);
	}
	
	/**
	 * TOKEN 已过期
	 * @throws AuthException
	 */
	public static void tokenExpired()throws AuthException{
		throw new AuthException(200, "token已过期");
	}
	/**
	 * 无效 TOKEN
	 * @throws AuthException
	 */
	public static void invalidToken()throws AuthException{
		throw new AuthException(201, "无效token");
	}
}
