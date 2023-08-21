package com.jweb.dao.dto;

import java.io.Serializable;

import lombok.Data;
/**
 * 
 * @author 邓超
 *
 * 2022/6/4 12:24
 */
@Data
public class MqQueueCountDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String queueName;
	private Integer msgSize;
}
