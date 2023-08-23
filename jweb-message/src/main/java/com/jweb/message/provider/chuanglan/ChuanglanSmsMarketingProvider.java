package com.jweb.message.provider.chuanglan;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.jweb.common.exception.MyException;
import com.jweb.common.util.MobileNumberUtil;
import com.jweb.message.base.MessageChannel;

@Component
@PropertySource(ignoreResourceNotFound = false, value = "classpath:msgconfig.properties")
public class ChuanglanSmsMarketingProvider extends ChuanglanSmsProvider {

	@Value("${chuanglan.sms.marketing.url:}")
	private String url;
	@Value("${chuanglan.sms.marketing.account:}")
	private String account;
	@Value("${chuanglan.sms.marketing.password:}")
	private String password;
	
	@Override
	public MessageChannel getChannel() {
		return MessageChannel.CHUANGLAN_SMS_MARKETING;
	}

	@Override
	protected Map<String, Object> buildRequest(String smsId, String recipient, String message) throws MyException{
		String mobile = MobileNumberUtil.addCountryCode(recipient);
		JSONObject json = new JSONObject();
    	json.put("account", account);
    	json.put("password", password);
    	json.put("mobile", mobile);
    	json.put("msg", message);
    	
    	Map<String, Object> request = new HashMap<String, Object>();
        request.put("jsonParam", json.toString());

        return request;
	}

	@Override
	protected String getUrl() {
		return url;
	}

}
