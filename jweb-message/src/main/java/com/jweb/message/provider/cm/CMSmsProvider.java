package com.jweb.message.provider.cm;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jweb.common.exception.MyException;
import com.jweb.common.util.HttpUtil;
import com.jweb.common.util.HttpUtil.HttpResult;
import com.jweb.common.util.MobileNumberUtil;
import com.jweb.dao.constant.DatabaseConstant;
import com.jweb.message.base.MessageBody;
import com.jweb.message.base.MessageProvider;
import com.jweb.message.base.MessageResult;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public abstract class CMSmsProvider extends MessageProvider{

	@Override
	protected MessageResult sent(MessageBody body){
		
		Message message = new Message();
        message.setSendTo(body.getRecipient());
        message.setFrom(body.getSender());
        message.setReference(body.getMsgId());
        message.setProductionKey(getAccessKey());
        message.setMessageBody(body.getContent());
        String jsonParam = message.createMessageJsonObject().toString();
        
	    HttpResult result = HttpUtil.builder().url(getUrl()).addParam(jsonParam).post(true).sync();
	    
	    MessageResult msr = new MessageResult();
	    if(result.isOk()){
	    	msr.setStatus(DatabaseConstant.MessageSentStatus.SENTTING);
	    }else{
	    	msr.setStatus(DatabaseConstant.MessageSentStatus.SENT_FAIL);
	    }
	    msr.setResultData(result.getResponse());
	    msr.setReference(message.getReference());
	    
	    if(result.isOk()) {
	    	JSONObject json = toJsonObject(result.getResponse());
	    	String errorCode  = json.getString("errorCode");
	    	switch (errorCode) {
			case "999":
				setAvailable(false);
				break;
			case "101":
				setAvailable(false);
				break;
			case "102":
				setAvailable(false);
				break;
			case "103":
				setAvailable(false);
				break;
			case "301":
				setAvailable(false);
				break;
			case "500":
				setAvailable(false);
				break;
			default:
				setAvailable(true);
				break;
			}
	    }
		return msr;
	}
	
	@Override
	protected List<MessageResult> callback(JSONObject jsonParam) {
		String reference = jsonParam.getString("reference");
		String status = jsonParam.getString("status");
		
		MessageResult msr = new MessageResult();
		if(status.equals("2")){
	    	msr.setStatus(DatabaseConstant.MessageSentStatus.SENT_SUCCESS);
	    }else{
	    	msr.setStatus(DatabaseConstant.MessageSentStatus.SENT_FAIL);
	    }
		msr.setResultData(jsonParam.toString());
		msr.setReference(reference);
		
		List<MessageResult> msrs = new ArrayList<MessageResult>();
		msrs.add(msr);
		
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
        private String productionKey;
        private String from;
        private List<String> sendToList;
        private String messageBody;

        private String reference;
        private int maximumNumberOfMessageParts = 8;
        private int minimumNumberOfMessageParts = 1;

        private static final String MESSAGES ="messages";
        private static final String AUTHENTICATION = "authentication";
        private static final String PRODUCTTOKEN = "producttoken";
        private static final String MSG ="msg";
        private static final String FROM = "from";
        private static final String TO ="to";
        private static final String NUMBER = "number";
        private static final String MINIMUMNUMBEROFMESSAGEPARTS ="minimumNumberOfMessageParts";
        private static final String MAXIMUMNUMBEROFMESSAGEPARTS ="maximumNumberOfMessageParts";
        private static final String BODY = "body";
        private static final String TYPE = "type";
        private static final String CONTENT ="content";
        private static final String REFERENCE ="REFERENCE";

        public void setSendTo(String sendTo) {
            try {
				sendToList = new ArrayList<>();
				if(!sendTo.contains(",")) {
					sendToList.add("00" + MobileNumberUtil.addCountryCode(sendTo));
				}else {
					String[] mobiles = sendTo.split(",");
					for(String mobile : mobiles) {
						if(!isNullOrTrimEmpty(mobile)) {
							sendToList.add("00" + MobileNumberUtil.addCountryCode(mobile));
						}
					}
				}
			} catch (MyException e) {
				log.error("", e);
			}
        }

        public JSONObject createMessageJsonObject(){
            JSONObject result = new JSONObject(true);

            JSONObject messages = new JSONObject(true);

            JSONObject authentication = new JSONObject(true);
            authentication.put(PRODUCTTOKEN,productionKey);
            messages.put(AUTHENTICATION,authentication);

            JSONArray msgArray= new JSONArray();

            JSONObject msg = new JSONObject(true);
            msg.put(FROM,from);


            for(String sendTo : sendToList) {
                JSONObject to = new JSONObject(true);
                to.put(NUMBER, sendTo);
                JSONArray toArray= new JSONArray();
                toArray.add(to);
                msg.put(TO, toArray);
            }
            msg.put(MINIMUMNUMBEROFMESSAGEPARTS,minimumNumberOfMessageParts);
            msg.put(MAXIMUMNUMBEROFMESSAGEPARTS,maximumNumberOfMessageParts);

            JSONObject body = new JSONObject(true);
            body.put(TYPE,"AUTO");
            body.put(CONTENT,messageBody);
            msg.put(BODY,body);
            msg.put(REFERENCE, reference);

            msgArray.add(msg);

            messages.put(MSG,msgArray);
            result.put(MESSAGES,messages);

            return result;
        }
    }
	
	protected abstract String getUrl();
	
	protected abstract String getAccessKey();
}
