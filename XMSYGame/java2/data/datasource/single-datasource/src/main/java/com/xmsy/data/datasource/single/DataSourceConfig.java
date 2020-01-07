package com.xmsy.data.datasource.single;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;

/**
 * .数据源配置
 * 
 * @since 2017-6-17
 * @author chenjisi
 */
@Configuration
public class DataSourceConfig {

	private Logger logger = LoggerFactory.getLogger(DataSourceConfig.class);

	@Value("${spring.datasource.url}")
	private String dbUrl;

	@Value("${spring.datasource.username}")
	private String username;

	@Value("${spring.datasource.password}")
	private String password;

	@Value("${spring.datasource.driverClassName}")
	private String driverClassName;

	@Value("${spring.datasource.initialSize}")
	private int initialSize;

	@Value("${spring.datasource.minIdle}")
	private int minIdle;

	@Value("${spring.datasource.maxActive}")
	private int maxActive;

	@Value("${spring.datasource.maxWait}")
	private int maxWait;

	@Value("${spring.datasource.timeBetweenEvictionRunsMillis}")
	private int timeBetweenEvictionRunsMillis;

	@Value("${spring.datasource.minEvictableIdleTimeMillis}")
	private int minEvictableIdleTimeMillis;

	@Value("${spring.datasource.validationQuery}")
	private String validationQuery;

	@Value("${spring.datasource.testWhileIdle}")
	private boolean testWhileIdle;

	@Value("${spring.datasource.testOnBorrow}")
	private boolean testOnBorrow;

	@Value("${spring.datasource.testOnReturn}")
	private boolean testOnReturn;

	@Value("${spring.datasource.poolPreparedStatements}")
	private boolean poolPreparedStatements;

	@Value("${spring.datasource.maxPoolPreparedStatementPerConnectionSize}")
	private int maxPoolPreparedStatementPerConnectionSize;

	@Value("${spring.datasource.filters}")
	private String filters;

	@Value("${spring.datasource.connectionProperties}")
	private String connectionProperties;

	@Bean("dataSource") // 声明其为Bean实例
	@Primary // 在同样的DataSource中，首先使用被标注的DataSource
	public DataSource dataSource() {
		DruidDataSource datasource = new DruidDataSource();
		datasource.setUrl(this.dbUrl);
		datasource.setUsername(username);
		datasource.setPassword(password);
		datasource.setDriverClassName(driverClassName);
		// configuration
		datasource.setInitialSize(initialSize);
		datasource.setMinIdle(minIdle);
		datasource.setMaxActive(maxActive);
		datasource.setMaxWait(maxWait);
		datasource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
		datasource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
		datasource.setValidationQuery(validationQuery);
		datasource.setTestWhileIdle(testWhileIdle);
		datasource.setTestOnBorrow(testOnBorrow);
		datasource.setTestOnReturn(testOnReturn);
		datasource.setPoolPreparedStatements(poolPreparedStatements);
		datasource.setMaxPoolPreparedStatementPerConnectionSize(maxPoolPreparedStatementPerConnectionSize);
		try {
			datasource.setFilters(filters);
		} catch (SQLException e) {
			logger.error("druid configuration initialization filter", e);
		}
		datasource.setConnectionProperties(connectionProperties);
		return datasource;
	}

	@Bean
	public ServletRegistrationBean<StatViewServlet> druidStatViewServlet() {
		ServletRegistrationBean<StatViewServlet> registrationBean = new ServletRegistrationBean<StatViewServlet>(new StatViewServlet(), "/druid/*");
		registrationBean.addInitParameter("allow", "127.0.0.1");// IP白名单
																// (没有配置或者为空，则允许所有访问)
		registrationBean.addInitParameter("deny", "");// IP黑名单
														// (存在共同时，deny优先于allow)
		registrationBean.addInitParameter("loginUsername", "admin");
		registrationBean.addInitParameter("loginPassword", "123!");
		registrationBean.addInitParameter("resetEnable", "false");
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
