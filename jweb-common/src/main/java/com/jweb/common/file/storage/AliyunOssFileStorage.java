package com.jweb.common.file.storage;

import java.io.IOException;
import java.util.Date;

import javax.annotation.PostConstruct;

import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.jweb.common.exception.FileException;
import com.jweb.common.exception.MyException;
import com.jweb.common.util.DateTimeUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * Aliyun Oss文件存储服务
 * @author 邓超
 *
 * 2022/10/13 下午1:35:06
 */
@Slf4j
@Configuration
@ConditionalOnExpression("'${file.storage.site:}'.equals('ALIYUNOSS')")
@PropertySource(value = {"classpath:common-${spring.profiles.active}.properties"})
@ConfigurationProperties(prefix = "file.aliyunoss")
public class AliyunOssFileStorage extends FileStorageProvider{
	
	 private OSS oss;
	 private String bucketName;
	 private String cdnUrl;
	 private String endpoint;
	 private String key;
	 private String secret;
	 
	 @PostConstruct
	 private void init() {
		 oss = new OSSClientBuilder().build(endpoint, key, secret);
	 }
	
	 @Override
	 public FileStorageSite storageSite() {
		return FileStorageSite.ALIYUNOSS;
	 }

	 @Override
	 protected String writeToDisk(MultipartFile file, long saasId, FileBizType type, String owner, String filePath, String fileName, String domain) throws MyException {
		 try {
			oss.putObject(bucketName, filePath+SEPARATOR+fileName, file.getInputStream());
			return buildFileAccessUrl(filePath, fileName, domain);
		} catch (OSSException e) {
			log.error("上传文件异常", e);
			FileException.uploadFileError();
		} catch (ClientException e) {
			log.error("上传文件异常", e);
			FileException.uploadFileError();
		} catch (IOException e) {
			log.error("上传文件异常", e);
			FileException.uploadFileError();
		}
		 return null;
	 }
	 
	@Override
	protected String buildFileAccessUrl(String filePath, String fileName, String domain) throws MyException {
		Date expiration = DateTimeUtil.timeToDate(DateTimeUtil.afterHours(1));
        String url = oss.generatePresignedUrl(bucketName, filePath+SEPARATOR+fileName, expiration).toString();
        if (!StringUtils.isEmpty(cdnUrl)) {
            if (url.startsWith("https")) {
                url = url.replaceFirst("https", "http");
            }
            url = url.replaceFirst(bucketName + "." + endpoint, cdnUrl);
        }
        return url;
	}

	public String getBucketName() {
		return bucketName;
	}

	public void setBucketName(String bucketName) {
		this.bucketName = bucketName;
	}

	public String getCdnUrl() {
		return cdnUrl;
	}

	public void setCdnUrl(String cdnUrl) {
		this.cdnUrl = cdnUrl;
	}

	public String getEndpoint() {
		return endpoint;
	}

	public void setEndpoint(String endpoint) {
		this.endpoint = endpoint;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

	
}
