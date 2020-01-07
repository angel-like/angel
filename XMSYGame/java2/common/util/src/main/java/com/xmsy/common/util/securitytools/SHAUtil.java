package com.xmsy.common.util.securitytools;

import java.security.MessageDigest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SHAUtil {

	private static Logger logger = LoggerFactory.getLogger(SHAUtil.class);

	/**
	 * 计算SHA256
	 * 
	 * @param s
	 * @return
	 */
	public static String evalSHA256(String s) {
		if (s == null) {
			return null;
		}
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			md.update(s.getBytes("UTF-8"));
			byte[] digest = md.digest();
			return Base16.encode(digest);
		} catch (Exception e) {
			logger.error("【SHAUtil->evalSHA256】 Exception", e);
			return null;
		}
	}

	public static String evalSHA1(String s) {
		if (s == null) {
			return null;
		}

		try {
			MessageDigest md = MessageDigest.getInstance("SHA-1");
			md.update(s.getBytes("UTF-8"));
			byte[] digest = md.digest();
			return Base16.encode(digest);
		} catch (Exception e) {
			logger.error("【SHAUtil->evalSHA1】 Exception", e);
			return null;
		}
	}

	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println(evalSHA256("admin"));
		System.out.println(evalSHA1("a11111dmiDFSDFSDn").length());
	}
}
