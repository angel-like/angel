package com.xmsy.server.zxyy.manager.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * .密码验证，数字加字母（6-12位）
 * 
 * @author xiaoliu
 * @since 2017年8月20日
 */
public class PasswordVerification {
	
	public static Boolean regularVerification(String password) {
		if(password.length() < 6 && password.length() > 10) {
			return false;
		}
		String regex="^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,10}$";
	    Matcher m=Pattern.compile(regex).matcher(password);
		return m.matches();
	}
	
	public static Boolean otpVerification(String code) {
		if(code.length() != 6) {
			return false;
		}
		String regex="[0-9]*";
	    Matcher m=Pattern.compile(regex).matcher(code);
		return m.matches();
	}

}
