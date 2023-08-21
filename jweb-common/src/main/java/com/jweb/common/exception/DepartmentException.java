package com.jweb.common.exception;
/**
 * 
 * @author 邓超
 *
 * 2022/6/4 12:24
 */
public class DepartmentException extends MyException{

	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public int bizCode() {
		return BizCode.DEPARTMENT_EXCEPTION.getCode();
	}
	public DepartmentException(Integer errCode, String message) {
		super(errCode, message);
	}
	private DepartmentException(Integer errCode, String message, Throwable e) {
		super(errCode, message, e);
	}
	
	/**
	 * 部门已存在
	 * @throws DepartmentException
	 */
	public static void departmentExist()throws DepartmentException{
		throw new DepartmentException(100, "部门已存在");
	}
	/**
	 * 部门不存在
	 * @throws DepartmentException
	 */
	public static void departmentNotExist()throws DepartmentException{
		throw new DepartmentException(101, "部门不存在");
	}
	/**
	 * 部门已删除
	 * @throws DepartmentException
	 */
	public static void departmentIsDelete()throws DepartmentException{
		throw new DepartmentException(102, "部门已删除");
	}
}
