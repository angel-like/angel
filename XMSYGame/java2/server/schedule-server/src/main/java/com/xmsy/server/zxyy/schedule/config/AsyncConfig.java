package com.xmsy.server.zxyy.schedule.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@ConfigurationProperties(prefix = "threadpool")
public class AsyncConfig {
	
	@Value("${async.executor.thread.max_pool_size}")
	private int maxPoolSize;

	@Value("${async.executor.thread.queue_capacity}")
	private int queueCapacity;
	@Value("${async.executor.thread.keep_alive_seconds}")
	private int keepAliveSeconds;
	
	@Value("${async.executor.thread.core_pool_size}")
	private int corePoolSize;

	@Bean(name = "threadPoolTaskExecutor")
	public ThreadPoolTaskExecutor threadPoolTaskExecutor() {
		ThreadPoolTaskExecutor pool = new ThreadPoolTaskExecutor();
		pool.setKeepAliveSeconds(keepAliveSeconds);// 空闲线程在等待工作时间超时。
		pool.setCorePoolSize(corePoolSize);// 核心线程池数
		pool.setMaxPoolSize(maxPoolSize); // 最大线程DataSourceConfig.java
		pool.setQueueCapacity(queueCapacity);// 队列容量
		// 队列满，线程被拒绝执行策略 默认值：AbortPolicy
		// pool.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
		// CallerRunsPolicy：线程调用运行该任务的 execute 本身。此策略提供简单的反馈控制机制，
		// 能够减缓新任务的提交速度。这个策略显然不想放弃执行任务。
		// 但是由于池中已经没有任何资源了，那么就直接使用调用该execute的线程本身来执行
		// AbortPolicy：处理程序遭到拒绝将抛出运行时 RejectedExecutionException这种策略直接抛出异常，丢弃任务。
		// DiscardPolicy：不能执行的任务将被删除，这种策略和AbortPolicy几乎一样，也是丢弃任务，只不过他不抛出异常
		// DiscardOldestPolicy：如果执行程序尚未关闭，则位于工作队列头部的任务将被删除，
		// 然后重试执行程序（如果再次失败，则重复此过程）该策略就稍微复杂一些，在pool没有关闭的前提下
		// 首先丢掉缓存在队列中的最早的任务，然后重新尝试运行该任务。这个策略需要适当小心。
		// 如果其他线程都还在运行，那么新来任务踢掉旧任务，缓存在queue中，再来一个任务又会踢掉queue中最老任务。
		return pool;
	}

}
