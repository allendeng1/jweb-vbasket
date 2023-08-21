package com.jweb.common.encrypt;

import java.io.UnsupportedEncodingException;

import org.springframework.util.DigestUtils;

import com.jweb.common.exception.CommonException;
import com.jweb.common.exception.MyException;
import com.jweb.common.util.DataUtil;

/**
 * MD5加密
 *
 * @author 邓超
 *
 */
public class MD5 extends DataUtil{

	public static String encrypt(String str)throws MyException{
		if(isNullOrTrimEmpty(str)){
			CommonException.parameterLost();
		}
		try {
			return DigestUtils.md5DigestAsHex(str.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			CommonException.parameterInvalid();
		}
		return null;
	}

}
