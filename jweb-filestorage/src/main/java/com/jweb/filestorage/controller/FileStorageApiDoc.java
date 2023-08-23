package com.jweb.filestorage.controller;


import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.jweb.common.api.ApiResult;
import com.jweb.common.file.storage.FileBizType;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RequestMapping(value="filestorage")
@Api(tags="文件存储接口")
public interface FileStorageApiDoc {
	

	@ApiOperation(value = "上传文件",notes = "上传文件")
	@ApiImplicitParams({
    	@ApiImplicitParam(name="file", dataType="__file", paramType="form", required=true, value="文件"),
    	@ApiImplicitParam(name="type", dataType="string", paramType="form", required=true, value="文件类型"),
    	@ApiImplicitParam(name="owner", dataType="string", paramType="form", required=false, value="文件拥有者，默认当前登录用户")
    })
	@PostMapping("/upload")
	public ApiResult upload(@RequestParam("file") MultipartFile file,
							@RequestParam(value="type", required = true) FileBizType type,
							@RequestParam(value="owner", required = false) String owner,
							HttpServletRequest request);
	
	@ApiOperation(value = "上传文件openapi",notes = "上传文件openapi")
	@ApiImplicitParams({
		@ApiImplicitParam(name="accessKey", dataType="string", paramType="form", required=true, value="访问密钥"),
    	@ApiImplicitParam(name="file", dataType="__file", paramType="form", required=true, value="文件"),
    	@ApiImplicitParam(name="type", dataType="string", paramType="form", required=true, value="文件类型"),
    	@ApiImplicitParam(name="owner", dataType="string", paramType="form", required=true, value="文件拥有者，默认当前登录用户")
    })
	@PostMapping("/upload/openapi")
	public ApiResult uploadOpenapi(@RequestParam(value="accessKey", required = true) String accessKey,
							@RequestParam("file") MultipartFile file,
							@RequestParam(value="type", required = true) FileBizType type,
							@RequestParam(value="owner", required = true) String owner,
							HttpServletRequest request);

}
			                    