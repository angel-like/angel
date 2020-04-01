package com.itheima.springboot.callback.simple;

public class SuperCalculatorXiaoHong {
	/**
	 * 小红的计算方法
	 * @param a
	 * @param b
	 * @param s
	 */
	public void add(int a,int b,StudengXiaoMing s) {
		int result=a+b;//1.小红计算
		s.callBack(a, b, result);//计算完回调 小明的方法去通知小明做好
	}
}
