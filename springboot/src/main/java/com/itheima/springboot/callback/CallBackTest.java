package com.itheima.springboot.callback;

public class CallBackTest {
	public static void main(String[] args) {
		Student s=new Student("小明");
		s.callHelp(4, 5);
		
		Seller se=new Seller("老婆婆");
		se.callHelp(445, 454);
	}
}
