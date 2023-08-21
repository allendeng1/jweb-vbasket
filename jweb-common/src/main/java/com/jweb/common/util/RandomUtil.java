package com.jweb.common.util;

import java.util.UUID;

import org.apache.commons.lang.RandomStringUtils;

import com.jweb.common.exception.MyException;

/**
 * 随机流水号生成器
 * @author 邓超
 *
 * 2022/10/13 上午10:39:17
 */
public class RandomUtil {
	/**
	 * 当前毫秒数 + 随机3位数
	 * @return
	 */
	public static String millNo() {
		return DateTimeUtil.nowTime()+RandomStringUtils.randomNumeric(3);
	}

	/**
	 * 时间(yyyyMMddHHmmss) + 随机3位数
	 * @return
	 * @throws MyException
	 */
	public static String datetimeNo() throws MyException {
		return DateTimeUtil.timeToString(DateTimeUtil.nowTime(), DateTimeUtil.Format.YYYYMMDDHHMMSS_4)+RandomStringUtils.randomNumeric(3);
	}
	/**
	 * UUID
	 * @return
	 */
	public static String uuidNo() {
		return UUID.randomUUID().toString();
	}
}
