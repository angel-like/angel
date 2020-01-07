package com.xmsy.network.util;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.Security;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.net.util.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

public class utils {
	private static final String KEY_ALGORITHM = "AES";
	private static final String DEFAULT_CIPHER_ALGORITHM = "AES/ECB/PKCS7Padding";// 默认的加密算法

	/**
	 * 生成加密秘钥
	 */
	private static SecretKeySpec getSecretKey(final String password) {
		byte[] keyBytes = password.getBytes();
		Security.addProvider(new BouncyCastleProvider());
		return new SecretKeySpec(keyBytes, KEY_ALGORITHM);
	}

	/**
	 * AES 加密
	 * 
	 * @param encodeRules
	 *            密钥
	 * @param content
	 *            内容
	 */
	public static String AESEncode(String encodeRules, String content) {
		byte[] byteContent = null;
		try {
			Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
			Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);
			byteContent = content.getBytes("utf-8");
			cipher.init(Cipher.ENCRYPT_MODE, getSecretKey(encodeRules));
			byte[] result = cipher.doFinal(byteContent); // 加密
			return Base64.encodeBase64String(result); // 通过Base64转码返回
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
		// return content;
	}

	/**
	 * AES 解密
	 * 
	 * @param encodeRules
	 *            密钥
	 * @param content
	 *            内容
	 */
	public static String AESDecode(String encodeRules, String content) {
		try {
			Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
			// 实例化
			Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);
			// 使用密钥初始化，设置为解密模式
			cipher.init(Cipher.DECRYPT_MODE, getSecretKey(encodeRules));
			// 执行操作
			byte[] result = cipher.doFinal(Base64.decodeBase64(content));
			return new String(result, "utf-8");
		} catch (Exception e) {
			e.printStackTrace();
		}

		// return content;
		return null;
	}

	/** RSA 加解密 */
	// 生成秘钥对
	public static KeyPair getKeyPair() {
		try {
			KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
			keyPairGenerator.initialize(1024);
			KeyPair keyPair = keyPairGenerator.generateKeyPair();
			return keyPair;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	// 获取公钥(Base64编码)
	public static String getPublicKey(KeyPair keyPair) {
		RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
		byte[] bytes = publicKey.getEncoded();
		return Base64.encodeBase64String(bytes);
	}

	// 获取公钥(Base64编码)
	public static String getPrvlicKey(KeyPair keyPair) {
		RSAPrivateKey publicKey = (RSAPrivateKey) keyPair.getPrivate();
		byte[] bytes = publicKey.getEncoded();
		return Base64.encodeBase64String(bytes);
	}

	public static byte[] hexStringToByte(String hex) {
		int len = (hex.length() / 2);
		byte[] result = new byte[len];
		char[] achar = hex.toCharArray();
		String str = "0123456789ABCDEF";
		for (int i = 0; i < len; i++) {
			int pos = i * 2;
			result[i] = (byte) (str.indexOf(achar[pos]) << 4 | str.indexOf(achar[pos + 1]));
		}
		return result;
	}

	// 私钥解密
	public static String privateDecrypt(String content, PrivateKey privateKey) throws Exception {
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.DECRYPT_MODE, privateKey);
		byte[] bytes = cipher.doFinal(Base64.decodeBase64(content));
		return new String(bytes, "utf-8");
	}
}
