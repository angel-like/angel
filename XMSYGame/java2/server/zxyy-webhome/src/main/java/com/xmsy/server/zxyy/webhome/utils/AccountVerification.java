package com.xmsy.server.zxyy.webhome.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * .账号验证，数字加字母，首位必须字母（6-10位）
 * 
 * @author xiaoliu
 * @since 2017年8月20日
 */
public class AccountVerification {
	
	public static Boolean regularVerification(String account) {
		String regex="^[a-zA-Z](?![a-zA-Z]+$)[0-9A-Za-z]{5,9}$";
	    Matcher m=Pattern.compile(regex).matcher(account);
		return m.matches();
	}

	public static void main(String[] args) {
		System.out.println(regularVerification("阿斯蒂芬"));
	}
}


