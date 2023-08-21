package com.jweb.common.api;

import java.io.Serializable;

import com.jweb.common.exception.MyException;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
/**
 * 
 * @author 邓超
 *
 * 2022/6/4 12:24
 */
@Data
public class ApiResult implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value="业务码：100-业务处理成功，>100业务处理异常")
	private Integer bizcode = 100;
	@ApiModelProperty(value="业务异常码")
	private Integer errcode = 0;
	@ApiModelProperty(value="业务异常信息")
	private String msg="成功";
	@ApiModelProperty(value="数据")
	private Object data;
	@ApiModelProperty(value="总条数")
	private Integer count;

	public Integer getErrcode() {
		return errcode;
	}
	public void setErrcode(Integer errcode) {
		this.errcode = errcode;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Integer getBizcode() {
		return bizcode;
	}
	public void setBizcode(Integer bizcode) {
		this.bizcode = bizcode;
	}
	
	public void bizFail(MyException e){
		this.bizcode = e.getBizCode();
		this.errcode = e.getErrCode();
		this.msg = e.getI18nMessage();
	}
}
