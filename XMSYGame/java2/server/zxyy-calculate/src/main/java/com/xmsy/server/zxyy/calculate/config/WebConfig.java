package com.xmsy.server.zxyy.calculate.config;

import java.util.List;

import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;

/**
 * WebMvc配置
 *
 * @author aleng
 */

@Configuration
public class WebConfig implements WebMvcConfigurer {
	

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

}
