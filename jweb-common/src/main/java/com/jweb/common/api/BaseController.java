package com.jweb.common.api;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import com.jweb.common.exception.MyException;
import com.jweb.common.token.JwtToken;
import com.jweb.common.token.TokenData;
import com.jweb.common.util.DataUtil;
/**
 * 
 * @author 邓超
 *
 * 2022/6/4 12:24
 */
public class BaseController extends DataUtil{
	
	private static final String USER_ID = "USER_ID";
	private static final String SAAS_ID = "SAAS_ID";
	
	public static void checkAuthToken(String token) throws MyException{
		TokenData authData = JwtToken.checkToken(token);
		saveAuthData(authData);
	}
	
	private static void saveAuthData(TokenData authData){
		RequestAttributes attr = RequestContextHolder.getRequestAttributes();
		attr.setAttribute(USER_ID, authData.getUserId(), RequestAttributes.SCOPE_REQUEST);
		attr.setAttribute(SAAS_ID, authData.getSaasId(), RequestAttributes.SCOPE_REQUEST);
	}
	
	/**
	 * 获取用户ID
	 * @return
	 */
	public static Long getUserId(){
		Object obj = RequestContextHolder.getRequestAttributes().getAttribute(USER_ID, RequestAttributes.SCOPE_REQUEST);
    	return isNull(obj) ? null : Long.valueOf(obj.toString());
	}
	/**
	 * 获取saasId
	 * @return
	 */
	public static Long getSaasId(){
		Object obj = RequestContextHolder.getRequestAttributes().getAttribute(SAAS_ID, RequestAttributes.SCOPE_REQUEST);
    	return isNull(obj) ? null : Long.valueOf(obj.toString());
	}
}
