package com.jweb.message.provider.xuerong;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.jweb.message.base.MessageChannel;

@Component
public class XuerongSmsVerifyCodeProvider extends XuerongSmsProvider {

	@Value("${xuerong.sms.url:}")
	private String url;
	@Value("${xuerong.sms.verifycode.spid:}")
	private String spid;
	@Value("${xuerong.sms.verifycode.password:}")
	private String password;
	
	@Override
	public MessageChannel getChannel() {
		return MessageChannel.XUERONG_SMS_VERIFY_CODE;
	}

	@Override
	protected String getUrl() {
		return url;
	}

	@Override
	protected String getSpid() {
		return spid;
	}
	@Override
	protected String getPassword() {
		return password;
	}

}
