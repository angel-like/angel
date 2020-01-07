package com.xmsy.data.datasource.multiple;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;

/**
 * 
 * .数据源配置（主库读写，从库只读）
 *
 * @author chenjisi
 * @date 2018年1月12日 上午12:56:34
 * @Description: TODO
 */
@Configuration
public class DataSourceConfig {

	@ConfigurationProperties(prefix = "spring.datasourceMaster")
	@Primary
	@Bean(name = "master", initMethod = "init", destroyMethod = "close")
	public DataSource getDataSourceMaster() {
		return new DruidDataSource();
	}

	@ConfigurationProperties(prefix = "spring.datasourceSlave")
	@Bean(name = "slave", initMethod = "init", destroyMethod = "close")
	public DataSource getDataSourceSlave() {
		return new DruidDataSource();
	}

	@Bean("dataSource")
	public RoutingDataSource routingDataSource() {
		Map<Object, Object> targetDataSources = new HashMap<Object, Object>();
		targetDataSources.put(DbContextHolder.DbType.MASTER, getDataSourceMaster());
		targetDataSources.put(DbContextHolder.DbType.SLAVE, getDataSourceSlave());
		RoutingDataSource dataSource = new RoutingDataSource();
		dataSource.setTargetDataSources(targetDataSources);
		dataSource.setDefaultTargetDataSource(getDataSourceMaster());
		return dataSource;
	}

	/**
	 * 
	 * .主库读写，所以添加事务，从库只读不添加事务
	 * 
	 * @return
	 * @date 2018年1月12日 上午1:10:59
	 * @Description: TODO
	 *
	 */
	@Bean
	@Order(2)
	public PlatformTransactionManager transactionManager() {
		return new DataSourceTransactionManager(routingDataSource());

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
