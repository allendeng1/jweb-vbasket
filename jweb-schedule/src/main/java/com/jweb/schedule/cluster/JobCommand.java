package com.jweb.schedule.cluster;

import java.io.Serializable;


import lombok.Data;
/**
 * 
 * @author 邓超
 *
 * 2022/6/4 12:24
 */
@Data
public class JobCommand implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String nodeId;
	
	private CommandType type;
	
	private Long adminUserId;
	
	private Long taskId;
	
}
