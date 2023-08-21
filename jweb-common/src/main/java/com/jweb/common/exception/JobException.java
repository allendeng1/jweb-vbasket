package com.jweb.common.exception;
/**
 * 
 * @author 邓超
 *
 * 2022/6/4 12:24
 */
public class JobException extends MyException{

	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public int bizCode() {
		return BizCode.JOB_EXCEPTION.getCode();
	}
	public JobException(Integer errCode, String message) {
		super(errCode, message);
	}
	private JobException(Integer errCode, String message, Throwable e) {
		super(errCode, message, e);
	}
	
	/**
	 * 已存在同名称任务
	 * @throws JobException
	 */
	public static void repeatTaskName()throws JobException{
		throw new JobException(300, "已存在同名称任务");
	}
	/**
	 * 任务执行器已绑定其他任务
	 * @throws JobException
	 */
	public static void repeatHandler()throws JobException{
		throw new JobException(301, "任务执行器已绑定其他任务");
	}
	/**
	 * 任务执行器不存在或未注册
	 * @throws JobException
	 */
	public static void handlerNotExist()throws JobException{
		throw new JobException(302, "任务执行器不存在或未注册");
	}
	/**
	 * 任务不存在
	 * @throws JobException
	 */
	public static void jobNotExist()throws JobException{
		throw new JobException(303, "任务不存在");
	}
	/**
	 * 任务执行器不支持
	 * @throws JobException
	 */
	public static void handlerNotSupport()throws JobException{
		throw new JobException(304, "任务执行器不支持");
	}
	
	/**
	 * 不能重复启动任务
	 * @throws JobException
	 */
	public static void repeatTaskRun()throws JobException{
		throw new JobException(305, "不能重复启动任务");
	}
	/**
	 * 发布指令不能为空
	 * @throws JobException
	 */
	public static void commandIsNull()throws JobException{
		throw new JobException(306, "发布指令不能为空");
	}
	/**
	 * 发布指令数据错误
	 * @throws JobException
	 */
	public static void commandDataError()throws JobException{
		throw new JobException(307, "发布指令数据错误");
	}
	/**
	 * 无效定时时间
	 * @throws JobException
	 */
	public static void invalidTimingTime() throws JobException{
		throw new JobException(308, "无效定时时间");
	}
	/**
	 * 任务运行状态异常
	 * @throws JobException
	 */
	public static void jobRunStatusError() throws JobException{
		throw new JobException(309, "任务运行状态异常");
	}
}
