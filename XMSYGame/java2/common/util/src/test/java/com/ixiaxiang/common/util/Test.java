package com.ixiaxiang.common.util;

import java.sql.Timestamp;

public class Test{
	
	public static void main(String[] args) {
//		String regex = "^[a-zA-Z][a-zA-Z0-9]{5,17}$";
//		String regex1="\\d{6,16}$";
//		System.out.println("332".matches(regex));
//		System.out.println("111111a".matches(regex1));
		Timestamp d = new Timestamp(System.currentTimeMillis()); 
		System.out.println(d.toString());
		
	}
}

