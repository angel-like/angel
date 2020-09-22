package com.itheima.springboot.callback;

/**
 * 小红的回调接口
 * 
 * @author Lenovo
 *
 */
public interface DoJob {
	//只有公共属性 默认以 public static 修饰静态变量
	//默认以public abstract 修饰方法 (default除外了)
	Integer i = 1;

	public abstract void fillBlank(int a, int b, int result);

	public  default void testDefault() {
		System.out.println(i + "-----接口默认方法\n");
	}
}
