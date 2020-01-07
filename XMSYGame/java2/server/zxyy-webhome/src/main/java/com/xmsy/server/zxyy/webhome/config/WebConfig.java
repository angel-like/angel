package com.xmsy.server.zxyy.webhome.config;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.xmsy.server.zxyy.webhome.filter.IpInterceptor;
import com.xmsy.server.zxyy.webhome.filter.TokenInterceptor;
import com.xmsy.server.zxyy.webhome.filter.UserInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * WebMvc配置
 *
 * @author aleng
 */

@Configuration
public class WebConfig implements WebMvcConfigurer {

	@Autowired
	private TokenInterceptor tokenInterceptor;
	@Autowired
	private IpInterceptor ipInterceptor;
	@Autowired
	private UserInterceptor userInterceptor;
	@Value("${spring.cache.ehcache.config}")
	private String ehcacheXml;

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
		registry.addInterceptor(tokenInterceptor) // token拦截器类
				.addPathPatterns("/webim/**").addPathPatterns("/webhome/**").addPathPatterns("/V1.0/**")
				.excludePathPatterns("/V1.0/App/Login/**")// 用户注册登陆时不拦截
				.excludePathPatterns("/V1.0/App/appCustomerService")// 用户客服不拦截
				.excludePathPatterns("/V1.0/App/appCustomerInfo")// 用户客服分享发图片等信息不拦截
				.excludePathPatterns("/V1.0/App/configure/getPromotionsTypeList")// 活动类型不拦截
				.excludePathPatterns("/V1.0/App/configure/getPromotionsListForTypeId")// 活动列表不拦截
				.excludePathPatterns("/webhome/public/**")// 网页设置
				.excludePathPatterns("/webhome/user/**")// 用户登录注册时不验证token
				.excludePathPatterns("/webim/v1/logon/**")// 用户登录注册时不验证token》》》33
				//.excludePathPatterns("/webim/v1/sina/conversion-link")// 长链接转短链接不验证
				.excludePathPatterns("/webim/v1/home/**")// 网页设置》》》33
				.excludePathPatterns("/V1.0/pay/**");
		registry.addInterceptor(ipInterceptor) // ip黑名单拦截器类
				.addPathPatterns("/webim/**").addPathPatterns("/V1.0/**").addPathPatterns("/webhome/**")
				.excludePathPatterns("/webhome/public/**").excludePathPatterns("/webim/v1/home/**")// 网页设置》》》33
				.excludePathPatterns("/V1.0/pay/**");
		registry.addInterceptor(userInterceptor) // 黑名单用户拦截器类
				.addPathPatterns("/webim/**").addPathPatterns("/webhome/**").addPathPatterns("/V1.0/**")
				.excludePathPatterns("/V1.0/App/**").excludePathPatterns("/webhome/user/**")// 用户注册登陆时不拦截
				.excludePathPatterns("/webim/v1/logon/**")// 用户登录注册时不验证token》》》33
				.excludePathPatterns("/webim/v1/home/**")// 网页设置》》》33
				.excludePathPatterns("/webhome/public/**").excludePathPatterns("/V1.0/pay/**");
		WebMvcConfigurer.super.addInterceptors(registry);
	}

}
