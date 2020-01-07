package com.xmsy.server.zxyy.calculate.utils;

import java.util.Random;

/**
 * 随机码生成工具(8位数字加字母)
 * 
 * @author Administrator
 *
 */
public class UniqueCodeUtil {
	
	/**
	 * 
	 * 
	 * @param 
	 * @param 生成8为数字加字母随机码
	 * @return
	 */
	public static String create(){
		 char[] c=charArray();
	        Random rd = new Random();
	        String code="";
	        for (int k = 0; k < 8; k++) {
	            int index = rd.nextInt(c.length);//随机获取数组长度作为索引
	            code+=c[index];//循环添加到字符串后面
	        } 
	        return code;
	 }
	
	 public static char[] charArray(){
		int i = 1234567890;
		String s = "qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM";
		String word = s + i;
		char[] c = word.toCharArray();

		return c;

	}
	
}