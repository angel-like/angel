package com.xmsy.server.zxyy.schedule.config;

import javax.sql.DataSource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;

/**
 * 
 * .数据源配置
 *
 * @author aleng
 * @date 2018年1月12日 上午12:56:34
 * @Description: TODO
 */
@Configuration
public class DataSourceConfig {

	@ConfigurationProperties(prefix = "spring.datasource-webhome")
	@Primary
	@Bean(name = "webhome", initMethod = "init", destroyMethod = "close")
	public DataSource getDataSourceMaster() {
		return new DruidDataSource();
	}

	@ConfigurationProperties(prefix = "spring.datasource-robot")
	@Bean(name = "robot", initMethod = "init", destroyMethod = "close")
	public DataSource getDataSourceSlave() {
		return new DruidDataSource();
	}

	@Bean
	public ServletRegistrationBean<StatViewServlet> druidStatViewServlet() {
		ServletRegistrationBean<StatViewServlet> registrationBean = new ServletRegistrationBean<>(new StatViewServlet(), "/druid/*");
		registrationBean.addInitParameter("allow", "127.0.0.1");// IP白名单
																// (没有配置或者为空，则允许所有访问)
		registrationBean.addInitParameter("deny", "");// IP黑名单
														// (存在共同时，deny优先于allow)
		registrationBean.addInitParameter("loginUsername", "admin");
		registrationBean.addInitParameter("loginPassword", "123!");
		registrationBean.addInitParameter("resetEnable", "true");
		return registrationBean;
	}

	@Bean
	public FilterRegistrationBean<WebStatFilter> druidWebStatViewFilter() {
		FilterRegistrationBean<WebStatFilter> registrationBean = new FilterRegistrationBean<>(new WebStatFilter());
		registrationBean.addInitParameter("urlPatterns", "/*");
		registrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.bmp,*.png,*.css,*.ico,/druid/*");
		return registrationBean;
	}

}
