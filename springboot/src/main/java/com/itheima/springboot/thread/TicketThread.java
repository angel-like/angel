package com.itheima.springboot.thread;
/*
 * 问题出现的原因：
 * 		要有多个线程
 * 		要有被多个线程所共享的数据
 * 		多个线程并发的访问共享的数据
 * 
 * 在火车上上厕所
 * 张三来了，一看门是绿的，他就进去了，把门锁上了，门就变红了
 * 李四来了，一看门市红色的，他就只能憋着
 * 张三用完了厕所，把锁打开了，门就变成了绿色
 * 李四一看门变绿了，他就进去了，把门锁上，门就变红了
 * 王五来了，一看们是红色的，他也只能憋着
 * 李四用完测试了，把锁打开了，肚子又不舒服了，扭头回去了，又把门锁上了，
 * 
 * synchronized:同步（锁），可以修饰代码块和方法，被修饰的代码块和方法
 * 				一旦被某个线程访问，则直接锁住，其他的线程将无法访问
 * 
 * 同步代码块：
 * 			synchronized(锁对象){
 * 
 * 			}
 * 
 * 注意：锁对象需要被所有的线程所共享
 * 
 * 
 * 同步：安全性高，效率低
 * 非同步：效率高，但是安全性低
 * 
 */

/*
 * 同步方法:使用关键字synchronized修饰的方法，一旦被一个线程访问，
 * 	               则整个方法全部锁住，其他线程则无法访问
 * 
 * synchronized
 * 注意：
 * 		非静态同步方法的锁对象是this
 * 		静态的同步方法的锁对象是当前类的字节码对象
 */
public class TicketThread implements Runnable {
	static int tickets = 100;// 为了共享车票，只能有一个Runnable对象
	Object obj = new Object();

	@Override
	public void run() {
		// 出售火车票
		while (true) {			
		/*	synchronized (obj) {
				method1();
			}*/
			//method();
			method1();
		}
	}

	@SuppressWarnings("unused")
	private synchronized void method() {
		// 当火车票大于0开始售票
		if (tickets > 0) {
			/*
			 * t1,t2,t3 假设只剩一张票 t1过来了，他一看有票，他就进来了，但是他突然肚子不舒服，然后他就去上卫生间了
			 * t2也过来了，他一看也有票，他也进来了，但是他的肚子也不舒服，他也去上卫生间了
			 * 
			 * t1上完了卫生间回来了，开始售票 tickets = 0; t2也上完卫生间回来了，他也进行售票 tickets
			 * = -1;
			 * 
			 * 
			 */
			try {
				//static void sleep(long millis);让当前线程睡一会
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(Thread.currentThread().getName() + ":" + tickets--);
		}
	}
	private static synchronized void method1() {
		//静态方法随着类的加载而加载优先所有对象
		// 当火车票大于0开始售票
		if (tickets > 0) {
			/*
			 * t1,t2,t3 假设只剩一张票 t1过来了，他一看有票，他就进来了，但是他突然肚子不舒服，然后他就去上卫生间了
			 * t2也过来了，他一看也有票，他也进来了，但是他的肚子也不舒服，他也去上卫生间了
			 * 
			 * t1上完了卫生间回来了，开始售票 tickets = 0; t2也上完卫生间回来了，他也进行售票 tickets
			 * = -1;
			 * 
			 * 
			 */
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(Thread.currentThread().getName() + ":" + tickets--);
		}
	}
}
