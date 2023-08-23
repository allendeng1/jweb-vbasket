package com.jweb.message.provider.cm;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import com.jweb.message.base.MessageChannel;

@Component
@PropertySource(ignoreResourceNotFound = false, value = "classpath:msgconfig.properties")
public class CMSmsMarketingProvider extends CMSmsProvider {

	@Value("${cm.sms.url:}")
	private String url;
	@Value("${cm.sms.marketing.channel.accessKey:}")
	private String accessKey;
	
	@Override
	public MessageChannel getChannel() {
		return MessageChannel.CM_SMS_MARKETING;
	}

	@Override
	protected String getUrl() {
		return url;
	}

	@Override
	protected String getAccessKey() {
		return accessKey;
	}

}
