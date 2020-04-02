package com.itheima.springboot.callback;

/**
 * 小红的超级计算器：
 * 		把回调接口传进来，实现回调接口的类->就能实现回调功能
 * @author Lenovo
 *
 */
public class SuperCalculator {
	public void add(int a, int b, DoJob customer) {
		int result = a + b;//1.通过传入的数据     ->生成想要的数据 
		customer.fillBlank(a, b, result);//2. 通过接口抽象方法传出去
	}
}
