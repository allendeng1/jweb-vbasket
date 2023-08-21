package com.jweb.common.file.storage;

import java.io.File;

import org.springframework.web.multipart.MultipartFile;

import com.jweb.common.exception.MyException;
import com.jweb.common.util.DataUtil;
import com.jweb.common.util.RandomUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * 文件存储
 * @author 邓超
 *
 * 2022/10/12 下午3:47:11
 */
@Slf4j
public abstract class FileStorageProvider extends DataUtil{
	
	protected static final String SEPARATOR = File.separator;
	
	/**
	 * 存储文件
	 * @param saasId saasId
	 * @param type 文件业务类型，详见：{@link com.dengchao.common.file.storage.FileBizType.java}
	 * @param owner 文件拥有者
	 * @param file 文件
	 * @param domain 访问域名
	 * @return
	 * @throws MyException
	 */
	public FileStorageResult save(long saasId, FileBizType type, String owner, MultipartFile file, String domain) throws MyException {
		String originalFilename = file.getOriginalFilename();
		long size = file.getSize();
		log.info("{}文件存储：type-{}，owner-{}，filename-{}，size-{}", storageSite(), type.name(),owner,originalFilename,size);

		String fileType = originalFilename.substring(originalFilename.lastIndexOf(".")+1, originalFilename.length()).toLowerCase();
		String filePath = saasId+SEPARATOR+type.name();
		String fileName = owner+"."+fileType;
		if(!type.isOnly()) {
			filePath += SEPARATOR+owner;
			fileName = RandomUtil.datetimeNo()+"."+fileType;
		}
		
		String fileUrl = writeToDisk(file,saasId,type,owner, filePath, fileName, domain);
		
		FileStorageResult result = new FileStorageResult();
		result.setStorageSite(storageSite());
		result.setBizType(type);
		result.setOwner(owner);
		result.setFileName(fileName);
		result.setFilePath(filePath);
		result.setFileType(fileType);
		result.setFileSize(size);
		result.setFileUrl(fileUrl);
		return result;
	}
	/**
	 * 获取文件访问url
	 * @param filePath
	 * @param fileName
	 * @return
	 * @throws MyException
	 */
	public String getFileUrl(String filePath, String fileName, String domain) throws MyException{
		return buildFileAccessUrl(filePath, fileName, domain);
	}
	
	public abstract FileStorageSite storageSite();
	
	protected abstract String writeToDisk(MultipartFile file, long saasId, FileBizType type, String owner, String filePath, String fileName, String domain)throws MyException;
	
	protected abstract String buildFileAccessUrl(String filePath, String fileName, String domain)throws MyException;
	
}
