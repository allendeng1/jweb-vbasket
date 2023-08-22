package com.jweb.mq.message.handler;

import org.springframework.stereotype.Component;

import com.jweb.mq.message.MessageHandler;
import com.jweb.mq.message.MessageType;
/**
 * 
 * @author 邓超
 *
 * 2022/6/4 12:24
 */
@Component
public class SentSmsHandler extends MessageHandler{

	@Override
	protected void todo(String content) throws RuntimeException {

		
		
	}

	@Override
	public MessageType getType() {
		return MessageType.SENT_SMS;
	}

	@Override
	protected String getExcuteHandler() {
		return getClass().getName();
	}

}
