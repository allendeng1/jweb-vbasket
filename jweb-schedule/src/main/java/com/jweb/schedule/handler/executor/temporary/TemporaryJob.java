package com.jweb.schedule.handler.executor.temporary;

import com.jweb.schedule.handler.BaseJob;
/**
 * 
 * @author 邓超
 *
 * 2022/09/09 下午2:18:22
 */
public abstract class TemporaryJob extends BaseJob{
	
	public abstract TemporaryJobType getType();
	
}
