package com.jweb.common.util;

import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.stereotype.Component;
/**
 * 
 * @author 邓超
 *
 * 2022/6/4 12:24
 */
@Component
public class I18nUtil extends DataUtil implements MessageSourceAware{
	
	private static MessageSource messageSource;
	
	
	@Override
	public void setMessageSource(MessageSource messageSource) {
		I18nUtil.messageSource = messageSource;
	}

	public static String getMessage(String code){
		return messageSource.getMessage(code, null, LocaleUtil.getLocale());
	}
}
