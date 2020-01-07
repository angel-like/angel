package com.xmsy.common.util.securitytools;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import org.apache.commons.codec.binary.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * .MD5算法和MD5加盐算法
 * 
 * @author chenjisi
 * @since 2017年7月1日
 */
public class MD5Util {

	private static Logger logger = LoggerFactory.getLogger(MD5Util.class);

	/**
	 * 普通MD5
	 * 
	 * @param inStr
	 * @return
	 */
	public static String MD5(String param) {
		MessageDigest md5 = null;
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			logger.error("【MD5Util->MD5】 NoSuchAlgorithmException check jdk", e);
			return "check jdk";
		} catch (Exception e) {
			logger.error("【MD5Util->MD5】 Exception ", e);
			return "";
		}
		char[] charArray = param.toCharArray();
		byte[] byteArray = new byte[charArray.length];

		for (int i = 0; i < charArray.length; i++)
			byteArray[i] = (byte) charArray[i];
		byte[] md5Bytes = md5.digest(byteArray);
		StringBuffer hexValue = new StringBuffer();
		for (int i = 0; i < md5Bytes.length; i++) {
			int val = ((int) md5Bytes[i]) & 0xff;
			if (val < 16)
				hexValue.append("0");
			hexValue.append(Integer.toHexString(val));
		}
		return hexValue.toString();

	}

	/**
	 * 加盐MD5
	 * 
	 * @param password
	 * @return
	 */
	public static String generate(String password) {
		if (null == password) {
			return null;
		}
		Random r = new Random();
		StringBuilder sb = new StringBuilder(16);
		sb.append(r.nextInt(99999999)).append(r.nextInt(99999999));
		int len = sb.length();
		if (len < 16) {
			for (int i = 0; i < 16 - len; i++) {
				sb.append("0");
			}
		}
		String salt = sb.toString();
		password = md5Hex(password + salt);
		char[] cs = new char[48];
		for (int i = 0; i < 48; i += 3) {
			cs[i] = password.charAt(i / 3 * 2);
			char c = salt.charAt(i / 3);
			cs[i + 1] = c;
			cs[i + 2] = password.charAt(i / 3 * 2 + 1);
		}
		return new String(cs);
	}

	/**
	 * 校验加盐后是否和原文一致
	 * 
	 * @param password
	 * @param md5
	 * @return
	 */
	public static boolean verify(String password, String md5) {
		char[] cs1 = new char[32];
		char[] cs2 = new char[16];
		for (int i = 0; i < 48; i += 3) {
			cs1[i / 3 * 2] = md5.charAt(i);
			cs1[i / 3 * 2 + 1] = md5.charAt(i + 2);
			cs2[i / 3] = md5.charAt(i + 1);
		}
		String salt = new String(cs2);
		return md5Hex(password + salt).equals(new String(cs1));
	}

	/**
	 * 获取十六进制字符串形式的MD5摘要
	 */
	private static String md5Hex(String src) {
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			byte[] bs = md5.digest(src.getBytes());
			return new String(new Hex().encode(bs));
		} catch (Exception e) {
			logger.error("【MD5Util->md5Hex】 Exception", e);
			return null;
		}
	}

	public static void main(String args[]) {
		// 原文
		String plaintext = "783479";
		System.out.println("原始：" + plaintext);
		System.out.println("普通MD5后：" + MD5Util.MD5(plaintext));

		// 获取加盐后的MD5值
		String ciphertext = MD5Util.generate(plaintext);
		// 获取加盐后的MD5值
		System.out.println("加盐后MD5：" + ciphertext);
		System.out.println("是否是同一字符串:" + MD5Util.verify(plaintext, ciphertext));
		/**
		 * 其中某次DingSai字符串的MD5值（存数据库）
		 */
		String tempSalt = "15273d85277409d374e75b0437ab15327c1f21ed98645b78";
		System.out.println("是否是同一字符串:" + MD5Util.verify(plaintext, tempSalt));
	}

}