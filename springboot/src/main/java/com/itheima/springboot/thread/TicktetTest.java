package com.itheima.springboot.thread;
/*
 * 模拟火车站售票
 * 			分析：
 * 
 * static void sleep(long millis);让当前线程睡一会
 * 

 * 
 */
public class TicktetTest {
	public static void main(String[] args) {
		TicketThread tt=new TicketThread();//△△△ 只能有一个 共享票数
		Thread t1=new Thread(tt);
		t1.setName("窗口1");
		Thread t2=new Thread(tt);
		t2.setName("窗口2");
		Thread t3=new Thread(tt);
		t3.setName("窗口3");
		
		t1.start();
		t2.start();
		t3.start();
		
	}
}
