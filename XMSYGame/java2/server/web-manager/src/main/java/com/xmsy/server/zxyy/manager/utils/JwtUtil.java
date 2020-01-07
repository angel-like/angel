package com.xmsy.server.zxyy.manager.utils;

import java.util.Date;
import java.util.Map;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;

/**
 * Jwt token生成工具
 * 
 * @author Administrator
 *
 */
@Slf4j
public class JwtUtil {

	/**
	 * JWT 加解密类型
	 */
	private static final SignatureAlgorithm JWT_ALG = SignatureAlgorithm.HS256;
	/**
	 * JWT 生成密钥使用的密码
	 */
	private static final String JWT_RULE = "xmsy-zxyy";

	public static SecretKey generateKey(SignatureAlgorithm alg, String rule) {
		// 将密钥生成键转换为字节数组
		byte[] bytes = Base64.decodeBase64(rule);
		// 根据指定的加密方式，生成密钥
		return new SecretKeySpec(bytes, alg.getJcaName());
	}

	/**
	 * 解密
	 * 
	 * @param jsonWebToken
	 * @param base64Security
	 * @return
	 */
	public static Claims parseJWT(String jsonWebToken) {
		try {
			Claims claims = Jwts.parser().setSigningKey(generateKey(JWT_ALG, JWT_RULE)).parseClaimsJws(jsonWebToken)
					.getBody();
			return claims;
		} catch (Exception ex) {
			log.error("parseJWT jsonWebToken {}", jsonWebToken, ex);
			return null;
		}
	}

	/**
	 * 校验
	 * 
	 * @param jsonWebToken
	 * @param base64Security
	 * @return
	 */
	public static boolean validateJWT(String jsonWebToken) {
		try {
			Jwts.parser().setSigningKey(generateKey(JWT_ALG, JWT_RULE)).parseClaimsJws(jsonWebToken).getBody();
			return true;
		} catch (Exception ex) {
			log.error("parseJWT jsonWebToken {}", jsonWebToken, ex);
			throw new RuntimeException("token错误，或token失效");
		}
	}

	/**
	 * 解密获取userId
	 * 
	 * @param jsonWebToken
	 * @param base64Security
	 * @return
	 */
	public static String getUserId(String jsonWebToken) {
		try {
			Claims claims = Jwts.parser().setSigningKey(generateKey(JWT_ALG, JWT_RULE)).parseClaimsJws(jsonWebToken)
					.getBody();
			return claims.getId().split("_")[0];
		} catch (Exception ex) {
			log.error("parseJWT jsonWebToken {}", jsonWebToken, ex);
			return null;
		}
	}

	/**
	 * 解密获取userId
	 * 
	 * @param jsonWebToken
	 * @param base64Security
	 * @return
	 */
	public static Long getId(String jsonWebToken) {
		try {
			Claims claims = Jwts.parser().setSigningKey(generateKey(JWT_ALG, JWT_RULE)).parseClaimsJws(jsonWebToken)
					.getBody();
			return Long.valueOf(claims.getId().split("_")[0]);
		} catch (Exception ex) {
			log.error("parseJWT jsonWebToken {}", jsonWebToken, ex);
			return null;
		}
	}

	/**
	 * 前三个参数为自己用户token的一些信息比如id，权限，名称等。不要将隐私信息放入（大家都可以获取到）
	 * 
	 * @param map
	 * @param base64Security
	 * @return
	 */
	public static String createJWT(Map<Object, Object> map) {
		// 添加构成JWT的参数
		JwtBuilder builder = Jwts.builder().setPayload(JSON.toJSONString(map))
				.signWith(JWT_ALG, generateKey(JWT_ALG, JWT_RULE)).setIssuedAt(new Date());
		// 生成JWT
		return builder.compact();
	}

	/**
	 * 
	 * 
	 * @param map
	 * @param base64Security
	 * @return
	 */
	public static String createJWT(String userId) {
		if (StringUtils.isEmpty(userId)) {
			return null;
		}
		StringBuilder userIdStr = new StringBuilder();
		userIdStr.append(userId);
		userIdStr.append("_");
		userIdStr.append(System.nanoTime());
		JwtBuilder builder = Jwts.builder().signWith(JWT_ALG, generateKey(JWT_ALG, JWT_RULE))
				.setId(userIdStr.toString());
		return builder.compact();
	}

	/**
	 * 
	 * 
	 * @param map
	 * @param base64Security
	 * @return
	 */
	public static String createJWT(Long userId) {
		if (null == userId) {
			return null;
		}
		StringBuilder userIdStr = new StringBuilder();
		userIdStr.append(userId);
		userIdStr.append("_");
		userIdStr.append(System.nanoTime());
		JwtBuilder builder = Jwts.builder().signWith(JWT_ALG, generateKey(JWT_ALG, JWT_RULE))
				.setId(userIdStr.toString());
		return builder.compact();
	}

	public static void main(String[] args) {
		// Map<Object, Object> map = new HashMap<Object, Object>();
		// map.put("province", "898765");
		// map.put("city", "898765");
		// map.put("appkey", "HMu1H/cmyKDOiHv41Y9lLROuOlOo+PPG8F4/RotRmNc=");
		// map.put("timestamp", new Date().getTime());
		// 密钥
		String token = JwtUtil.createJWT(1l);
		String token1 = JwtUtil.createJWT(1l);
		System.out.println(token);
		System.out.println(token1);
		System.out.println("JWT解密的结果：" + getUserId(token));
		System.out.println("JWT解密的结果：" + getUserId(token1));
		// System.out.println(JwtUtil.parseJWT("eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI5OTM0MTY2ODQtZ2FtZV90b2tlbl9DcUNPWUZlL1FxS1M0NGJ5RTJNMjFRIn0.WH8hKoZi-XLKNAb5ugdJnclNs02rAQ-jA74cDXZwFUw"));
	}
}