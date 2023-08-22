package com.jweb.message.provider.infobip;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jweb.common.util.HttpUtil;
import com.jweb.common.util.HttpUtil.HttpResult;
import com.jweb.dao.constant.DatabaseConstant;
import com.jweb.message.base.MessageBody;
import com.jweb.message.base.MessageChannel;
import com.jweb.message.base.MessageProvider;
import com.jweb.message.base.MessageResult;

import lombok.Data;

@Component
public class InfobipSmsProvider extends MessageProvider{
	
	@Value("${infobip.sms.url:}")
	private String url;
	@Value("${infobip.sms.apiKey:}")
	private String apiKey;
	
	@Override
	public MessageChannel getChannel() {
		return MessageChannel.INFOBIP_SMS;
	}

	@Override
	protected MessageResult sent(MessageBody body){
		
		Message message = new Message();
		message.setBulkId(body.getMsgId());
        message.setTo(body.getRecipient());
        message.setFrom(body.getSender());
        message.setText(body.getContent());
        String jsonParam = message.createMessageJsonObject().toString();
        
	    HttpResult result = HttpUtil.builder().url(url)
	    							.addHeader("Content-Type", "application/json")
	    							.addHeader("Authorization", "App "+apiKey)
	    							.addParam(jsonParam)
	    							.post(true).sync();
	    
	    MessageResult msr = new MessageResult();
	    if(result.isOk()){
	    	msr.setStatus(DatabaseConstant.MessageSentStatus.SENTTING);
	    }else{
	    	msr.setStatus(DatabaseConstant.MessageSentStatus.SENT_FAIL);
	    }
	    msr.setResultData(result.getResponse());
	    
	    if(result.isOk()) {
	    	JSONObject json = toJsonObject(result.getResponse());
	    	if(json.containsKey("requestError")) {
	    		setAvailable(false);
	    		msr.setStatus(DatabaseConstant.MessageSentStatus.SENT_FAIL);
	    		msr.setErrMsg(json.getJSONObject("serviceException").getString("text"));
	    	}else {
	    		JSONObject messageJson = json.getJSONArray("messages").getJSONObject(0);
	    		msr.setReference(messageJson.getString("messageId"));
	    		JSONObject statusJson = messageJson.getJSONObject("status");
	    		int groupId = statusJson.getIntValue("groupId");
	    		String description = statusJson.getString("description");
	    		switch (groupId) {
				case 1:
					setAvailable(true);
					msr.setStatus(DatabaseConstant.MessageSentStatus.SENTTING);
					break;
				case 2:
					setAvailable(false);
					msr.setStatus(DatabaseConstant.MessageSentStatus.SENT_FAIL);
					msr.setErrMsg(description);
					break;
				case 3:
					setAvailable(true);
					msr.setStatus(DatabaseConstant.MessageSentStatus.SENT_SUCCESS);
					break;
				case 4:
					msr.setStatus(DatabaseConstant.MessageSentStatus.SENT_FAIL);
					msr.setErrMsg(description);
					break;
				case 5:
					setAvailable(false);
					msr.setStatus(DatabaseConstant.MessageSentStatus.SENT_FAIL);
					msr.setErrMsg(description);
					break;
				default:
					setAvailable(true);
					break;
				}
	    	}
	    }
		return msr;
	}
	
	@Override
	protected List<MessageResult> callback(JSONObject jsonParam) {
		List<MessageResult> msrs = new ArrayList<MessageResult>();
		JSONArray results = jsonParam.getJSONArray("results");
		for(int i=0;i<results.size();i++) {
			JSONObject result = results.getJSONObject(i);
			String reference = result.getString("messageId");
			JSONObject status = result.getJSONObject("status");
			int groupId = status.getIntValue("groupId");
			
			MessageResult msr = new MessageResult();
			if(groupId == 3){
		    	msr.setStatus(DatabaseConstant.MessageSentStatus.SENT_SUCCESS);
		    }else{
		    	msr.setStatus(DatabaseConstant.MessageSentStatus.SENT_FAIL);
		    }
			msr.setResultData(result.toString());
			msr.setReference(reference);
			msrs.add(msr);
		}
		return msrs;
	}

	@Override
	protected String formatMessage(String message) {
		return message.replaceAll("Ñ", "N").replaceAll("ñ", "n").replaceAll("Á", "A").replaceAll("á", "a")
  			  .replaceAll("É", "E").replaceAll("é", "e").replaceAll("Ó", "O").replaceAll("ó", "o")
  			  .replaceAll("Í", "I").replaceAll("í", "i").replaceAll("Ú", "U").replaceAll("ú", "u");
	}

	@Override
	protected boolean isWarning() {
		return true;
	}
	@Override
	protected boolean isCheckRecipient() {
		return true;
	}
	@Data
    public static class Message{
		private String bulkId;
		private String from;
		private String to;
		private String text;
		private String notifyUrl;

        public JSONObject createMessageJsonObject(){
        	
        	JSONObject message = new JSONObject();
        	message.put("from", from);
        	message.put("text", text);
        	if(!isNullOrTrimEmpty(notifyUrl)) {
        		message.put("notifyUrl", notifyUrl);
        	}
        	
        	JSONObject destination = new JSONObject();
        	destination.put("to", to);
        	JSONArray destinations = new JSONArray();
        	destinations.add(destination);
        	
        	message.put("destinations", destinations);
        	
        	JSONArray messages = new JSONArray();
        	messages.add(message);
        	
        	JSONObject bulk = new JSONObject();
        	bulk.put("bulkId", bulkId);
        	bulk.put("messages", messages);
        	
            return bulk;
        }
    }
	
}
