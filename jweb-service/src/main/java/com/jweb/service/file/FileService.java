package com.jweb.service.file;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

import com.jweb.common.exception.MyException;
import com.jweb.common.file.storage.FileBizType;
import com.jweb.common.file.storage.FileStorageResult;

/**
 * 文件服务
 * @author 邓超
 *
 * 2022/10/13 下午3:30:45
 */
public interface FileService {
	
	/**
	 * 上传文件
	 * @param owner 文件拥有者
	 * @param type 文件业务类型，详见：{@link com.jweb.common.file.storage.FileBizType.java}
	 * @param file 文件
	 * @throws MyException
	 */
	public FileStorageResult uploadFile(String owner, FileBizType type,  MultipartFile file, HttpServletRequest request) throws MyException;
	/**
	 * 获取文件访问url
	 * @param owner 文件拥有者
	 * @param type 文件业务类型，详见：{@link com.jweb.common.file.storage.FileBizType.java}
	 * @return
	 * @throws MyException
	 */
	public List<String> getFileUrls(String owner, FileBizType type, HttpServletRequest request)throws MyException;
}
