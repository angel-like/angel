package com.itheima.springboot.callback;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Seller {
	private String name = null;
	 /**
	  * 算账是调用小红的接口   实现回调函数
	  * @author Lenovo 
	  *
	  */
	 public class DoCalculator implements DoJob {
		@Override
		public void fillBlank(int a, int b, int result) {//小红那边通过接口把数据传出来，这边通过实现类重写抽象方法->对接口方法 传过来数据  操作
			for(int i=0;i<10000;i++) {
				if(i==9999) {
					System.out.println(i);
				}
			}
			System.out.println(name + "求助小红算账:" + a + " + " + b + " = " + result);
		}
	 }
	 /**
	  * 去请求帮助方法 
	  * @param a
	  * @param b
	  */
	public void callHelp(int a, int b) {
		//创建做作业对象，去求小红帮助
		new SuperCalculator().add(a, b, new DoCalculator());
	}
}
