package com.xmsy.data.datasource.multiple;

import javax.sql.DataSource;

import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;

/**
 * 
 * .动态事务
 *
 * @author chenjisi
 * @date 2018年1月12日 上午11:14:19
 * @Description: TODO
 */
public class DynamicDataSourceTransactionManager extends DataSourceTransactionManager {

	/**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = -5612166967059289673L;

	public DynamicDataSourceTransactionManager(DataSource dataSource) {
		super(dataSource);
	}

	/**
	 * 只读事务到读库，读写事务到写库
	 * 
	 * @param transaction
	 * @param definition
	 */
	@Override
	protected void doBegin(Object transaction, TransactionDefinition definition) {

		super.doBegin(transaction, definition);
	}

	/**
	 * 清理本地线程的数据源
	 * 
	 * @param transaction
	 */
	@Override
	protected void doCleanupAfterCompletion(Object transaction) {
		super.doCleanupAfterCompletion(transaction);
		DbContextHolder.clearDbType();
	}
}