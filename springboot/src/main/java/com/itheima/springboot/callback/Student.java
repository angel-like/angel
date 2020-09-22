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
		
		public void testDefault() {
			System.out.println("-----小明重写接口默认方法\n");
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
