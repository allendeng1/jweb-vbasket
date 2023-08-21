package com.jweb.common.file.storage;

/**
 * 文件业务类型定义
 * @author 邓超
 *
 * 2022/10/12 下午5:46:17
 */
public enum FileBizType {
	
	/**
	 * 头像
	 */
	AVATAR(1, true),
	/**
	 * 身份证正面
	 */
	ID_FRONT(2, true),
	/**
	 * 身份证背面
	 */
	ID_BACK(3, true);

	private int type;
	private boolean isOnly;
	FileBizType(int type, boolean isOnly){
		this.type = type;
		this.isOnly = isOnly;
	}
	public int getType() {
		return type;
	}
	public boolean isOnly() {
		return isOnly;
	}
	
}
