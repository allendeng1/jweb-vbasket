package com.jweb.filestorage.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.jweb.common.api.ApiResult;
import com.jweb.common.api.BaseController;
import com.jweb.common.exception.FileException;
import com.jweb.common.exception.MyException;
import com.jweb.common.file.storage.FileBizType;
import com.jweb.common.file.storage.FileStorageResult;
import com.jweb.service.file.FileService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class FileStorageConroller extends BaseController implements FileStorageApiDoc {
	
	@Autowired
	private FileService fileService;
	
	@Value("${file.storage.accessKey:}")
	private String accessKey;

	@Override
	public ApiResult upload(MultipartFile file, FileBizType type, String owner, HttpServletRequest request) {
		
		ApiResult result = new ApiResult(); 
		try {
			owner = isNullOrTrimEmpty(owner) ? getUserId()+"" : owner;
			FileStorageResult fsResult = fileService.uploadFile(owner, type,  file, request);
			result.setData(fsResult.getFileUrl());
		} catch (MyException e) {
			log.error(e.getMessage(), e);
			result.bizFail(e);
		}
		return result;
	}

	@Override
	public ApiResult uploadOpenapi(String accessKey, MultipartFile file, FileBizType type, String owner,
			HttpServletRequest request) {
		ApiResult result = new ApiResult(); 
		try {
			if(!isEqual(accessKey, this.accessKey)) {
				FileException.authorizationError();
			}
			FileStorageResult fsResult = fileService.uploadFile(owner, type, file, request);
			result.setData(fsResult.getFileUrl());
		} catch (MyException e) {
			log.error(e.getMessage(), e);
			result.bizFail(e);
		}
		return result;
	}

}
