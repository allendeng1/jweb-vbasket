package com.jweb.message.template;

import java.util.ArrayList;
import java.util.List;

import com.jweb.common.api.KeyValueVo;
import com.jweb.common.exception.MessageException;
import com.jweb.common.exception.MyException;
import com.jweb.common.util.DataUtil;
import com.jweb.common.util.I18nUtil;
import com.jweb.common.util.RegularMatchUtil;
import com.jweb.common.util.RegularMatchUtil.Patter;

/**
 * 消息模板变量名定义
 * @author 邓超
 *
 * 2022/09/02 下午4:49:55
 */
public enum TemplateVariable {
	
	/**
	 * 消息签名
	 */
	SIGNNAME,
	/**
	 * 客服热线
	 */
	CONSUMER_HOTLINE,
	/**
	 * 广告链接
	 */
	AD_LINK,
	/**
	 * 验证码
	 */
	VERIFY_CODE,
	/**
	 * Websocket 消息类型
	 */
	WS_MSG_TYPE,
	/**
	 * Websocket 消息内容
	 */
	WS_MSG_CONTENT,
	;

	public static List<KeyValueVo> getSelectOptions(){
		List<KeyValueVo> vos = new ArrayList<KeyValueVo>();
		for (TemplateVariable variable : TemplateVariable.values()) {
			vos.add(new KeyValueVo("{"+variable.name()+"}", I18nUtil.getMessage(variable.name())));
        }
		return vos;
	}
	
	/**
	 * 校验消息模板中定义的变量
	 * @param template 消息模板
	 * @throws MyException 
	 */
	public static void checkMessageVariable(String template) throws MyException {
		List<String> variables = RegularMatchUtil.findList(template, Patter.MESSAGE_TEMPLATE_VARIABLE, true);
		if(DataUtil.isNullOrEmpty(variables)){//没有变量
			return;
		}
		List<String> validVariables = new ArrayList<String>();
		for (TemplateVariable variable : TemplateVariable.values()) {
			validVariables.add("{"+variable.name()+"}");
        }
		for(String variable : variables) {
			if(!validVariables.contains(variable)) {
				MessageException.templateVarInvalid();
			}
		}
	}
}
