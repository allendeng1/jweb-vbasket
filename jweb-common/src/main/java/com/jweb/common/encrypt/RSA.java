package com.jweb.common.encrypt;

import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import com.jweb.common.exception.CommonException;
import com.jweb.common.exception.MyException;

/**
 * RSA 非对称加密/解密
 * @author 邓超
 *
 * 2023/08/25 下午4:19:45
 */
public class RSA {
	
	/**
     * 生成签名
     *
     * @param data          待生成签名内容
     * @param privateKeyStr 私钥
     * @return 签名信息
     * @throws MyException 异常
     */
    public static String sign(String data, String privateKeyStr) throws MyException {
        try {
			PrivateKey priK = getPrivateKey(privateKeyStr);
			Signature sig = Signature.getInstance("SHA256withRSA");
			sig.initSign(priK);
			sig.update(data.getBytes());
			return BASE64.encrypt2string(sig.sign());
		} catch (NoSuchAlgorithmException | InvalidKeyException | SignatureException | InvalidKeySpecException e) {
			CommonException.parameterInvalid();
		}
		return null; 
    }
    
    /**
     * 验证签名
     *
     * @param data         待验证原文
     * @param sign         待验证签名
     * @param publicKeyStr 公钥
     * @return 是否验证成功
     * @throws Exception 异常
     */
    public static boolean verify(String data, String sign, String publicKeyStr) throws Exception {
        PublicKey pubK = getPublicKey(publicKeyStr);
        Signature sig = Signature.getInstance("SHA256withRSA");
        sig.initVerify(pubK);
        sig.update(data.getBytes());
        return sig.verify(BASE64.decrypt2byte(sign));
    }

	public static byte[] encrypt2byte(String str, String publicKeyStr) throws MyException{
		try {
			PublicKey publicKey = getPublicKey(publicKeyStr);
			Cipher cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.ENCRYPT_MODE, publicKey);
			return cipher.doFinal(str.getBytes());
		} catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | InvalidKeySpecException | IllegalBlockSizeException | BadPaddingException e) {
			CommonException.parameterInvalid();
		}
		return null; 
	}
	
	public static String encrypt2string(String str, String key)throws MyException{
		return BASE64.encrypt2string(encrypt2byte(str, key));
	}
	public static byte[] decrypt2byte(String str, String key)throws MyException {
        try {
        	PrivateKey privateKey = getPrivateKey(key);
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            
            byte[] inputArray = BASE64.decrypt2byte(str);
            
            int inputLength = inputArray.length;
            // 最大解密字节数，超出最大字节数需要分组解密
            int MAX_ENCRYPT_BLOCK = 256;
            // 标识
            int offSet = 0;
            byte[] resultBytes = {};
            byte[] cache = {};
            while (inputLength - offSet > 0) {
                if (inputLength - offSet > MAX_ENCRYPT_BLOCK) {
                    cache = cipher.doFinal(inputArray, offSet, MAX_ENCRYPT_BLOCK);
                    offSet += MAX_ENCRYPT_BLOCK;
                } else {
                    cache = cipher.doFinal(inputArray, offSet, inputLength - offSet);
                    offSet = inputLength;
                }
                resultBytes = Arrays.copyOf(resultBytes, resultBytes.length + cache.length);
                System.arraycopy(cache, 0, resultBytes, resultBytes.length - cache.length, cache.length);
            }
			return resultBytes;
		} catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException | InvalidKeySpecException  e) {
			CommonException.parameterInvalid();
		}
		return null;
    }
	
	public static String decrypt2string(String str, String key)throws MyException {
        return new String(decrypt2byte(str, key));
    }
	
	/**
	 * 生成密钥对
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unused")
	private static void generateKey(int keySize) throws Exception {
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
        keyPairGen.initialize(keySize);
        KeyPair keyPair = keyPairGen.generateKeyPair();
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
       
        System.out.println("=============公钥==============");
        System.out.println(BASE64.encrypt2string(publicKey.getEncoded()));
        
        System.out.println("=============私钥==============");
        System.out.println(BASE64.encrypt2string(privateKey.getEncoded()));
        
    }

	/**
     * 获取公钥
     *
     * @param key 公钥字符串
     * @return 公钥
	 * @throws NoSuchAlgorithmException 
	 * @throws InvalidKeySpecException 
     * @throws Exception 异常
     */
    private static PublicKey getPublicKey(String key) throws NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] keyBytes = BASE64.decrypt2byte(key);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey publicKey = keyFactory.generatePublic(keySpec);
        return publicKey;
    }
    /**
     * 获取私钥
     *
     * @param key 私钥字符串
     * @return 私钥
     * @throws NoSuchAlgorithmException 
     * @throws InvalidKeySpecException 
     * @throws Exception 异常
     */
    private static PrivateKey getPrivateKey(String key) throws NoSuchAlgorithmException, InvalidKeySpecException  {
        byte[] keyBytes = BASE64.decrypt2byte(key);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
        return privateKey;
    }
}
