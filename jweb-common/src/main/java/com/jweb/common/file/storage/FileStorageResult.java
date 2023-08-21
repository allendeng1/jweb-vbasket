package com.jweb.common.file.storage;

import lombok.Data;

/**
 * 文件存储结果
 * @author 邓超
 *
 * 2022/10/13 上午11:53:00
 */
@Data
public class FileStorageResult {

	private FileStorageSite storageSite;
	private FileBizType bizType;
	private String owner;
	private String filePath;
	private String fileName;
	private String fileType;
	private long fileSize;
	private String fileUrl;
	
}
