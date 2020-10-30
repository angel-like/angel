package com.itheima.springboot.callback;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student {
	 private String name = null;
	 
	 /**
	  * 做作业是调用小红的接口   实现回调函数
	  * @author Lenovo 
	  *
	  */
	 public class DoHomeWork implements DoJob {
		@Override
		public void fillBlank(int a, int b, int result) {
			System.out.println(name + "求助小红计算:" + a + " + " + b + " = " + result);
		}
		/*
		 * 在不写@Override注解的情况下，当基类存在与子类各种条件都符合的方法时实现覆盖；
		 * 如果条件不符合时，则是当成新定义的方法使用。
		 * 覆盖基类方法时，最好还是写上@Override注解，这样有利于编译器帮助检查错误
		 */
		@Override//加不加注解不影响 都是重写父类方法
		public void testDefault() {
			 
			System.out.println("-----小明重写接口默认方法(有加注解@Override)\n");
		}
	 }
	 /**
	  * 去请求帮助方法 
	  * @param a
	  * @param b
	  */
	public void callHelp(int a, int b) {
		//创建做作业对象，去求小红帮助
		new SuperCalculator().add(a, b, new DoHomeWork());
	}
}
