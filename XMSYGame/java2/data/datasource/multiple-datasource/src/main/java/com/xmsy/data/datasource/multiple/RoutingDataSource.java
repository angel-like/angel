package com.xmsy.data.datasource.multiple;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * 
 * .数据源切换原理
 *
 * @author chenjisi
 * @date 2018年1月12日 上午12:55:59 
 * @Description: TODO
 */
public class RoutingDataSource extends AbstractRoutingDataSource {
	@Override
	protected Object determineCurrentLookupKey() {
		return DbContextHolder.getDbType();
	}
}
