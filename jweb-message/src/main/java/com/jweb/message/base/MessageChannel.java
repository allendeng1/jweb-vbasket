package com.jweb.message.base;

import java.util.ArrayList;
import java.util.List;

import com.jweb.common.api.KeyValueVo;
import com.jweb.common.util.I18nUtil;

/**
 * 消息渠道定义
 * @author 邓超
 *
 * 2022/08/26 下午3:28:38
 */
public enum MessageChannel {
	/**
	 * WEBSOCKET 消息
	 */
	WEBSOCKET,
	/**CM验证码短信**/
	CM_SMS_VERIFY_CODE,
	/**CM市场营销短信**/
	CM_SMS_MARKETING,
	/**创蓝验证码短信**/
	CHUANGLAN_SMS_VERIFY_CODE,
	/**创蓝市场营销短信**/
	CHUANGLAN_SMS_MARKETING,
	/**ALIYUN短信**/
	ALIYUN_SMS,
	/**INFOBIP短信**/
	INFOBIP_SMS,
	/**雪融云验证码短信**/
	XUERONG_SMS_VERIFY_CODE,
	/**雪融云市场营销短信**/
	XUERONG_SMS_MARKETING,
	/**颂量短信**/
	SONLIANG_SMS,
	/**EMAIL**/
	EMAIL
	;

	public static List<KeyValueVo> getSelectOptions(){
		List<KeyValueVo> vos = new ArrayList<KeyValueVo>();
		for (MessageChannel channel : MessageChannel.values()) {
			vos.add(new KeyValueVo(channel.name(), I18nUtil.getMessage(channel.name())));
        }
		return vos;
	}
}
