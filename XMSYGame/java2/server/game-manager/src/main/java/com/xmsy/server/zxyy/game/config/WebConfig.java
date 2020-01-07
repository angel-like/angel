package com.xmsy.server.zxyy.game.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.xmsy.server.zxyy.game.filter.TokenInterceptor;

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

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(tokenInterceptor) // 指定拦截器类
				.addPathPatterns("/web/**");
		WebMvcConfigurer.super.addInterceptors(registry);
	}

}
