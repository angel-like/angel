package com.xmsy.network.pay.paybase.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import lombok.extern.slf4j.Slf4j;

/**
 * .MD5加密
 * 
 * @author aleng
 * @date 2018年12月3日
 * @version 1.0
 */
@Slf4j
public class Md5Util {

	public static String md5(String str) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(str.getBytes());
			byte[] byteDigest = md.digest();
			int i;
			// 字符数组转换成字符串
			StringBuffer buf = new StringBuffer("");
			for (int offset = 0; offset < byteDigest.length; offset++) {
				i = byteDigest[offset];
				if (i < 0)
					i += 256;
				if (i < 16)
					buf.append("0");
				buf.append(Integer.toHexString(i));
			}
			// 32位加密
			return buf.toString().toUpperCase();
			// 16位的加密
			// return buf.toString().substring(8, 24).toUpperCase();
		} catch (NoSuchAlgorithmException e) {
			log.error("[Md5Util]->md5 NoSuchAlgorithmException", e);
			return null;
		}
	}

}
