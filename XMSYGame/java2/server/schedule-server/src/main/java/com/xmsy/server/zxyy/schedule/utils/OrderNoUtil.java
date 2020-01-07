package com.xmsy.server.zxyy.schedule.utils;

import java.util.Date;

import org.apache.commons.lang.RandomStringUtils;

public class OrderNoUtil {

	public static String getOrderNo() {
		return DateUtils.format(new Date(), "yyyyMMddHHmmssSSS")
				+RandomStringUtils.randomNumeric(4);
	}
	/**
	 * 创建一个随机字符串，其长度是指定的字符数，
	 * 字符将从拉丁字母（a-z、A-Z）和数字0-9中选择。
	 * @param len
	 * @return
	 */
	public static String getRandomAlphanumeric(int len) {
		return RandomStringUtils.randomAlphanumeric(len);
	}
	public static void main(String[] args) {
		
		System.out.println(getOrderNo());
	}

}
