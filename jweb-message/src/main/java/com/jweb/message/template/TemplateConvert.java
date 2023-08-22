package com.jweb.message.template;

import java.util.List;


import org.springframework.stereotype.Component;

import com.jweb.common.exception.MessageException;
import com.jweb.common.exception.MyException;
import com.jweb.common.util.DataUtil;
import com.jweb.message.base.MessageBody;

/**
 * 消息模板转换器
 * @author 邓超
 *
 * 2022/09/02 下午6:39:32
 */
@Component
public class TemplateConvert extends DataUtil{
	
	public MessageBody transform(String template, TemplateParam param) throws MyException{
		MessageBody body = new MessageBody();
		
		List<String> variables = findList(template, Patter.MESSAGE_TEMPLATE_VARIABLE, true);
		if(isNullOrEmpty(variables)){//没有变量
			body.setContent(template);
			return body;
		}
		for(String variable : variables){
			if(param.containsKey(variable)){
				template = template.replaceAll(variable.replaceAll("\\{", "\\\\{"), param.get(variable).toString());
			}else{
				MessageException.templateVarNotConvert();
			}
		}
		
		body.setContent(template);
		return body;
	}
}
