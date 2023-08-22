package com.jweb.message.template;

import java.util.HashMap;

/**
 * 消息模板参数
 * @author 邓超
 *
 * 2022/09/02 下午5:51:22
 */
public class TemplateParam extends HashMap<String, Object>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public TemplateParam add(TemplateVariable variableName, Object value) {
		put("{"+variableName.name()+"}", value);
		return this;
	}
}
