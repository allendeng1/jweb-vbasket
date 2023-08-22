package com.jweb.message.provider.chuanglan;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.jweb.common.exception.MyException;
import com.jweb.common.util.HttpUtil;
import com.jweb.common.util.HttpUtil.HttpResult;
import com.jweb.dao.constant.DatabaseConstant;
import com.jweb.message.base.MessageBody;
import com.jweb.message.base.MessageProvider;
import com.jweb.message.base.MessageResult;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public abstract class ChuanglanSmsProvider extends MessageProvider{
	
	@Override
	protected MessageResult sent(MessageBody body) {
		MessageResult msr = new MessageResult();
		try {
			Map<String, Object> request = buildRequest(body.getMsgId(), body.getRecipient(), body.getContent());
			
			@SuppressWarnings("unchecked")
			HttpResult result = HttpUtil.builder().url(getUrl())
					.addHeader((Map<String, String>)request.get("header"))
					.addParam(request.get("jsonParam").toString()).post(true).sync();
			
		    if(result.isOk()){
		    	msr.setStatus(DatabaseConstant.MessageSentStatus.SENTTING);
		    }else{
		    	msr.setStatus(DatabaseConstant.MessageSentStatus.SENT_FAIL);
		    }
		    msr.setResultData(result.getResponse());
		    
		    if(result.isOk()) {
		    	JSONObject json = toJsonObject(result.getResponse());
		    	String code = json.getString("code");
	            if(!"0".equals(code)) {
	            	msr.setStatus(DatabaseConstant.MessageSentStatus.SENT_FAIL);
	            	msr.setErrCode(code);
	            	msr.setErrMsg(json.getString("errorMsg"));
	            }else {
	            	msr.setReference(json.getJSONObject("data").getString("messageId"));
	            }
		    	switch (code) {
				case "101":
					setAvailable(false);
					break;
				case "102":
					setAvailable(false);
					break;
				case "103":
					setAvailable(false);
					break;
				case "104":
					setAvailable(false);
					break;
				case "109":
					setAvailable(false);
					break;
				case "117":
					setAvailable(false);
					break;
				case "134":
					setAvailable(false);
					break;
				default:
					setAvailable(true);
					break;
				}
		    }
		} catch (MyException e) {
			log.error("", e);
			msr.setStatus(DatabaseConstant.MessageSentStatus.SENT_FAIL);
		}
		return msr;
	}

	@Override
	protected List<MessageResult> callback(JSONObject jsonParam) {
		String reference = jsonParam.getString("msgid");
		String status = jsonParam.getString("status");
		
		MessageResult msr = new MessageResult();
		if(status.equalsIgnoreCase("DELIVRD")){
	    	msr.setStatus(DatabaseConstant.MessageSentStatus.SENT_SUCCESS);
	    }else{
	    	msr.setStatus(DatabaseConstant.MessageSentStatus.SENT_FAIL);
	    	msr.setErrCode("DELIVRD");
	    	if(status.equalsIgnoreCase("UNKNOWN")) {
	    		msr.setErrMsg("未知短信状态");
	    	}else if(status.equalsIgnoreCase("REJECTD")) {
	    		msr.setErrMsg("短信被短信中心拒绝");
	    	}else if(status.equalsIgnoreCase("MBBLACK")) {
	    		msr.setErrMsg("目的号码是黑名单号码");
	    	}else if(status.equalsIgnoreCase("SM11")) {
	    		msr.setErrMsg("网关验证号码格式错误");
	    	}else if(status.equalsIgnoreCase("SM12")) {
	    		msr.setErrMsg("我方验证号码格式错误");
	    	}else {
	    		msr.setErrMsg("网关内部状态");
	    	}
	    }
		msr.setResultData(jsonParam.toString());
		msr.setReference(reference);
		
		List<MessageResult> msrs = new ArrayList<MessageResult>();
		msrs.add(msr);
		
		return msrs;
	}

	@Override
	protected String formatMessage(String message) {
		return message;
	}

	@Override
	protected boolean isWarning() {
		return true;
	}
	@Override
	protected boolean isCheckRecipient() {
		return true;
	}
	protected abstract String getUrl();
	
	protected abstract Map<String, Object> buildRequest(String smsId, String recipient, String message)throws MyException;

}
