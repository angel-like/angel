package com.xmsy.server.zxyy.robot.config;

import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.task.TaskExecutor;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.xmsy.server.zxyy.robot.filter.TokenInterceptor;

/**
 * WebMvc配置
 *
 * @author aleng
 */

@Configuration
public class WebConfig implements WebMvcConfigurer {

	@Autowired
	private TokenInterceptor tokenInterceptor;

	@Override
	public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
		FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();
		FastJsonConfig fastJsonConfig = new FastJsonConfig();
		SerializerFeature features[] = new SerializerFeature[] { SerializerFeature.PrettyFormat };
		fastJsonConfig.setSerializerFeatures(features);
		String dateFormat = "yyyy-MM-dd HH:mm:ss";
		fastJsonConfig.setDateFormat(dateFormat);
		fastConverter.setFastJsonConfig(fastJsonConfig);
		HttpMessageConverter<?> converter = fastConverter;
		converters.add(0, converter);
	}

	@Bean(name = "ehCacheManager")
	public EhCacheCacheManager ehCacheCacheManager(EhCacheManagerFactoryBean bean) {
		return new EhCacheCacheManager(bean.getObject());
	}

	/*
	 * 据shared与否的设置,Spring分别通过CacheManager.create()或new
	 * CacheManager()方式来创建一个ehcache基地.
	 */
	@Bean
	public EhCacheManagerFactoryBean ehCacheManagerFactoryBean() {
		EhCacheManagerFactoryBean cacheManagerFactoryBean = new EhCacheManagerFactoryBean();
		cacheManagerFactoryBean.setConfigLocation(new ClassPathResource("ehcache.xml"));
		cacheManagerFactoryBean.setShared(true);
		return cacheManagerFactoryBean;
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(tokenInterceptor) // token拦截器类
				.addPathPatterns("/V1.0/**").excludePathPatterns("/V1.0/robot/EnterGame");
		WebMvcConfigurer.super.addInterceptors(registry);
	}

	@Bean(name = "sysTaskExecutor")
	public TaskExecutor taskExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		// 设置核心线程数
		executor.setCorePoolSize(3);
		// 设置最大线程数
		executor.setMaxPoolSize(3);
		// 设置队列容量
		executor.setQueueCapacity(Integer.MAX_VALUE);
		// 设置线程活跃时间（秒）
		executor.setKeepAliveSeconds(60);
		// 设置默认线程名称
		executor.setThreadNamePrefix("modifyRobot-");
		// 设置拒绝策略
		executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
		// 等待所有任务结束后再关闭线程池
		executor.setWaitForTasksToCompleteOnShutdown(true);
		return executor;
	}

}
