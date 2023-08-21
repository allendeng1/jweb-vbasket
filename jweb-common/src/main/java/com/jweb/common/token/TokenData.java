package com.jweb.common.token;

import java.io.Serializable;

import lombok.Data;
/**
 * 
 * @author 邓超
 *
 * 2022/6/4 12:24
 */


@Data
public class TokenData implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private long userId;
	private long saasId;
}
