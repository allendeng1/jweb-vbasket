package com.jweb.message.provider.aliyun;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.aliyun.dysmsapi20170525.models.SendSmsResponse;
import com.aliyun.dysmsapi20170525.models.SendSmsResponseBody;
import com.aliyun.tea.TeaException;
import com.aliyun.tea.TeaUnretryableException;
import com.aliyun.tea.ValidateException;
import com.aliyun.teaopenapi.models.Config;
import com.jweb.dao.constant.DatabaseConstant;
import com.jweb.message.base.MessageBody;
import com.jweb.message.base.MessageChannel;
import com.jweb.message.base.MessageProvider;
import com.jweb.message.base.MessageResult;

import lombok.Data;

@Component
@PropertySource(ignoreResourceNotFound = false, value = "classpath:msgconfig.properties")
public class AliyunSmsProvider extends MessageProvider{
	
	@Value("${alyun.sms.url:}")
	private String url;
	@Value("${alyun.sms.accessKeyId:}")
	private String accessKeyId;
	@Value("${alyun.sms.accessKeySecret:}")
	private String accessKeySecret;
	
	private Config config = null;
	
	@Override
	public MessageChannel getChannel() {
		return MessageChannel.ALIYUN_SMS;
	}

	@Override
	protected MessageResult sent(MessageBody body){
		if(config == null) {
			synchronized (config) {
				if(config == null) {
					config = new Config()
			                .setAccessKeyId(accessKeyId)
			                .setAccessKeySecret(accessKeySecret);
					config.endpoint = url;
				}
			}
		}
		MessageResult msr = new MessageResult();
		try {
			com.aliyun.dysmsapi20170525.Client smsClient = new com.aliyun.dysmsapi20170525.Client(config);
			com.aliyun.dysmsapi20170525.models.SendSmsRequest sendSmsRequest = new com.aliyun.dysmsapi20170525.models.SendSmsRequest()
			            .setPhoneNumbers(body.getRecipient())
			            .setSignName(body.getSender())
			            .setTemplateParam(body.getContent())
			            .setTemplateCode(body.getTemplateId());
			
			com.aliyun.teautil.models.RuntimeOptions runtime = new com.aliyun.teautil.models.RuntimeOptions();
			SendSmsResponse response = smsClient.sendSmsWithOptions(sendSmsRequest, runtime);
			SendSmsResponseBody result = response.getBody();
			msr.setResultData(toJsonString(result));
			if(isEqualIgnoreCase(result.getCode(), "OK")) {
				msr.setStatus(DatabaseConstant.MessageSentStatus.SENT_SUCCESS);
				msr.setReference(result.getBizId());
			}else {
				msr.setStatus(DatabaseConstant.MessageSentStatus.SENT_FAIL);
			}
			setAvailable(true);
		}catch(ValidateException e) {
			setAvailable(false);
			msr.setStatus(DatabaseConstant.MessageSentStatus.SENT_FAIL);
    		msr.setErrMsg(e.getMessage());
	    } catch(TeaUnretryableException e) {
	    	setAvailable(false);
			msr.setStatus(DatabaseConstant.MessageSentStatus.SENT_FAIL);
    		msr.setErrMsg(e.getMessage());
	    } catch (TeaException e) {
	    	setAvailable(false);
			msr.setStatus(DatabaseConstant.MessageSentStatus.SENT_FAIL);
    		msr.setErrMsg(e.getMessage());
	    } catch (Exception e) {
	    	msr.setStatus(DatabaseConstant.MessageSentStatus.SENT_FAIL);
		}
		return msr;
	}
	
	@Override
	protected List<MessageResult> callback(JSONObject jsonParam) {
		return null;
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
