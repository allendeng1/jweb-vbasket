package com.jweb.common.exception;
/**
 * 
 * @author 邓超
 *
 * 2022/6/4 12:24
 */
public class RoleException extends MyException{

	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public int bizCode() {
		return BizCode.ROLE_EXCEPTION.getCode();
	}
	public RoleException(Integer errCode, String message) {
		super(errCode, message);
	}
	private RoleException(Integer errCode, String message, Throwable e) {
		super(errCode, message, e);
	}
	
	/**
	 * 角色已存在
	 * @throws RoleException
	 */
	public static void roleExist()throws RoleException{
		throw new RoleException(100, "角色已存在");
	}
	/**
	 * 角色不存在
	 * @throws RoleException
	 */
	public static void roleNotExist()throws RoleException{
		throw new RoleException(101, "角色不存在");
	}
	/**
	 * 角色已删除
	 * @throws RoleException
	 */
	public static void roleIsDelete()throws RoleException{
		throw new RoleException(102, "角色已删除");
	}
}
