package com.xmsy.data.datasource.multiple;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import com.xmsy.data.datasource.multiple.DbContextHolder.DbType;

/**
 * 
 * .对复杂读操作切换到从库
 *
 * @author chenjisi
 * @date 2018年1月12日 上午1:34:30
 * @Description: TODO
 */
@Aspect
@Component
public class DataSourceAop implements Ordered {

	@Pointcut("execution(* com.xmsy.server.*.service..get*(..)) || execution(* com.xmsy.server.*.service..find*(..)) || execution(* com.xmsy.server.*.service..select*(..)) || execution(* com.xmsy.server.*.service..query*(..)) || execution(* com.xmsy.server.*.service..fetch*(..))")
	public void anyReadMethod() {
	}

	@Pointcut("@annotation(com.xmsy.data.datasource.multiple.ReadOnly)")
	public void readOnly() {
	}

	@Around(value = "anyReadMethod()")
	public Object proceedAnyReadMethod(ProceedingJoinPoint pjp) throws Throwable {
		return switchToDataSourceSlave(pjp);
	}

	@Around(value = "readOnly()")
	public Object proceedReadOnly(ProceedingJoinPoint pjp) throws Throwable {
		return switchToDataSourceSlave(pjp);
	}

	/**
	 * 
	 * .切换到从库
	 * 
	 * @param pjp
	 * @return
	 * @throws Throwable
	 * @date 2018年1月12日 上午10:38:46
	 * @Description: TODO
	 *
	 */
	private Object switchToDataSourceSlave(ProceedingJoinPoint pjp) throws Throwable {
		try {
			DbContextHolder.setDbType(DbType.SLAVE);
			Object result = pjp.proceed();
			DbContextHolder.clearDbType();
			return result;
		} finally {
			DbContextHolder.clearDbType();
		}
	}

	@Override
	public int getOrder() {
		return 1;
	}

}
