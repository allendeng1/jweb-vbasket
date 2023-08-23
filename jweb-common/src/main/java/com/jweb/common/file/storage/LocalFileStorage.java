package com.jweb.common.file.storage;

import java.io.File;
import java.io.IOException;

import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.multipart.MultipartFile;

import com.jweb.common.exception.FileException;
import com.jweb.common.exception.MyException;

import lombok.extern.slf4j.Slf4j;

/**
 * 本地文件存储服务
 * @author 邓超
 *
 * 2022/10/12 下午3:59:36
 */
@Slf4j
@Configuration
@ConditionalOnExpression("'${file.storage.site:}'.equals('LOCAL')")
@PropertySource(value = {"classpath:common-${spring.profiles.active}.properties"})
@ConfigurationProperties(prefix = "file")
public class LocalFileStorage extends FileStorageProvider {
	
	private String rootPath;

	@Override
	public FileStorageSite storageSite() {
		return FileStorageSite.LOCAL;
	}

	@Override
	protected String writeToDisk(MultipartFile file, FileBizType type, String owner, String filePath, String fileName, String domain) throws MyException {
		
		try {
			File dest = new File(rootPath+SEPARATOR+filePath);
			if(!dest.exists()) {
				dest.mkdirs();
			}
			dest = new File(rootPath+SEPARATOR+filePath+SEPARATOR+fileName);
			file.transferTo(dest);
			return buildFileAccessUrl(filePath, fileName, domain);
		} catch (IllegalStateException e) {
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
		return domain+rootPath+filePath+SEPARATOR+fileName;
	}

	public String getRootPath() {
		return rootPath;
	}

	public void setRootPath(String rootPath) {
		if(!rootPath.endsWith(SEPARATOR)) {
			rootPath += SEPARATOR;
		}
		this.rootPath = rootPath;
	}
}
