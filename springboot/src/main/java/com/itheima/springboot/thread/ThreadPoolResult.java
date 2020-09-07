package com.itheima.springboot.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.FutureTask;

/**
 * Thread.join()：当前线程会立即被执行,其他所有的线程会被暂停执行.当这个线程执行完后,其他线程才会继续执行.
 * 
 * 
 *  （1）通过一个 Callable<V> 任务或者一个 Runnable（一开始就指定 result）任务构造 FutureTask<V>；
 *	（2）将 FutureTask<V> 交给 Thread 去运行；
 *	（3）使用 FutureTask<V>的 get 方法（或者 Thread 的 join 方法）阻塞当前线程直到获得任务的结果。
 *
 * @author Lenovo
 *
 */
public class ThreadPoolResult {

	public static void main(String[] args) throws Exception {
		
		//basic();// 初学者 测试

		
		// 一、Runnable 获取返回结果 基本调用方式
		runnableMethod();
		
		
		// 二、：使用 Callable<V> 和 FutureTask<V> 进阶方式
		
		System.out.println("使用 Callable 获得返回结果：");

		List<FutureTask<Integer>> futureTaskList = new ArrayList<>(10);
		for (int i = 0; i < 10; i++) {
			ThreadPoolResultCallable task = new ThreadPoolResultCallable(i, 2 * i);//(i * 10 + 1, (i + 1) * 10)
			//1.通过一个 Callable<V> 任务或者一个 Runnable（一开始就指定 result）任务构造 FutureTask<V>
			FutureTask<Integer> futureTask = new FutureTask<>(task);
			
			futureTaskList.add(futureTask);

			Thread worker = new Thread(futureTask, "慢速累加器线程" + i);
			worker.start(); //2.将 FutureTask<V> 交给 Thread 去运行；  这里可以优化成 直接一整个list线程  交给线程池执行
		}
		int total = 0;
		for (FutureTask<Integer> futureTask : futureTaskList) {
			//3.使用 FutureTask<V>的 get 方法（或者 Thread 的 join 方法）阻塞当前线程直到获得任务的结果。
			total += futureTask.get(); // get() 方法会阻塞直到获得结果
		}
		System.out.println("累加的结果: " + total);
	}

	/**
	 * 使用Runnable 获取返回值 
	 *     1.需要在自定义线程类里  定义全局变量
	 *     2.把值赋予给  全局变量里
	 *     3.通过get方法获取 全局变量
	 * @throws Exception
	 */
	public static void runnableMethod() throws Exception {
		System.out.println("使用 Runnable 获得返回结果：");

		List<Thread> workers = new ArrayList<>(10);
		List<ThreadPoolResultRunnable> tasks = new ArrayList<>(10);

		// 新建 10 个线程，每个线程分别负责累加 1~10, 11~20, ..., 91~100
		for (int i = 0; i < 10; i++) {
			ThreadPoolResultRunnable task = new ThreadPoolResultRunnable(i, 2 * i);// (i * 10 + 1, (i + 1) * 10)
			Thread worker = new Thread(task, "慢速累加器线程" + i);

			tasks.add(task);
			workers.add(worker);

			worker.start();
		}

		int total = 0;
		for (int i = 0, s = workers.size(); i < s; i++) {
			workers.get(i).join(); // 等待线程执行完毕 当调用了 Thread.Join()方法后,当前线程会立即被执行,其他所有的线程会被暂停执行.当这个线程执行完后,其他线程才会继续执行.
			total += tasks.get(i).getResult();
		}

		System.out.println("\n累加的结果: " + total+"\n\n");
	}

	public static void basic() throws Exception {
		MyThread tt = new MyThread();// △△△只能有一个 共享一个参数
		Thread t1 = new Thread(tt, "窗口1");
		// t1.setName("窗口1");
		Thread t2 = new Thread(tt);
		t2.setName("窗口2");
		Thread t3 = new Thread(tt);
		t3.setName("窗口3");
		t1.start();
		// t1.join(); //开启后只有t1运行 ，其他 线程都不运行 ，包括runnableMethod() 里面的主线程
		t2.start();
		// t2.join(); //开启后 只有 t1 t2 运行(因为t1运行未结束)
		t3.start();

	}

}
