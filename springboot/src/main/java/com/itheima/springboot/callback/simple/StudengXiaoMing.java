package com.itheima.springboot.callback.simple;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudengXiaoMing {
	private String name = null;
	/**
	 * 小明的方法
	 */
	public void callHelp(int a ,int b) {
		new SuperCalculatorXiaoHong().add(a, b, this);
	}
	
	public void callBack(int a, int b, int result) {
		 System.out.println(name + "求助小红计算:" + a + " + " + b + " = " + result);
	}
}
