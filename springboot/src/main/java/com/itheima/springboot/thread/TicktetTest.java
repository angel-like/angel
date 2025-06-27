package com.itheima.springboot.thread;

import org.junit.Test;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.*;

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

	/**
	 * 测试 多线程按顺序返回结果信息
	 * @throws ExecutionException
	 * @throws InterruptedException
	 */
	@Test
	public void test2() throws ExecutionException, InterruptedException {
		TicktetTest ticktetTest = new TicktetTest();
		ticktetTest.doThing();
	}
	public void doThing() throws InterruptedException, ExecutionException {
		/**
		 * 创建线程池，并发量最大为5
		 * LinkedBlockingDeque，表示执行任务或者放入队列
		 */
		ThreadPoolExecutor threadPool = new ThreadPoolExecutor(5, 8, 0,
				TimeUnit.SECONDS, new LinkedBlockingQueue<>(),
				new ThreadPoolExecutor.CallerRunsPolicy());

		//存储线程的返回值
		List<Future<String>> results = new LinkedList<Future<String>>();

		for (int i = 0; i < 10; i++) {
			Task task = new Task(i);
			System.out.println("放入线程池：" + i);
			//调用submit可以获得线程的返回值
			Future<String> result = threadPool.submit(task);
			results.add(result);

		}
		//此函数表示不再接收新任务，
		//如果不调用，awaitTermination将一直阻塞
		threadPool.shutdown();
		//1小时，模拟等待
		System.out.println(threadPool.awaitTermination(1, TimeUnit.HOURS));

		//输出结果
		for (int i = 0; i < 10; i++) {
			System.out.println(results.get(i).get());
		}
	}


	private class Task implements Callable {
		private int val;

		public Task(int val) {
			this.val = val;
		}

		@Override
		public String call() throws Exception {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("完成 "+ val);
			return "返回值" + val;
		}
	}

}
