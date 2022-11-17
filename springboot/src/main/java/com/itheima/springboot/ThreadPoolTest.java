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
		(2)maximumPoolSize： 线程池维护线程的最大数量    核心满了，工作队列也满了，才会创建的
		(3)keepAliveTime： 线程池维护 最大线程 所允许的空闲时间
		(4)unit： 线程池维护线程所允许的空闲  时间的单位
		(5)workQueue： 线程池所使用的缓冲队列    -- 最后执行的几个线程   (FIFO先进先出队列、双端队列、阻塞队列)这里用的就是阻塞队列
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
				//最大线程4+队列3 =7个 所以后面3个抛异常
				System.out.println("异常：<<<<<<" +i);
			}
		}
		//关闭线程池 之后不能再调用了
		threadPool.shutdown();
		/*
		ThreadPoolExecutor 里的
			// 线程池中的运算重点    TODO   32位-> 前三位标识线程池状态，后29位标识线程池数量
			1). private final AtomicInteger ctl = new AtomicInteger(ctlOf(RUNNING, 0));本质int类型的数  前三位标识线程池状态，后29位标识线程池数量
			2). private static final int COUNT_BITS = Integer.SIZE - 3;			本质就是29，方便ctl做运算的常量
			//线程池的最大容量
    		3). private static final int CAPACITY   = (1 << COUNT_BITS) - 1;	线程池的最大容量 28个1组成
    		//线程池状态
    		4). private static final int RUNNING    = -1 << COUNT_BITS;   	111 正常运行
    		5). private static final int SHUTDOWN   =  0 << COUNT_BITS;		000 线程池ShutDown，继续执行剩下的任务（未完成任务继续执行）
    		6). private static final int STOP       =  1 << COUNT_BITS;		001 线程池ShutDownNow，并且所有任务中断
    		7). private static final int TIDYING    =  2 << COUNT_BITS;		010 ShutDown或 ShutDownNow之后，任务都被处理完，到这个过渡状态
    		8). private static final int TERMINATED =  3 << COUNT_BITS;		011	线程池停止 （TIDYING状态后就为了执行 TERMINATED线程终止）

    		里面都是基于Worker（包含Thread thread 成员变量）类去 做start操作  ，放进HashSet<Worker> workers里  ,
    		执行start()操作是 执行Worker里的run（）{ runWork(); }方法； 里面调用runWork();
    		runWork() 里面才是调用真正任务的run方法
		 */
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
		/**
		 * 队列信息
		 *  ArrayBlockingQueue ：一个由数组支持的有界队列。
		 *  LinkedBlockingQueue ：一个由链接节点支持的可选有界队列。
		 *  PriorityBlockingQueue ：一个由优先级堆支持的无界优先级队列。
		 *  DelayQueue ：一个由优先级堆支持的、基于时间的调度队列。
		 *  SynchronousQueue ：一个利用 BlockingQueue 接口的简单聚集（rendezvous）机制。
		 */

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
