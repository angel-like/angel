package com.xmsy.server.zxyy.robot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

	// @Override
	// public void addInterceptors(InterceptorRegistry registry) {
	// // TODO Auto-generated method stub
	// WebMvcConfigurer.super.addInterceptors(registry);
	// }
	//
	// @Override
	// public void addCorsMappings(CorsRegistry registry) {
	// registry.addMapping("/**")
	// .allowedOrigins("*")
	// .allowCredentials(true)
	// .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
	// .allowedHeaders("Content-Type")
	// .maxAge(3600);
	// }

	private CorsConfiguration buildConfig() {
		CorsConfiguration corsConfiguration = new CorsConfiguration();
		corsConfiguration.addAllowedOrigin("*"); // 1允许任何域名使用
		corsConfiguration.addAllowedHeader("*"); // 2允许任何头
		corsConfiguration.addAllowedMethod("*"); // 3允许任何方法（post、get等）
		corsConfiguration.setAllowCredentials(true);
		return corsConfiguration;
	}

	@Bean
	public CorsFilter corsFilter() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", buildConfig()); // 4
		return new CorsFilter(source);
	}
}