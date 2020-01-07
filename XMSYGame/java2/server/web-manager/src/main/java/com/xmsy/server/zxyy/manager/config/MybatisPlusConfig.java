package com.xmsy.server.zxyy.manager.config;

import javax.sql.DataSource;

import org.apache.ibatis.plugin.Interceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.baomidou.mybatisplus.MybatisConfiguration;
import com.baomidou.mybatisplus.MybatisXMLLanguageDriver;
import com.baomidou.mybatisplus.entity.GlobalConfiguration;
import com.baomidou.mybatisplus.mapper.LogicSqlInjector;
import com.baomidou.mybatisplus.plugins.OptimisticLockerInterceptor;
import com.baomidou.mybatisplus.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.spring.MybatisSqlSessionFactoryBean;
import com.baomidou.mybatisplus.spring.boot.starter.MybatisPlusProperties;
import com.baomidou.mybatisplus.spring.boot.starter.SpringBootVFS;

@Configuration
@DependsOn("dataSource")
@EnableTransactionManagement
@MapperScan("com.xmsy.server.**.dao*")
public class MybatisPlusConfig {

	@Autowired
	private MybatisPlusProperties properties;
	@Value("${mybatis.dbType}")
	private String dbType;

	@Bean
	public MybatisSqlSessionFactoryBean mybatisSqlSessionFactoryBean(@Qualifier("dataSource") DataSource dataSource) {
		MybatisSqlSessionFactoryBean mybatisPlus = new MybatisSqlSessionFactoryBean();
		mybatisPlus.setDataSource(dataSource);
		mybatisPlus.setVfs(SpringBootVFS.class);
		mybatisPlus.setConfiguration(properties.getConfiguration());
		mybatisPlus.setPlugins(getInterceptors());
		// MP 全局配置，更多内容进入类看注释
		GlobalConfiguration globalConfig = new GlobalConfiguration(new LogicSqlInjector());
		// Assert.notNull(dbType, "dbType is null");
		globalConfig.setDbType(dbType);
		// ID 策略 AUTO->`0`("数据库ID自增") INPUT->`1`(用户输入ID")
		// ID_WORKER->`2`("全局唯一ID") UUID->`3`("全局唯一ID")
		globalConfig.setIdType(this.properties.getGlobalConfig().getIdType());
		// 数据库大写下划线转换
		globalConfig.setCapitalMode(this.properties.getGlobalConfig().getCapitalMode());
		// 自定义填充策略接口实现
		globalConfig.setMetaObjectHandler(new MetaObjectConfig());
		// 字段策略 0:"忽略判断",1:"非 NULL 判断"),2:"非空判断"
		globalConfig.setFieldStrategy(this.properties.getGlobalConfig().getFieldStrategy());
		// 驼峰下划线转换
		globalConfig.setDbColumnUnderline(this.properties.getGlobalConfig().getDbColumnUnderline());
		// 刷新mapper 调试神器
		globalConfig.setRefresh(this.properties.getGlobalConfig().getRefreshMapper());
		//逻辑删除配置
		globalConfig.setLogicDeleteValue(this.properties.getGlobalConfig().getLogicDeleteValue());
		globalConfig.setLogicNotDeleteValue(this.properties.getGlobalConfig().getLogicNotDeleteValue());
		mybatisPlus.setGlobalConfig(globalConfig);
		MybatisConfiguration mc = new MybatisConfiguration();
		mc.setDefaultScriptingLanguage(MybatisXMLLanguageDriver.class);
		mc.setMapUnderscoreToCamelCase(true);
		mc.setCacheEnabled(false);
		mybatisPlus.setConfiguration(mc);
		if (StringUtils.hasLength(this.properties.getTypeAliasesPackage())) {
			mybatisPlus.setTypeAliasesPackage(this.properties.getTypeAliasesPackage());
		}
		if (StringUtils.hasLength(this.properties.getTypeHandlersPackage())) {
			mybatisPlus.setTypeHandlersPackage(this.properties.getTypeHandlersPackage());
		}
		if (!ObjectUtils.isEmpty(this.properties.resolveMapperLocations())) {
			mybatisPlus.setMapperLocations(this.properties.resolveMapperLocations());
		}
		return mybatisPlus;
	}

	private Interceptor[] getInterceptors() {
		PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
		Assert.notNull(dbType, "dbType is null");
		paginationInterceptor.setDialectType(dbType);
		OptimisticLockerInterceptor optimisticLockerInterceptor = new OptimisticLockerInterceptor();
		Interceptor[] interceptors = new Interceptor[] { paginationInterceptor, optimisticLockerInterceptor };
		return interceptors;
	}
}
