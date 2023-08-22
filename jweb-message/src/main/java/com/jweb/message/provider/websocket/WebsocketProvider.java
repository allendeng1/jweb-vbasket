package com.jweb.message.provider.websocket;

import java.util.List;

import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.jweb.message.base.MessageBody;
import com.jweb.message.base.MessageChannel;
import com.jweb.message.base.MessageProvider;
import com.jweb.message.base.MessageResult;


@Component
@ConditionalOnExpression("${websocket.enable:false}")
public class WebsocketProvider extends MessageProvider{
	
	@Override
	protected MessageResult sent(MessageBody body) {
		MessageResult msr = null;
		if(isNullOrTrimEmpty(body.getRecipient())) {
			msr = WebsocketServer.sentMessage(body.getContent());
		}else {
			msr = WebsocketServer.sentMessage(body.getRecipient(), body.getContent());
		}
		return msr;
	}

	@Override
	protected List<MessageResult> callback(JSONObject jsonParam) {
		return null;
	}
	
	@Override
	protected boolean isWarning() {
		return false;
	}
	@Override
	protected boolean isCheckRecipient() {
		return false;
	}
	@Override
	public MessageChannel getChannel() {
		return MessageChannel.WEBSOCKET;
	}

	@Override
	protected String formatMessage(String message) {
		return message;
	}

}
