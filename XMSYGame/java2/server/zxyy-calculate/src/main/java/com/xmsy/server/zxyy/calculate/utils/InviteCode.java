package com.xmsy.server.zxyy.calculate.utils;

import java.util.Random;

/**
 * 邀请码生成工具(6位数字加字母)
 * 
 * @author Administrator
 *
 */
public class InviteCode {
	
	/**
	 * 
	 * 
	 * @param 
	 * @param 生成6为数字加字母邀请码
	 * @return
	 */
	public static String create(){
		 char[] c=charArray();
	        Random rd = new Random();
	        String code="";
	        for (int k = 0; k < 6; k++) {
	            int index = rd.nextInt(c.length);//随机获取数组长度作为索引
	            code+=c[index];//循环添加到字符串后面
	        } 
	        return code;
	 }
	/**
	 * java 生成8位数字作为邀请码
	 * @return
	 */
	public static String createRecommendationCode(){
	    Random random = new Random();
	    StringBuilder result=new StringBuilder();
	    //首字母不能为0
	    result.append(random.nextInt(9)+1);
	    for(int i=0;i<7;i++){
	    	result.append(random.nextInt(10));
	    }
	    return result.toString();
	}
	 public static char[] charArray(){
		int i = 1234567890;
		String s = "qwertyuiopasdfghjklzxcvbnm";
		String word = s + i;
		char[] c = word.toCharArray();

		return c;

	}
	
}