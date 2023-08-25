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
	 * 数字型编号
	 * @param num 位数
	 * @return
	 */
	public static String numericNo(int num)  {
		return RandomStringUtils.randomNumeric(num);
	}
	/**
	 * 字母型编号
	 * @param num 位数
	 * @return
	 */
	public static String alphabeticNo(int num)  {
		return RandomStringUtils.randomAlphabetic(num);
	}
	/**
	 * 小写字母型编号
	 * @param num 位数
	 * @return
	 */
	public static String alphabeticLowerNo(int num)  {
		return alphabeticNo(num).toLowerCase();
	}
	/**
	 * 大写字母型编号
	 * @param num 位数
	 * @return
	 */
	public static String alphabeticUpperNo(int num)  {
		return alphabeticNo(num).toUpperCase();
	}
	/**
	 * 字母+数字型编号
	 * @param num 位数
	 * @return
	 */
	public static String alphaAndNumericNo(int num)  {
		return RandomStringUtils.randomAlphanumeric(num);
	}
	/**
	 * 小写字母+数字型编号
	 * @param num 位数
	 * @return
	 */
	public static String alphaLowerAndNumericNo(int num)  {
		return alphaAndNumericNo(num).toLowerCase();
	}
	/**
	 * 大写字母+数字型编号
	 * @param num 位数
	 * @return
	 */
	public static String alphaUpperAndNumericNo(int num)  {
		return alphaAndNumericNo(num).toUpperCase();
	}
	/**
	 * UUID
	 * @return
	 */
	public static String uuidNo() {
		return UUID.randomUUID().toString();
	}
}
