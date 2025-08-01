package com.itheima.springboot.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * 生产上线程池配置
 */
@Data
@Configuration
public class ThreadPoolConfig {

    @Value("${thread-pool.core-pool-size}")
    private int corePoolSize;
    @Value("${thread-pool.max-pool-size}")
    private int maxPoolSize;
    @Value("${thread-pool.queue-capacity}")
    private int queueCapacity;
    @Value("${thread-pool.keep-alive-seconds}")
    private int keepAliveSeconds;
    @Value("${thread-pool.thread-name-prefix}")
    private String threadNamePrefix;

    /**
     * 构建spring提供的线程池对象，通过@Bean注入ioc容器中
     * @return
     */
    @Bean
    public ThreadPoolTaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        //核心线程数
        executor.setCorePoolSize(corePoolSize);
        //最大线程数
        executor.setMaxPoolSize(maxPoolSize);
        //空闲时间 ，ThreadPoolTaskExecutor默认单位：秒
        executor.setKeepAliveSeconds(keepAliveSeconds);
        //缓冲队列个数，ThreadPoolTaskExecutor数值>0默认队列LinkedBlockingQueue
        //IO密集型的，较大的队列，以提高线程利用率和吞吐量; CPU密集型的，较小的队列，以避免CPU空闲或者上下文切换过多。
        executor.setQueueCapacity(queueCapacity);
        //抛弃策略： 重试
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        //线程池名称前缀(方便查找打印的日志)
        executor.setThreadNamePrefix(threadNamePrefix);
        // 默认false：当线程池关闭时，会立即中断所有正在执行的任务，并清空任务队列，导致未完成的任务被丢弃
        //true: 线程池在关闭时会等待所有已提交的任务完成。
        executor.setWaitForTasksToCompleteOnShutdown(true);
        //线程池等待任务完成的最大时间。时间内所有任务都完成了，线程池会正常关闭；如果超时，线程池会强制关闭，未完成的任务将被丢弃
        executor.setAwaitTerminationSeconds(60);
        //执行一些初始化，这里不需要，因为注入容器时会自动执行一次该初始化方法
        //executor.initialize();
        return executor;
    }

}