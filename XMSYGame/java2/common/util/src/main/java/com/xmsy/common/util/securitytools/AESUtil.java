package com.xmsy.common.util.securitytools;

import java.io.UnsupportedEncodingException;
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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;

public class AESUtil {

	private static Logger logger = LoggerFactory.getLogger(AESUtil.class);

	public static final String KEY = "89346a07e00204dec96d3ff8fa155f98";

	/**
	 * 加密
	 * 
	 * @param content
	 *            待加密内容
	 * @param key
	 *            加密的密钥
	 * @return
	 */
	public static String encrypt(String content) {
		try {
			KeyGenerator kgen = KeyGenerator.getInstance("AES");
			kgen.init(128, new SecureRandom(KEY.getBytes()));
//			SecretKey secretKey = kgen.generateKey();
			SecretKey secretKey=getKey(KEY);
			byte[] enCodeFormat = secretKey.getEncoded();
			SecretKeySpec secretKeySpec = new SecretKeySpec(enCodeFormat, "AES");
			Cipher cipher = Cipher.getInstance("AES");
			byte[] byteContent = content.getBytes("utf-8");
			cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
			byte[] byteRresult = cipher.doFinal(byteContent);
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < byteRresult.length; i++) {
				String hex = Integer.toHexString(byteRresult[i] & 0xFF);
				if (hex.length() == 1) {
					hex = '0' + hex;
				}
				sb.append(hex.toUpperCase());
			}
			return sb.toString();
		} catch (NoSuchAlgorithmException e) {
			logger.error("【AESUtil->encrypt】NoSuchAlgorithmException", e);
		} catch (NoSuchPaddingException e) {
			logger.error("【AESUtil->encrypt】NoSuchPaddingException", e);
		} catch (InvalidKeyException e) {
			logger.error("【AESUtil->encrypt】InvalidKeyException", e);
		} catch (UnsupportedEncodingException e) {
			logger.error("【AESUtil->encrypt】UnsupportedEncodingException", e);
		} catch (IllegalBlockSizeException e) {
			logger.error("【AESUtil->encrypt】IllegalBlockSizeException", e);
		} catch (BadPaddingException e) {
			logger.error("【AESUtil->encrypt】BadPaddingException", e);
		}
		return null;
	}

	/**
	 * 解密
	 * 
	 * @param content
	 *            待解密内容
	 * @param key
	 *            解密的密钥
	 * @return
	 */
	public static String decrypt(String content) {
		if (content.length() < 1)
			return null;
		byte[] byteRresult = new byte[content.length() / 2];
		for (int i = 0; i < content.length() / 2; i++) {
			int high = Integer.parseInt(content.substring(i * 2, i * 2 + 1), 16);
			int low = Integer.parseInt(content.substring(i * 2 + 1, i * 2 + 2), 16);
			byteRresult[i] = (byte) (high * 16 + low);
		}
		try {
			KeyGenerator kgen = KeyGenerator.getInstance("AES");
			kgen.init(128, new SecureRandom(KEY.getBytes()));
//			SecretKey secretKey = kgen.generateKey();
			SecretKey secretKey=getKey(KEY);
			byte[] enCodeFormat = secretKey.getEncoded();
			SecretKeySpec secretKeySpec = new SecretKeySpec(enCodeFormat, "AES");
			Cipher cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
			byte[] result = cipher.doFinal(byteRresult);
			return new String(result);
		} catch (NoSuchAlgorithmException e) {
			logger.error("【AESUtil->decrypt】NoSuchAlgorithmException", e);
		} catch (NoSuchPaddingException e) {
			logger.error("【AESUtil->decrypt】NoSuchPaddingException", e);
		} catch (InvalidKeyException e) {
			logger.error("【AESUtil->decrypt】InvalidKeyException", e);
		} catch (IllegalBlockSizeException e) {
			logger.error("【AESUtil->decrypt】IllegalBlockSizeException", e);
		} catch (BadPaddingException e) {
			logger.error("【AESUtil->decrypt】BadPaddingException", e);
		}
		return null;
	}

	public static SecretKey getKey(String strKey) {
		try {
			KeyGenerator _generator = KeyGenerator.getInstance("AES");
			SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
			secureRandom.setSeed(strKey.getBytes());
			_generator.init(128, secureRandom);
			return _generator.generateKey();
		} catch (Exception e) {
			throw new RuntimeException(" 初始化密钥出现异常 ");
		}
	}

	public static void main(String[] args) {
		JSONObject aa = new JSONObject();
		// aa.put("operationType", "test");
		// aa.put("msgContent", "message");
		aa.put("ip", "10.0.0.219");
		aa.put("account", "test559");
		aa.put("recommendationCode", "123457");
		aa.put("token", "Ob9EECTqTcOVcLELE4AxnQ");
		System.out.println(encrypt(aa.toString()));
	}

}
