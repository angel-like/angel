package com.xmsy.server.zxyy.manager.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * .密码验证，数字加字母（6-12位）
 * 
 * @author xiaoliu
 * @since 2017年8月20日
 */
public class VerificationUitl {

	/**
	 * 手机号检验
	 * 
	 * @param cardNum
	 * @return
	 */
	public static Boolean phoneVerification(String phone) {
		if (null == phone || phone.length() != 11) {
			return false;
		}
		String regex = "[0-9]*";
		Matcher m = Pattern.compile(regex).matcher(phone);
		return m.matches();
	}

	/**
	 * 银行卡号检验
	 * 
	 * @param cardNum
	 * @return
	 */
	public static Boolean bankCardVerification(String cardNum) {
		if (null == cardNum || cardNum.length() < 16 || cardNum.length() > 19) {
			return false;
		}
		String regex = "[0-9]*";
		Matcher m = Pattern.compile(regex).matcher(cardNum);
		return m.matches();
	}

	/**
	 * 真实姓名校验
	 * 
	 * @param cardNum
	 * @return
	 */
	public static Boolean userNameVerification(String userName) {
		if (null == userName || userName.length() < 2 || userName.length() > 20) {
			return false;
		}
		String regex = "^[A-Za-z\\u4e00-\\u9fa5]+$";
		Matcher m = Pattern.compile(regex).matcher(userName);
		return m.matches();
	}

	/**
	 * 支付宝账号校验
	 * 
	 * @param cardNum
	 * @return
	 */
	public static Boolean alipayAccountVerification(String alipayAccount) {
		if (null == alipayAccount || alipayAccount.length() < 2 || alipayAccount.length() > 30) {
			return false;
		}
		// 手机号
		String phoneRegex = "^1[3|4|5|8|6|7|9|2][0-9]\\d{8}$";
		Matcher phoneMatcher = Pattern.compile(phoneRegex).matcher(alipayAccount);
		if (!phoneMatcher.matches()) {
			String emialRegex = "\\w+(\\.\\w)*@\\w+(\\.\\w{2,3}){1,3}";
			Matcher emialMatcher = Pattern.compile(emialRegex).matcher(alipayAccount);
			return emialMatcher.matches();
		} else {
			return true;
		}
	}

	/**
	 * 用户头像校验
	 * 
	 * @param cardNum
	 * @return
	 */
	public static Boolean portraitVerification(String portrait) {
		if (null == portrait || portrait.length() < 2 || portrait.length() > 10) {
			return false;
		}
		return true;
	}

	// public static void main(String[] args) {
	// System.out.println(userNameVerification("啊啊aaaa"));
	// }

}
