package com.xmsy.server.zxyy.game.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * .密码验证，数字加字母（6-10位）
 * 
 * @author xiaoliu
 * @since 2017年8月20日
 */
public class PasswordVerification {
	
	public static Boolean regularVerification(String cardNum) {
		if(cardNum.length() < 6 && cardNum.length() > 10) {
			return false;
		}
		String regex="^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,10}$";
	    Matcher m=Pattern.compile(regex).matcher(cardNum);
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
