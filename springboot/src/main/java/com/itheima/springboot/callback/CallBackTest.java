package com.itheima.springboot.callback;


import java.util.TreeSet;

public class CallBackTest {
	public static void main(String[] args) {
		Student s=new Student("小明");
		s.callHelp(4, 5);
		//TreeSet set=new TreeSet();
		//set.add(s);//对象，没有实现Comparable接口，所以报错
		Seller se=new Seller("老婆婆");
		se.callHelp(445, 454);
		
		
		DoJob doJob = new DoJob (){
			@Override
			public void fillBlank(int a, int b, int result) {
				System.out.println("主方法直接获取结果："+result);
			}
			public void testDefault() {
				System.out.println("-----主类重写接口默认方法(不加注解@Override)\n");
				//DoJob.super.testDefault();//重新调用父类方法
			}
		};
		new SuperCalculator().add(11, 44, doJob);
		
		
		//====接口有公用变量
		//System.out.println("接口公用变量为："+DoJob.i);
		
	}
}
