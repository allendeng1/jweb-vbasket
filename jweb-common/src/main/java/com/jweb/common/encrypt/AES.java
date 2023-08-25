package com.jweb.common.encrypt;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import com.jweb.common.exception.CommonException;
import com.jweb.common.exception.MyException;

/**
 * AES 对称加密/解密
 * @author 邓超
 *
 * 2023/08/25 下午3:24:35
 */
public class AES {

	public static byte[] encrypt2byte(String str, String key) throws MyException{
        try {
        	KeyGenerator kgen = KeyGenerator.getInstance("AES");
            kgen.init(128, new SecureRandom(key.getBytes()));
            SecretKey secretKey = kgen.generateKey();
            byte[] enCodeFormat = secretKey.getEncoded();
            SecretKeySpec sks = new SecretKeySpec(enCodeFormat, "AES");
            
			Cipher cipher = Cipher.getInstance("AES");
	        cipher.init(Cipher.ENCRYPT_MODE, sks);
	        return cipher.doFinal(str.getBytes());
		} catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
			CommonException.parameterInvalid();
		}
		return null;
	}
	
	public static String encrypt2string(String str, String key)throws MyException{
		return BASE64.encrypt2string(encrypt2byte(str, key));
	}
	public static byte[] decrypt2byte(String str, String key)throws MyException {
        try {
        	KeyGenerator kgen = KeyGenerator.getInstance("AES");
            kgen.init(128, new SecureRandom(key.getBytes()));
            SecretKey secretKey = kgen.generateKey();
            byte[] enCodeFormat = secretKey.getEncoded();
            SecretKeySpec sks = new SecretKeySpec(enCodeFormat, "AES");
            
			Cipher cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.DECRYPT_MODE, sks);
			return cipher.doFinal(BASE64.decrypt2byte(str));
		} catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException  e) {
			e.printStackTrace();
			CommonException.parameterInvalid();
		}
		return null;
    }
	
	public static String decrypt2string(String str, String key)throws MyException {
        return new String(decrypt2byte(str, key));
    }
}
