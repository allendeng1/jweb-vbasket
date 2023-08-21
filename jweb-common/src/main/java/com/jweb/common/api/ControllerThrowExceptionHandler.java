package com.jweb.common.api;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.jweb.common.exception.MyException;

/**
 * Controller抛出异常处理
 * 
 * <p>为了处理Controller中的方法抛出异常，能正常返回异常信息
 * 
 * @author allen
 *
 */
@ResponseBody
@ControllerAdvice
public class ControllerThrowExceptionHandler {

	@ResponseStatus(HttpStatus.OK)
	@ExceptionHandler(MyException.class)
	public ApiResult paramErrorServletRequest(MyException ex){
		ApiResult resutl = new ApiResult();
		resutl.bizFail(ex);
		return resutl;
	}
}
