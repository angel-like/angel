package com.itheima.springboot.thread;

public class MyThread extends Thread {
	private static Integer i = 100;
	Object obj = new Object();
 
	@Override
	public void run() {

		while (true) {
			synchronized (obj) {//加了这个  使排序正常  然后一个个-1下去。不加的话  调用很乱
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				if (i > 0) {
					System.out.println(Thread.currentThread().getName() + ":" + i--);
				}
			}
		}

	}
}
