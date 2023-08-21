package com.jweb.common.file.storage;

import java.io.IOException;
import java.net.SocketException;
import java.util.regex.Matcher;

import javax.annotation.PostConstruct;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.multipart.MultipartFile;

import com.jweb.common.exception.FileException;
import com.jweb.common.exception.MyException;

import lombok.extern.slf4j.Slf4j;

/**
 * Ftp文件存储服务
 * @author 邓超
 *
 * 2022/10/12 下午3:59:36
 */
@Slf4j
@Configuration
@ConditionalOnExpression("'${file.storage.site:}'.equals('FTP')")
@PropertySource(value = {"classpath:common-${spring.profiles.active}.properties"})
@ConfigurationProperties(prefix = "file.ftp")
public class FtpFileStorage extends FileStorageProvider{
	
	private String host;
	private int port;
	private String username;
	private String password;
	private String domain;
	
	@Value("${file.rootPath:}")
	private String rootPath;
	
	private FTPClient client;

	@Override
	public FileStorageSite storageSite() {
		return FileStorageSite.FTP;
	}
	
	@Override
	protected String writeToDisk(MultipartFile file, long saasId, FileBizType type, String owner, String filePath,
			String fileName, String domain) throws MyException {
		
		createDir(filePath);
		try {
			client.storeFile(fileName, file.getInputStream());
			return buildFileAccessUrl(filePath, fileName, domain);
		} catch (IOException e) {
			log.error("上传文件异常", e);
			FileException.uploadFileError();
		}
		return null;
	}

	@Override
	protected String buildFileAccessUrl(String filePath, String fileName, String domain) throws MyException {
		return this.domain+rootPath+SEPARATOR+filePath+SEPARATOR+fileName;
	}
	
	// 创建文件夹，并切换到该文件夹
	private void createDir(String path) throws MyException {
	    try {
	    	client.changeWorkingDirectory(SEPARATOR);
	    	String[] dirs = path.split(Matcher.quoteReplacement(SEPARATOR));
			for (String dir : dirs) {
			      if (isEmpty(dir)) {
			        continue;
			      }
			      boolean changed = client.changeWorkingDirectory(dir);
			      if (!changed) {
			        client.makeDirectory(dir);
			      }
			      client.changeWorkingDirectory(dir);
			}
		} catch (IOException e) {
			log.error("FtpFileStorage create directory error：{}", path, e);
			FileException.uploadFileError();
		}
	}
	
	 @PostConstruct
	 private void init() {
		 client = new FTPClient();
		 try {
			client.connect(host, port);
			boolean isOk = client.login(username, password);
			if(!isOk) {
				throw new RuntimeException("FTP登录失败");
			}
			// 中文支持
			client.setControlEncoding("UTF-8");
            // 设置文件类型为二进制（如果从FTP下载或上传的文件是压缩文件的时候，不进行该设置可能会导致获取的压缩文件解压失败）
			client.setFileType(FTPClient.BINARY_FILE_TYPE);
			//开启被动模式，否则文件上传不成功，也不报错
			client.enterLocalPassiveMode();
            if (!FTPReply.isPositiveCompletion(client.getReplyCode())) {
            	client.disconnect();
            	throw new RuntimeException("FTP连接失败");
            }
		} catch (SocketException e) {
			log.error("FTP Client连接失败 {}:{}", host, port, e);
			throw new RuntimeException(e);
		} catch (IOException e) {
			log.error("FTP Client连接失败 {}:{}", host, port, e);
			throw new RuntimeException(e);
		}
	 }

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}
	
}
