package com.jweb.common.encrypt;

import java.util.Base64;

/**
 * base64 加密/解密
 * @author 邓超
 *
 * 2023/08/25 下午3:19:37
 */
public class BASE64 {
	
	public static byte[] encrypt2byte(String str){
		return Base64.getEncoder().encode(str.getBytes());
	}
	public static String encrypt2string(byte[] byt){
		return Base64.getEncoder().encodeToString(byt);
	}
	public static String encrypt2string(String str){
		return Base64.getEncoder().encodeToString(str.getBytes());
	}
	public static byte[] decrypt2byte(String str) {
        return Base64.getDecoder().decode(str);
    }
	
	public static String decrypt2string(String str) {
        return new String(decrypt2byte(str));
    }
}
