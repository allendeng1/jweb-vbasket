package com.jweb.common.file.storage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.jweb.common.exception.FileException;
import com.jweb.common.exception.MyException;
import com.jweb.common.util.HttpUtil;
import com.jweb.common.util.HttpUtil.HttpResult;

import lombok.extern.slf4j.Slf4j;

/**
 * 自建文件存储服务
 * @author 邓超
 *
 * 2022/10/12 下午3:59:36
 */
@Slf4j
@Configuration
@ConditionalOnExpression("'${file.storage.site:}'.equals('MYSITE')")
@PropertySource(value = {"classpath:common-${spring.profiles.active}.properties"})
@ConfigurationProperties(prefix = "file.mysite")
public class MyFileStorage extends FileStorageProvider{
	
	private String domain;
	private String uploadUrl;
	private String accessKey;
	@Value("${file.rootPath:}")
	private String rootPath;

	@Override
	public FileStorageSite storageSite() {
		return FileStorageSite.MYSITE;
	}

	@Override
	protected String writeToDisk(MultipartFile file, long saasId, FileBizType type, String owner, String filePath, String fileName, String domain) throws MyException {
		HttpResult httpResult = HttpUtil.builder().url(spliceUrl(this.domain, uploadUrl))
				.addParam("accessKey", accessKey)
				.addParam("saasId", saasId+"")
				.addParam("owner", owner)
				.addParam("type", type.name())
				.multipartPost(file).sync();
		
		if(isNull(httpResult)) {
			FileException.uploadFileError();
		}
		String response = httpResult.getResponse();
		log.info("MyFileStorage upload file result：{}", response);
		
		if(!httpResult.isOk()) {
			FileException.uploadFileError();
		}
		 
		JSONObject json = toJsonObject(response);
		if(json.getIntValue("bizcode") == 100) {
			return json.getString("data");
		}else {
			throw new MyException(json.getIntValue("errcode"), json.getString("msg")) {
				private static final long serialVersionUID = 1L;
				@Override
				public int bizCode() {
					return json.getIntValue("bizcode");
				}
			};
		}
	}

	@Override
	protected String buildFileAccessUrl(String filePath, String fileName, String domain) throws MyException {
		return this.domain+rootPath+SEPARATOR+filePath+SEPARATOR+fileName;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getUploadUrl() {
		return uploadUrl;
	}

	public void setUploadUrl(String uploadUrl) {
		this.uploadUrl = uploadUrl;
	}

	public String getAccessKey() {
		return accessKey;
	}

	public void setAccessKey(String accessKey) {
		this.accessKey = accessKey;
	}
	
}
