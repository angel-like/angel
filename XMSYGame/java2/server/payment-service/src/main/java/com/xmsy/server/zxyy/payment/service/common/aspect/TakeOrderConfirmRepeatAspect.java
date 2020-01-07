package com.xmsy.server.zxyy.payment.service.common.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import com.xmsy.common.define.exception.OrderRepeatException;
import com.xmsy.network.redis.utils.RedisLockUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * 取款订单重复确认
 *
 * @author aleng
 * @email xxxxxx
 * @date 2017-07-17 23:30
 */
@Slf4j
@Aspect
@Configuration
public class TakeOrderConfirmRepeatAspect {

	@Autowired
	private RedisLockUtil redisLockUtil;

	public static final String OPERATE_NAME = "TakeOrderConfirm";

	@Pointcut("@annotation(com.xmsy.server.zxyy.payment.service.common.annotation.TakeOrderConfirmRepeat)")
	public void orderRepeatPointCut() {

	}

	@Around("orderRepeatPointCut()")
	public Object around(ProceedingJoinPoint point) throws Throwable {
		Object[] args = point.getArgs();
		Long id = (Long) args[0];
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(OPERATE_NAME);
		stringBuilder.append(id);
		String lockKey = stringBuilder.toString();
		Object result = null;
		if (redisLockUtil.setLock(lockKey, lockKey)) {
			try {
				result = point.proceed();
				redisLockUtil.releaseLock(lockKey, lockKey);
			} catch (Exception e) {
				log.error("[RechargeOrderRepeatAspect] 取款订单确认中，防止重复提交 arg[] {} ", point.getArgs());
				redisLockUtil.releaseLock(lockKey, lockKey);
				throw e;
			}
		} else {
			throw new OrderRepeatException();
		}
		return result;
	}
}
