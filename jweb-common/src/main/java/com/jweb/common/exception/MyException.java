package com.jweb.common.exception;

import com.jweb.common.util.I18nUtil;

import lombok.extern.slf4j.Slf4j;
/**
 * 
 * @author 邓超
 *
 * 2022/6/4 12:24
 */
@Slf4j
public abstract class MyException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer bizCode;
	private Integer errCode;
	
	public MyException(Integer errCode, String message, Throwable e){
		super(message, e);
		this.bizCode = bizCode();
		this.errCode = errCode;
		
	}
	public MyException(Integer errCode, String message){
		super(message);
		this.bizCode = bizCode();
		this.errCode = errCode;
	}
	
	public abstract int bizCode();

	public Integer getBizCode() {
		return bizCode;
	}


	public void setBizCode(Integer bizCode) {
		this.bizCode = bizCode;
	}


	public Integer getErrCode() {
		return errCode;
	}

	public void setErrCode(Integer errCode) {
		this.errCode = errCode;
	}
	
	/**
	 * 获取国际化message
	 * @return
	 */
	public String getI18nMessage() {
		try {
			String i18nMessage = I18nUtil.getMessage(bizCode+"."+errCode);
			return i18nMessage;
		} catch (Exception e) {
			log.error("", e);
			return this.getMessage();
		}
		
	}
}
