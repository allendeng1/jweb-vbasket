package com.jweb.common.encrypt;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import com.jweb.common.exception.CommonException;
import com.jweb.common.exception.MyException;

/**
 * DES 对称加密/解密
 * @author 邓超
 *
 * 2023/08/25 下午3:24:35
 */
public class DES {

	public static byte[] encrypt2byte(String str, String key) throws MyException{
        SecretKey secretKey = new SecretKeySpec(key.getBytes(), "DES");
        try {
			Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
	        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
	        return cipher.doFinal(str.getBytes());
		} catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
			CommonException.parameterInvalid();
		}
		return null;
	}
	
	public static String encrypt2string(String str, String key)throws MyException{
		return new String(encrypt2byte(str, key));
	}
	public static byte[] decrypt2byte(String str, String key)throws MyException {
        SecretKey secretKey = new SecretKeySpec(key.getBytes(), "DES");
        try {
			Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
			cipher.init(Cipher.DECRYPT_MODE, secretKey);
			return cipher.doFinal(str.getBytes());
		} catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException  e) {
			CommonException.parameterInvalid();
		}
		return null;
    }
	
	public static String decrypt2string(String str, String key)throws MyException {
        return new String(decrypt2byte(str, key));
    }
	
}
