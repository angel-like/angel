package com.xmsy.data.datasource.multiple;

/**
 * 
 * .数据源类型定义（主从）
 *
 * @author chenjisi
 * @date 2018年1月12日 上午12:49:07 
 * @Description: TODO
 */
public class DbContextHolder {
	
	public enum DbType {
        MASTER,
        SLAVE
    }

    private static final ThreadLocal<DbType> contextHolder = new ThreadLocal<>();

    public static void setDbType(DbType dbType) {
        contextHolder.set(dbType);
    }

    public static DbType getDbType() {
        return contextHolder.get() == null ? DbType.MASTER : contextHolder.get();
    }

    public static void clearDbType() {
        contextHolder.remove();
    }
}
