package com.jweb.message.provider.xuerong;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.jweb.common.exception.MyException;
import com.jweb.common.util.HttpUtil;
import com.jweb.common.util.HttpUtil.HttpResult;
import com.jweb.common.util.MobileNumberUtil;
import com.jweb.dao.constant.DatabaseConstant;
import com.jweb.message.base.MessageBody;
import com.jweb.message.base.MessageProvider;
import com.jweb.message.base.MessageResult;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public abstract class XuerongSmsProvider extends MessageProvider{

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
	    HttpResult result = HttpUtil.builder().url(getUrl())
	    		.addParam("sp_id",getSpid())
	    		.addParam("password",getPassword())
	    		.addParam("mobile",mobile)
	    		.addParam("content",body.getContent())
	    		.post(true).sync();
	    
	    
	    if(result.isOk()){
	    	msr.setStatus(DatabaseConstant.MessageSentStatus.SENTTING);
	    }else{
	    	msr.setStatus(DatabaseConstant.MessageSentStatus.SENT_FAIL);
	    }
	    msr.setResultData(result.getResponse());
	    
	    if(result.isOk()) {
	    	JSONObject json = toJsonObject(result.getResponse());
	    	int code = json.getIntValue("code");
	        if(code == 0) {
	        	msr.setReference(json.getString("msg_id"));
	        }else {
	        	msr.setStatus(DatabaseConstant.MessageSentStatus.SENT_FAIL);
		        if(code == 10008) {
		        	setAvailable(false);
		        	//todo 账号被锁定
		        }else if(code == 10011) {
		        	setAvailable(false);
		        	//todo 账号余额不足
		        }
	        }
	    }
		return msr;
	}
	
	@Override
	protected List<MessageResult> callback(JSONObject jsonParam) {
		List<MessageResult> msrs = new ArrayList<MessageResult>();
		String result = jsonParam.getString("result");
		String[] reports = result.split("\\|");
    	for(String report : reports) {
    		String[] infos = report.split(",");
    		String reference = infos[1];
    		String status = infos[3];
    		
    		MessageResult msr = new MessageResult();
    		msr.setResultData(report);
    		msr.setReference(reference);
    		
            if("DELIVRD".equals(status)) {
            	msr.setStatus(DatabaseConstant.MessageSentStatus.SENT_SUCCESS);
            }else {
                msr.setStatus(DatabaseConstant.MessageSentStatus.SENT_FAIL);
            }
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
	
	protected abstract String getUrl();
	
	protected abstract String getSpid();
	
	protected abstract String getPassword();
}
