package com.jweb.common.exception;
/**
 * 
 * @author 邓超
 *
 * 2022/6/4 12:24
 */
public class CommonException extends MyException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public int bizCode() {
		return BizCode.COMMON_EXCEPTION.getCode();
	}
	
	public CommonException(Integer errCode, String message) {
		super(errCode, message);
	}
	private CommonException(Integer errCode, String message, Throwable e) {
		super(errCode, message, e);
	}
	/**
	 * 服务器错误
	 * @throws CommonException
	 */
	public static void serverError()throws CommonException{
		throw new CommonException(100, "服务器错误");
	}
	/**
	 * 缺少参数
	 * @throws CommonException
	 */
	public static void parameterLost()throws CommonException{
		throw new CommonException(101, "缺少参数");
	}
	/**
	 * 数据不符合要求
	 * @throws CommonException
	 */
	public static void parameterInvalid()throws CommonException{
		throw new CommonException(102, "数据不符合要求");
	}
	/**
	 * 图片格式不支持
	 * @throws CommonException
	 */
	public static void imgFormatNotSupport()throws CommonException{
		throw new CommonException(103, "图片格式不支持");
	}
	/**
	 * 无效的文件路径
	 * @throws CommonException
	 */
	public static void invalidFilePath()throws CommonException{
		throw new CommonException(104, "无效的文件路径");
	}
	/**
	 * 无效的文件名
	 * @throws CommonException
	 */
	public static void invalidFileName()throws CommonException{
		throw new CommonException(105, "无效的文件名");
	}
	/**
	 * 无效的文件格式
	 * @throws CommonException
	 */
	public static void invalidFileFormat()throws CommonException{
		throw new CommonException(106, "无效的文件格式");
	}
	/**
	 * 读取文件出错
	 * @throws CommonException
	 */
	public static void readFileError()throws CommonException{
		throw new CommonException(107, "读取文件出错");
	}
	/**
	 * 验证码已过期
	 * @throws CommonException
	 */
	public static void captchaExpired()throws CommonException{
		throw new CommonException(108, "验证码已过期");
	}
	/**
	 * 验证码不正确
	 * @throws CommonException
	 */
	public static void captchaNotMatch()throws CommonException{
		throw new CommonException(109, "验证码不正确");
	}
}
