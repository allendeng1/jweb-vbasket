package com.jweb.message.provider.email;

import java.io.File;
import java.util.List;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.jweb.dao.constant.DatabaseConstant;
import com.jweb.message.base.MessageBody;
import com.jweb.message.base.MessageChannel;
import com.jweb.message.base.MessageProvider;
import com.jweb.message.base.MessageResult;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Component
@ConditionalOnExpression("${email.enable:false}")
@PropertySource(ignoreResourceNotFound = false, value = "classpath:msgconfig.properties")
public class EmailProvider extends MessageProvider{

	@Autowired
    private JavaMailSenderImpl mailSender;
	
	@Value("${email.read.status.feedback.url:}")
	private String feedbackUrl;
	
	@Override
	protected MessageResult sent(MessageBody body) {
		MessageResult msr = new MessageResult();
		try {
			String from = mailSender.getUsername();
			MimeMessage mimeMessage = mailSender.createMimeMessage();
			mimeMessage.setFrom(new InternetAddress(from));
			mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(body.getRecipient()));
			mimeMessage.setSubject(body.getSubject());
			
			MimeBodyPart text = new MimeBodyPart();
			
			if(isNullOrTrimEmpty(feedbackUrl)){
				text.setContent(body.getContent(), "text/html;charset=utf-8");
			}else {
				//邮件内容插入logo图片，用于收集邮件是否已读
				text.setContent("<div style='float:left;width:100%;'><img src='"+feedbackUrl.replaceAll("\\{emailId}", body.getMsgId())+"' width='60px' height='60px' style='float:right;'></div>"+body.getContent(), "text/html;charset=utf-8");
			}
			
			MimeMultipart mimeMultipart = new MimeMultipart();
			mimeMultipart.addBodyPart(text);
			mimeMultipart.setSubType("related");//文本和图片内嵌成功
			File[] files = body.getFiles();
			if (!isNullOrEmpty(files)) { // 添加附件（多个）
			    for (File file : files) {
			        MimeBodyPart filePart = new MimeBodyPart();
			        DataHandler dh = new DataHandler(new FileDataSource(file));
			        filePart.setDataHandler(dh);
			        filePart.setFileName(file.getName());
			        mimeMultipart.addBodyPart(filePart);
			    }
			    mimeMultipart.setSubType("mixed");//文本和图片内嵌成功
			}
			 
			mimeMessage.setContent(mimeMultipart);
			mailSender.send(mimeMessage);
			
			msr.setStatus(DatabaseConstant.MessageSentStatus.SENT_SUCCESS);
			
		} catch (AddressException e) {
			log.error("", e);
			msr.setStatus(DatabaseConstant.MessageSentStatus.SENT_FAIL);
			msr.setErrMsg("邮件地址错误");
		} catch (MailException e) {
			log.error("", e);
			msr.setStatus(DatabaseConstant.MessageSentStatus.SENT_FAIL);
			msr.setErrMsg("邮件错误");
		} catch (MessagingException e) {
			log.error("", e);
			msr.setStatus(DatabaseConstant.MessageSentStatus.SENT_FAIL);
			msr.setErrMsg("邮件错误");
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
		return true;
	}

	@Override
	public MessageChannel getChannel() {
		return MessageChannel.EMAIL;
	}

	@Override
	protected String formatMessage(String message) {
		return message;
	}

}
