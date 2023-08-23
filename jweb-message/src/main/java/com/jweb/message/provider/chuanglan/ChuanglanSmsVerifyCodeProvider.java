package com.jweb.message.provider.chuanglan;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.jweb.common.exception.MyException;
import com.jweb.common.util.DateTimeUtil;
import com.jweb.common.util.MobileNumberUtil;
import com.jweb.message.base.MessageChannel;

@Component
@PropertySource(ignoreResourceNotFound = false, value = "classpath:msgconfig.properties")
public class ChuanglanSmsVerifyCodeProvider extends ChuanglanSmsProvider {

	@Value("${chuanglan.sms.verifycode.url:}")
	private String url;
	@Value("${chuanglan.sms.verifycode.account:}")
	private String account;
	@Value("${chuanglan.sms.verifycode.password:}")
	private String password;
	
	@Override
	public MessageChannel getChannel() {
		return MessageChannel.CHUANGLAN_SMS_VERIFY_CODE;
	}

	@Override
	protected Map<String, Object> buildRequest(String smsId, String recipient, String message) throws MyException{
		String mobile = MobileNumberUtil.addCountryCode(recipient);
	    String nonce = DateTimeUtil.nowTime()+"";
	    String uid = smsId;
	        
	    JSONObject json = new JSONObject();
    	json.put("account", account);
    	json.put("mobile", mobile);
    	json.put("msg", message);
    	json.put("uid", uid);
    	
		StringBuilder builder = new StringBuilder();
        builder.append("account").append(account);
        builder.append("mobile").append(mobile);
        builder.append("msg").append(message);
        builder.append("nonce").append(nonce);
        builder.append("uid").append(uid);
    	builder.append(password);
    	String sign = DigestUtils.md5Hex(builder.toString().getBytes());
    	 
    	Map<String, String> headers = new HashMap<String, String>();
    	headers.put("sign", sign);
    	headers.put("nonce", nonce);
        
        Map<String, Object> request = new HashMap<String, Object>();
        request.put("jsonParam", json.toString());
        request.put("header", headers);
        
        return request;
	}

	@Override
	protected String getUrl() {
		return url;
	}
}
