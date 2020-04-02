package com.itheima.springboot.callback;


public class CallBackTest {
	public static void main(String[] args) {
		Student s=new Student("小明");
		s.callHelp(4, 5);
		
		Seller se=new Seller("老婆婆");
		se.callHelp(445, 454);
		
		
		DoJob doJob = new DoJob (){
			@Override
			public void fillBlank(int a, int b, int result) {
				System.out.println("主方法直接获取结果："+result);
			}
		};
		new SuperCalculator().add(11, 44, doJob);
	}
}
