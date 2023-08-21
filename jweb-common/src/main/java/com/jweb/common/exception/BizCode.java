package com.jweb.common.exception;
/**
 * 
 * @author 邓超
 *
 * 2022/6/4 12:24
 */
public enum BizCode {
	
	COMMON_EXCEPTION(101, "公共异常"),
	AUTH_EXCEPTION(201, "认证授权异常"),
	JOB_EXCEPTION(301, "定时任务异常"),
	DEPARTMENT_EXCEPTION(401, "部门异常"),
	ROLE_EXCEPTION(501, "角色异常"),
	MENUOPERA_EXCEPTION(601, "菜单异常"),
	MQ_EXCEPTION(701, "MQ消息异常"),
	MESSAGE_EXCEPTION(801, "消息异常"),
	SEARCH_ENGINE_EXCEPTION(901, "搜索引擎异常"),
	FILE_EXCEPTION(1001, "文件异常");

	private int code;
	private String descr;
	
	BizCode(int code, String descr){
		this.code = code;
		this.descr = descr;
	}

	public int getCode() {
		return code;
	}

	public String getDescr() {
		return descr;
	}

	
}
