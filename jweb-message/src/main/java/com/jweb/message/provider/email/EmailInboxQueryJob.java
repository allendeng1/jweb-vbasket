package com.jweb.message.provider.email;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.Part;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.UIDFolder;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeUtility;
import javax.mail.search.ComparisonTerm;
import javax.mail.search.ReceivedDateTerm;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


import lombok.extern.log4j.Log4j2;

@Log4j2
@Component
@ConditionalOnExpression("${email.enable:false} && '${spring.application.name:}'.equals('jweb-admin')")
@PropertySource(ignoreResourceNotFound = false, value = "classpath:msgconfig.properties")
public class EmailInboxQueryJob {
	
	@Value("${email.imap.host:}")
	private String host;
	@Value("${email.imap.port:}")
	private String port;
	@Value("${email.transport.protocol:}")
	private String protocol;
	@Value("${email.imap.username:}")
	private String username;
	@Value("${email.imap.password:}")
	private String password;
	@Value("${email.imap.query.mode:ID}")
	private String queryMode;
	
	private Date lastDate = null;
	private  Store store = null;
	private Long lastId = null;
	

	@Scheduled(cron = "0/20 * * * * *")
	public void run() {
		log.info("查询邮件开始");
		getStore();
		if(store == null) {
			return;
		}
		Folder folder = null;
		try {
			// 获得收件箱
			folder = store.getFolder("INBOX");
			// 以读写模式打开收件箱
			folder.open(Folder.READ_WRITE);
			UIDFolder uidFolder = (UIDFolder)folder;
			Message[] messages = null;
			if(queryMode.equalsIgnoreCase("time")) {
				if(lastDate == null) {
					log.info("获取邮件起始时间：NULL");
					return;
				}
				log.info("获取邮件起始时间：{}", lastDate);
				ReceivedDateTerm term = new ReceivedDateTerm(ComparisonTerm.GT, lastDate);
				messages = folder.search(term);
			}else if(queryMode.equalsIgnoreCase("id")){
				log.info("获取邮件起始ID：{}", lastId+1);
				messages = uidFolder.getMessagesByUID(lastId+1, UIDFolder.MAXUID);
			}else {
				messages = folder.getMessages();
			}
		
			if(messages == null || messages.length == 0) {
				return;
			}
			for (int i=0;i<messages.length;i++) {
            	 try {
					 Message message = messages[i];
					 String subject = message.getSubject();
					 String fromEmail = MimeUtility.decodeText(message.getFrom()[0].toString());
					 InternetAddress internetAddress = new InternetAddress(fromEmail);
					 String fullName = internetAddress.getPersonal();
					 String email = internetAddress.getAddress();
					 Date sentTime = message.getReceivedDate();
					 long uid = uidFolder.getUID(message);
					 
					 StringBuffer content = new StringBuffer();
					 getContent(message, content);
	
					 lastDate = lastDate == null || lastDate.before(sentTime) ? sentTime : lastDate;
					 lastId = uid;
					 
				} catch (UnsupportedEncodingException e) {
					log.error("获取邮件内容出错", e);
					continue;
				} catch (IOException e) {
					log.error("获取邮件内容出错", e);
					continue;
				}catch (Exception e) {
					log.error("获取邮件内容出错", e);
					continue;
				}
           }

		} catch (MessagingException e) {
			log.error("获取邮件内容出错", e);
		}
		log.info("查询邮件结束");
	}
	public static void getContent(Part part, StringBuffer content) throws MessagingException, IOException {
		boolean isContainTextAttac = part.getContentType().indexOf("name") > 0;
		 if(part.isMimeType("text/*") && !isContainTextAttac) {
			 content.append(part.getContent().toString());
         }else if(part.isMimeType("message/rfc822")) {
        	 getContent((Part)part.getContent(), content);
         }else if(part.isMimeType("multipart/*")) {
        	 Multipart multipart = (Multipart) part.getContent();
        	 int count = multipart.getCount();
        	 for(int i=0;i<count;i++) {
        		BodyPart bodyPart = multipart.getBodyPart(i);
        		getContent(bodyPart, content);
        	 }
         }
	}
	
	private void getStore() {
		if(store != null) {
			return;
		}
		Properties properties = System.getProperties();
        properties.setProperty("mail.imap.host", host);// 设置邮件服务器
        properties.setProperty("mail.imap.auth", "true");// 打开认证
        properties.setProperty("mail.imap.port", port); //设置端口
        properties.setProperty("mail.transport.protocol", protocol); //设置端口
        properties.setProperty("mail.imap.socketFactory.class","javax.net.ssl.SSLSocketFactory");

         // 1.获取默认session对象
         Session session = Session.getDefaultInstance(properties, new Authenticator() {
            public PasswordAuthentication getPasswordAuthentication() {
               return new PasswordAuthentication(username, password); // 发件人邮箱账号、密码
            }
         });
         try {
			store = session.getStore("imap");
			store.connect();
		} catch (NoSuchProviderException e) {
			log.error("连接邮件出错", e);
		} catch (MessagingException e) {
			log.error("连接邮件出错", e);
		}
	     
	}
	
	@PostConstruct
	public void init() {
		//从数据库获取时间和ID
		lastDate = null;//查询数据库
		if(lastDate == null) {
			lastDate = new Date();
		}
		lastId = 0L;//查询数据库
		if(lastId == null) {
			lastId = 0L;
		}
	}
}
