package com.jweb.common.exception;
/**
 * 
 * @author 邓超
 *
 * 2022/6/4 12:24
 */
public class MenuOperaException extends MyException{

	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public int bizCode() {
		return BizCode.MENUOPERA_EXCEPTION.getCode();
	}
	public MenuOperaException(Integer errCode, String message) {
		super(errCode, message);
	}
	private MenuOperaException(Integer errCode, String message, Throwable e) {
		super(errCode, message, e);
	}
	
	/**
	 * 菜单已存在
	 * @throws MenuOperaException
	 */
	public static void menuExist()throws MenuOperaException{
		throw new MenuOperaException(100, "菜单已存在");
	}
	/**
	 * 菜单不存在
	 * @throws MenuOperaException
	 */
	public static void menuNotExist()throws MenuOperaException{
		throw new MenuOperaException(101, "菜单不存在");
	}
	/**
	 * 菜单已删除
	 * @throws MenuOperaException
	 */
	public static void menuIsDelete()throws MenuOperaException{
		throw new MenuOperaException(102, "菜单已删除");
	}
	
	/**
	 * 操作已存在
	 * @throws MenuOperaException
	 */
	public static void operaExist()throws MenuOperaException{
		throw new MenuOperaException(103, "操作已存在");
	}
	/**
	 * 操作不存在
	 * @throws MenuOperaException
	 */
	public static void operaNotExist()throws MenuOperaException{
		throw new MenuOperaException(104, "操作不存在");
	}
	/**
	 * 操作已删除
	 * @throws MenuOperaException
	 */
	public static void operaIsDelete()throws MenuOperaException{
		throw new MenuOperaException(105, "操作已删除");
	}
	
	/**
	 * 只能添加在菜单下面
	 * @throws MenuOperaException
	 */
	public static void typeNotSupport()throws MenuOperaException{
		throw new MenuOperaException(106, "只能添加在菜单下面");
	}
}
