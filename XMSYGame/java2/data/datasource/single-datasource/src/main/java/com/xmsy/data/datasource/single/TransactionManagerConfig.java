package com.xmsy.data.datasource.single;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * .druid配置 
 * @since 2017-6-17
 * @author chenjisi
 */
@Configuration
public class TransactionManagerConfig {
	

	@Resource 
	private DataSource dataSource;
	
	@Bean
	public PlatformTransactionManager transactionManager() {
		return new DataSourceTransactionManager(dataSource);
	}
}
