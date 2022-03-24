package com.itheima.springboot;

import java.io.Serializable;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
/**
 * 
   	3.添加任务到线程池 
   	通过 execute(Runnable)方法被添加到线程池，任务就是一个 Runnable类型的对象，任务的执行方法就是 Runnable类型对象的run()方法。 
	当一个任务通过execute(Runnable)方法欲添加到线程池时： 
	如果此时线程池中的数量小于corePoolSize，即使线程池中的线程都处于空闲状态，也要创建新的线程来处理被添加的任务。 
	如果此时线程池中的数量等于 corePoolSize，但是缓冲队列 workQueue未满，那么任务被放入缓冲队列。 
	如果此时线程池中的数量大于corePoolSize，缓冲队列workQueue满，并且线程池中的数量小于maximumPoolSize，建新的线程来处理被添加的任务。 
	如果此时线程池中的数量大于corePoolSize，缓冲队列workQueue满，并且线程池中的数量等于maximumPoolSize，那么通过 handler所指定的策略来处理此任务。
	也就是：处理任务的优先级为： 
	核心线程corePoolSize、任务队列workQueue、最大线程maximumPoolSize，如果三者都满了，使用handler处理被拒绝的任务。 
	当线程池中的线程数量大于 corePoolSize时，如果某线程空闲时间超过keepAliveTime，线程将被终止。这样，线程池可以动态的调整池中的线程数。 
	unit可选的参数为java.util.concurrent.TimeUnit中的几个静态属性：NANOSECONDS、MICROSECONDS、MILLISECONDS、SECONDS。 
	workQueue常用的是：java.util.concurrent.ArrayBlockingQueue 
	handler有四个选择： 
	ThreadPoolExecutor.AbortPolicy()： 抛出java.util.concurrent.RejectedExecutionException异常 
	ThreadPoolExecutor.CallerRunsPolicy(): 重试添加当前的任务，他会自动重复调用execute()方法     --一般用这个
	ThreadPoolExecutor.DiscardOldestPolicy(): 抛弃旧的任务 
	ThreadPoolExecutor.DiscardPolicy(): 抛弃当前的任务
	
	4.线程池的使用场合 
	（1）单个任务处理的时间比较短； 
	（2）需要处理的任务数量大；
	
 * @author Lenovo
 *
 */
public class ThreadPoolTest {

	private static int produceTaskSleepTime = 5;//
	private static int consumeTaskSleepTime = 1000;//执行线程任务的  睡眠时间  毫秒
	private static int produceTaskMaxNumber = 10; // 定义最大添加10个线程到线程池中

	public static void main(String[] args) {
		// 构造一个线程池
		/*
		(1)corePoolSize： 线程池维护线程的最少数量 （core : 核心） 
		(2)maximumPoolSize： 线程池维护线程的最大数量 
		(3)keepAliveTime： 线程池维护线程所允许的空闲时间 
		(4)unit： 线程池维护线程所允许的空闲时间的单位 
		(5)workQueue： 线程池所使用的缓冲队列    -- 最后执行的几个线程
		(6)handler： 线程池对拒绝任务的处理策略
		 */
		ThreadPoolExecutor threadPool = new ThreadPoolExecutor(2, 4, 3, TimeUnit.SECONDS,
				new ArrayBlockingQueue<Runnable>(3), new ThreadPoolExecutor.AbortPolicy());
		for (int i = 1; i <= produceTaskMaxNumber; i++) {
			try {

				// 一个任务，并将其加入到线程池
				String work = "work@ " + i;
				System.out.println("put ：" + work);
				threadPool.execute(new ThreadPoolTask(work));
				// 便于观察，等待一段时间
				Thread.sleep(produceTaskSleepTime);
			} catch (Exception e) {
				System.out.println("异常：<<<<<<" +i);
			}
		}
		//关闭线程池 之后不能再调用了
		//threadPool.shutdown();
	}

	/**
	 * 线程池执行的任务
	 * 
	 * @author zhu
	 */
	public static class ThreadPoolTask implements Runnable, Serializable {
		private static final long serialVersionUID = 1L;
		
		private String threadPoolTaskData;
		ThreadPoolTask(String s){
			this.threadPoolTaskData=s;
		}
		@Override
		public void run() {
			System.out.println("线程执行+++"+threadPoolTaskData);
			try {
				// 便于观察，等待一段时间
				Thread.sleep(consumeTaskSleepTime);
				System.out.println("睡眠五秒后+++"+threadPoolTaskData);
			} catch (Exception e) {
				e.printStackTrace();
			}
			//threadPoolTaskData = null;
		}
		
		/*private static final long serialVersionUID = 0;
		// 保存任务所需要的数据
		private Object threadPoolTaskData;

		ThreadPoolTask(Object works) {
			this.threadPoolTaskData = works;
		}

		public void run() {
			// 处理一个任务，这里的处理方式太简单了，仅仅是一个打印语句
			System.out.println("start------" + threadPoolTaskData);
			try {
				// 便于观察，等待一段时间
				Thread.sleep(consumeTaskSleepTime);
			} catch (Exception e) {
				e.printStackTrace();
			}
			threadPoolTaskData = null;
		}

		public Object getTask() {
			return this.threadPoolTaskData;
		}*/
	}

}
