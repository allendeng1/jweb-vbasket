package com.jweb.common.api;

import java.io.Serializable;

import lombok.Data;

/**
 * 
 * @author 邓超
 *
 * 2022/09/24 下午3:19:02
 */
@Data
public class KeyValueVo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String key;
	
	private Object value;
	
	public KeyValueVo(String key, Object value) {
		this.key = key;
		this.value = value;
	}

}
