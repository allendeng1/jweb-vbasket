package com.jweb.message.base;

import java.util.ArrayList;
import java.util.List;

import com.jweb.common.api.KeyValueVo;
import com.jweb.common.util.I18nUtil;

/**
 * 消息类型定义
 * @author 邓超
 *
 * 2022/08/26 下午3:27:03
 */
public enum MessageType {

	/**
	 * Websocket 消息
	 */
	WEBSOCKET("1000"),
	/**
	 * 注册验证码
	 */
	REGIST_VERIFY_CODE("1001"),
	/**
	 * 登录验证码
	 */
	LOGIN_VERIFY_CODE("1002");
	
	private String code;
	
	MessageType (String code){
		this.code = code;
	}

	public String getCode() {
		return code;
	}
	
	public static List<KeyValueVo> getSelectOptions(){
		List<KeyValueVo> vos = new ArrayList<KeyValueVo>();
		for (MessageType type : MessageType.values()) {
			vos.add(new KeyValueVo(type.name(), I18nUtil.getMessage(type.name())));
        }
		return vos;
	}
	
}
