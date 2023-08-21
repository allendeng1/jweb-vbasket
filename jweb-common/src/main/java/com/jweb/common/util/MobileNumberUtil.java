package com.jweb.common.util;

import org.springframework.stereotype.Component;

import com.jweb.common.exception.MyException;
import com.jweb.common.util.LocaleUtil.Country;

/**
 * 手机号码工具类
 * @author 邓超
 *
 * 2022/08/30 下午5:19:57
 */
@Component
public class MobileNumberUtil {
	
	/**
	 * 增加国码
	 * @param mobileNumber 电话号码
	 * @return
	 * @throws MyException 
	 */
	public static String addCountryCode(String mobileNumber) throws MyException {
		Country country = LocaleUtil.getCountry();
		mobileNumber = cleanup(mobileNumber);
		if(mobileNumber.startsWith(country.getMobileCode())) {
			if(mobileNumber.length() > country.getMobileLength()) {//传入的号码已包含国码
				return mobileNumber;
			}
		}
		return country.getMobileCode() + mobileNumber;
	}
	/**
	 * 去掉国码
	 * @param mobileNumber 电话号码
	 * @return
	 * @throws MyException 
	 */
	public static String cleanCountryCode(String mobileNumber) throws MyException {
		Country country = LocaleUtil.getCountry();
		mobileNumber = cleanup(mobileNumber);
		if(mobileNumber.length() <= country.getMobileLength()) {//传入的号码不包含国码
			return mobileNumber;
		}
		if(!mobileNumber.startsWith(country.getMobileCode())) {//传入的号码不包含国码
			return mobileNumber;
		}
		return mobileNumber.substring(country.getMobileCode().length(), mobileNumber.length());
	}
	/**
	 * 清理其他符号
	 * @param mobileNumber 电话号码
	 * @return
	 * @throws MyException 
	 */
	public static String cleanup(String mobileNumber) throws MyException {
		return RegularMatchUtil.extract(mobileNumber, RegularMatchUtil.Patter.NUMBER);
	}
}
