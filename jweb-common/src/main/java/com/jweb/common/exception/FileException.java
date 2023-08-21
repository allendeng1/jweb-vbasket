package com.jweb.common.exception;
/**
 * 
 * @author 邓超
 *
 * 2022/09/29 下午1:38:48
 */
public class FileException extends MyException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public int bizCode() {
		return BizCode.FILE_EXCEPTION.getCode();
	}

	public FileException(Integer errCode, String message) {
		super(errCode, message);
	}
	private FileException(Integer errCode, String message, Throwable e) {
		super(errCode, message, e);
	}
	
	/**
	 * 获取文件流异常
	 * @throws FileException
	 */
	public static void obtainFileStreamError() throws FileException{
		throw new FileException(1000, "获取文件流异常");
	}
	/**
	 * 未找到文件存储服务
	 * @throws FileException
	 */
	public static void fileStorageNotFoundError() throws FileException{
		throw new FileException(1001, "未找到文件存储服务");
	}
	/**
	 * 上传文件异常
	 * @throws FileException
	 */
	public static void uploadFileError() throws FileException{
		throw new FileException(1002, "上传文件异常");
	}
	/**
	 * 上传文件授权失败
	 * @throws FileException
	 */
	public static void authorizationError() throws FileException{
		throw new FileException(1003, "上传文件授权失败");
	}
}
