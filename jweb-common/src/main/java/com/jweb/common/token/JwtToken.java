package com.jweb.common.token;

import java.security.SignatureException;
import java.util.Base64;
import java.util.Random;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import com.jweb.common.exception.AuthException;
import com.jweb.common.util.DataUtil;
import com.jweb.common.util.DateTimeUtil;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
/**
 * 
 * @author 邓超
 *
 * 2022/6/4 12:24
 */
@Slf4j
public class JwtToken extends DataUtil{
	
	private static final String SIGN_KEY = "b76uhyksd9623hdishd954klajahm5";
	private static final String ISSUER = "sass_loan";
	
	private static String[] letters = {"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z",
			"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z"};
	
	/**
	 * 生成TOKEN
	 * @param dataMap 数据
	 * @param second 有效时长,大于0才有效，单位：秒
	 * @return
	 */
	public static String generate(TokenData data, long second){
		
		SecretKey secretKey = generalKey();
		JwtBuilder builder = Jwts.builder()
			.claim("userId", data.getUserId())
			.claim("saasId", data.getSaasId())
			.setIssuer(ISSUER)
			.signWith(SignatureAlgorithm.HS256, secretKey);
		if(second > 0){
			long expiredDate = DateTimeUtil.nowTime()+second*1000;
			builder.setExpiration(DateTimeUtil.timeToDate(expiredDate));
		}
		
		String jwtToken = builder.compact();
	    String saltToken = addSalt(jwtToken);
	    return Base64.getEncoder().encodeToString(saltToken.getBytes());
		
	}
	
	/**
	 * 校验token
	 * @param token
	 */
	public static TokenData checkToken(String token)throws AuthException{
	       
        try {
        	if(isNullOrTrimEmpty(token)){
        		AuthException.invalidToken();
        	}
        	String saltToken = new String(Base64.getDecoder().decode(token));
        	String jwtToken = deleteSalt(saltToken);
        	Claims claims = parseJWT(jwtToken);
        	if(!claims.getIssuer().equals(ISSUER)) {
        		AuthException.invalidToken();
        	}
        	
        	Integer userId = (Integer) claims.get("userId");
        	Integer saasId = (Integer) claims.get("saasId");
        	
        	if(isNull(userId) || isNull(saasId)){
        		AuthException.invalidToken();
        	}
        	
        	TokenData data = new TokenData();
        	data.setUserId(Long.valueOf(userId));
        	data.setSaasId(Long.valueOf(saasId));
        	return data;
        } catch (ExpiredJwtException e) {
        	log.error("校验token错误", e);
        	AuthException.tokenExpired();
        } catch (SignatureException e) {
        	log.error("校验token错误", e);
        	AuthException.invalidToken();
        } catch (Exception e) {
        	log.error("校验token错误", e);
        	AuthException.invalidToken();
        }
		return null;
    }
	
	private static SecretKey generalKey() {
        byte[] encodedKey = Base64.getDecoder().decode(SIGN_KEY);
        SecretKey key = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
        return key;
    }
    
    private static String addSalt(String token) {
    	Random r = new Random();
		String salt1 = "";
		for(int i = 0;i<4;i++){
			salt1 += letters[r.nextInt(52)];
		}
		
		String salt2 = "";
		for(int j=0;j<5;j++){
			salt2 += letters[r.nextInt(52)];
		}
		return salt1+token+salt2;
    }
    
    private static String deleteSalt(String token) {
    	return token.substring(4, token.length()-5);
    }

    /**
     * 
     * 解析JWT字符串
     * 
     * @param jwt
     * @return
     * @throws Exception
     */
    private static Claims parseJWT(String jwt) throws Exception {
        SecretKey secretKey = generalKey();
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwt).getBody();
    }
}
