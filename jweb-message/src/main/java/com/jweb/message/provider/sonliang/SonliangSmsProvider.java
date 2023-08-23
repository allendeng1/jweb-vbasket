package com.jweb.message.provider.sonliang;


import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

import com.alibaba.fastjson.JSONObject;
import com.jweb.common.exception.MyException;
import com.jweb.common.util.HttpUtil;
import com.jweb.common.util.HttpUtil.HttpResult;
import com.jweb.common.util.MobileNumberUtil;
import com.jweb.dao.constant.DatabaseConstant;
import com.jweb.message.base.MessageBody;
import com.jweb.message.base.MessageChannel;
import com.jweb.message.base.MessageProvider;
import com.jweb.message.base.MessageResult;

import lombok.extern.log4j.Log4j2;

@Component
@Log4j2
@PropertySource(ignoreResourceNotFound = false, value = "classpath:msgconfig.properties")
public class SonliangSmsProvider extends MessageProvider{
	
	@Value("${sonliang.sms.url:}")
	private String url;
	@Value("${sonliang.sms.appid:}")
	private String appId;
	@Value("${sonliang.sms.apiKey:}")
	private String apiKey;
	@Value("${sonliang.sms.apiSecret:}")
	private String apiSecret;
	
	@Override
	public MessageChannel getChannel() {
		return MessageChannel.SONLIANG_SMS;
	}

	@Override
	protected MessageResult sent(MessageBody body){
		MessageResult msr = new MessageResult();
		String mobile;
		try {
			mobile = MobileNumberUtil.addCountryCode(body.getRecipient());
		} catch (MyException e) {
			log.error("发送短信失败{}", body.getRecipient(), e);
			msr.setStatus(DatabaseConstant.MessageSentStatus.SENT_FAIL);
			msr.setResultData("号码不正确");
			return msr;
		}
		
		JSONObject data = new JSONObject();
		data.put("appId", appId);
		data.put("numbers", mobile);
		data.put("content", body.getContent());
		
		final String timestamp = String.valueOf(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().getEpochSecond());
        String sign = apiKey+apiSecret+timestamp;
        sign = DigestUtils.md5DigestAsHex(sign.getBytes());
		
	    HttpResult result = HttpUtil.builder().url(url)
	    							.addHeader("Content-Type", "application/json;charset=UTF-8")
	    							.addHeader("Api-Key", apiKey)
	    							.addHeader("Timestamp", timestamp)
	    							.addHeader("Sign", sign)
	    							.addParam(data.toJSONString())
	    							.post(true).sync();
	    
	    if(result.isOk()){
	    	msr.setStatus(DatabaseConstant.MessageSentStatus.SENTTING);
	    }else{
	    	msr.setStatus(DatabaseConstant.MessageSentStatus.SENT_FAIL);
	    }
	    msr.setResultData(result.getResponse());
	    
	    if(result.isOk()) {
	    	JSONObject json = toJsonObject(result.getResponse());
	    	int code = json.getIntValue("status");
            if(code == 0) {
            	 if(json.getIntValue("success") > 0) {
            		 String refMsgId = json.getJSONArray("array").getJSONObject(0).getString("msgId");
            		 msr.setReference(refMsgId);
            	 }else {
            		 msr.setStatus(DatabaseConstant.MessageSentStatus.SENT_FAIL);
            	 }
             }else {
            	 msr.setStatus(DatabaseConstant.MessageSentStatus.SENT_FAIL);
            	 if(code == -3) {
            		 log.info("sonliang sms 包含敏感词 {} {}", mobile, body.getContent());
            	 }else if(code == -5) {
            		 log.info("sonliang sms 内容过长 {} {}", mobile, body.getContent());
            	 }else if(code == -8) {
            		 log.info("sonliang sms 号码为空 {} {}", mobile, body.getContent());
            	 }else if(code == -9) {
            		 log.info("sonliang sms 号码异常 {} {}", mobile, body.getContent());
            	 }else if(code == -10) {
            		 
            		 setAvailable(false);
 		        	//todo 账号余额不足
            	 }else if(code == -13) {
            		 setAvailable(false);
 		        	//todo 账号被锁定
            	 }else if(code == -28) {
            		 log.info("sonliang sms 号码发送限制 {} {}", mobile, body.getContent());
            	 }
             }
	    	
	    }
		return msr;
	}
	
	@Override
	protected List<MessageResult> callback(JSONObject jsonParam) {
		List<MessageResult> msrs = new ArrayList<MessageResult>();
		MessageResult msr = new MessageResult();
		String reference = jsonParam.getString("msgid");
		int code = jsonParam.getIntValue("status");
	    if(code == 0) {
	    	msr.setStatus(DatabaseConstant.MessageSentStatus.SENT_SUCCESS);
	    }else {
	    	msr.setStatus(DatabaseConstant.MessageSentStatus.SENT_FAIL);
	    	msr.setErrMsg(jsonParam.getString("reason"));
	    	msr.setErrCode(code+"");
	    	if(code == -3) {
       		 	log.info("sonliang sms 包含敏感词 "+reference);
	       	 }else if(code == -5) {
	       		 log.info("sonliang sms 内容过长 "+reference);
	       	 }else if(code == -8) {
	       		 log.info("sonliang sms 号码为空 "+reference);
	       	 }else if(code == -9) {
	       		 log.info("sonliang sms 号码异常 "+reference);
	       	 }else if(code == -10) {
	       		 setAvailable(false);
		         //todo 账号余额不足
	       	 }else if(code == -13) {
	       		 setAvailable(false);
		          //todo 账号被锁定
	       	 }else if(code == -28) {
	       		 log.info("sonliang sms 号码发送限制 "+reference);
	       	 }
	    }
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

}
