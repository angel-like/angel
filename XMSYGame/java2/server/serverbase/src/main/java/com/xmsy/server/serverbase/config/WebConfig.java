package com.xmsy.server.serverbase.config;

import java.util.List;

import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.boot.context.ApplicationPidFileWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.xmsy.server.serverbase.result.GlobalJsonReturn;

/**
 * .web配置
 * 
 * @since 2017-6-17
 * @author chenjisi
 *
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

	@Override
	public void addReturnValueHandlers(List<HandlerMethodReturnValueHandler> returnValueHandlers) {
		// TODO Auto-generated method stub
		returnValueHandlers.add(jsonReturnValueHandler());
	}

	@Bean
	public GlobalJsonReturn jsonReturnValueHandler() {
		return new GlobalJsonReturn();
	}

	@Bean
	public HttpMessageConverters fastJsonHttpMessageConverters() {
		FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();
		FastJsonConfig fastJsonConfig = new FastJsonConfig();
		SerializerFeature features[] = new SerializerFeature[] { SerializerFeature.PrettyFormat };
		fastJsonConfig.setSerializerFeatures(features);
		String dateFormat = "yyyy-MM-dd HH:mm:ss";
		fastJsonConfig.setDateFormat(dateFormat);
		fastConverter.setFastJsonConfig(fastJsonConfig);
		HttpMessageConverter<?> converter = fastConverter;
		return new HttpMessageConverters(converter);
	}

	/**
	 * 写入进程ID到文件中
	 * 
	 * @return
	 */
	@Bean
	public ApplicationPidFileWriter initApplicationPidFileWriter() {
		return new ApplicationPidFileWriter();
	}

}