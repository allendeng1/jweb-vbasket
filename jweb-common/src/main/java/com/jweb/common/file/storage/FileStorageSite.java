package com.jweb.common.file.storage;

/**
 * 文件存储站点定义
 * @author 邓超
 *
 * 2022/10/12 下午3:52:25
 */
public enum FileStorageSite {
	/**
	 * 本地存储
	 */
	LOCAL,
	/**
	 * 自建的文件存储站点
	 */
	MYSITE,
	/**
	 * 阿里云OSS存储
	 */
	ALIYUNOSS,
	/**
	 * ftp文件存储站点
	 */
	FTP
	
}
