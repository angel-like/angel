package com.itheima.springboot.callback.simple;

/**
 * A里面有 a()方法 callback()方法
 * B里有b()方法
 * 			a()方法调用b(),b()方法再调用callback()方法
 * @author Lenovo
 *
 */
public class simpleTest {
	public static void main(String[] args) {
		StudengXiaoMing sxm=new StudengXiaoMing("小明");
		//小明的callHelp()方法里调用小红的  add()方法--->小红的add()方法里再调用小明的callback方法
		sxm.callHelp(5, 5);
	}
}
