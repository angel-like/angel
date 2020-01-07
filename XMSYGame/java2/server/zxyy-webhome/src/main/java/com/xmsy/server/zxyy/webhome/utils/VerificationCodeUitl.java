package com.xmsy.server.zxyy.webhome.utils;

import java.util.Random;

/**
 * .验证码生成工具
 * 
 * @author aleng
 * @since 2017年8月20日
 */
public class VerificationCodeUitl {

	/**
	 * 获取手机验证码
	 * 
	 * @param cardNum
	 * @return
	 */
	public static String createVerificationCode() {
		return String.valueOf(100000 + new Random().nextInt(899999));
	}

}
