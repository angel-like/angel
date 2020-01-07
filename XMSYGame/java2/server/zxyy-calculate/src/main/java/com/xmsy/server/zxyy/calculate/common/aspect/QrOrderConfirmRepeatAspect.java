package com.xmsy.server.zxyy.calculate.common.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import com.xmsy.common.bean.message.CallbackMessage;
import com.xmsy.network.redis.utils.RedisLockUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * 充值订单重复确认
 *
 * @author aleng
 * @email xxxxxx
 * @date 2017-07-17 23:30
 */
@Slf4j
@Aspect
@Configuration
public class QrOrderConfirmRepeatAspect {

	public static final String OPERATE_NAME = "QrOrderConfirm";

	@Autowired
	private RedisLockUtil redisLockUtil;

	@Pointcut("@annotation(com.xmsy.server.zxyy.calculate.common.annotation.QrOrderConfirmRepeat)")
	public void orderRepeatPointCut() {

	}

	@Around("orderRepeatPointCut()")
	public Object around(ProceedingJoinPoint point) throws Throwable {
		Object[] args = point.getArgs();
		CallbackMessage entity = (CallbackMessage) args[0];
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(OPERATE_NAME);
		stringBuilder.append(entity.getOrderNo());
		String lockKey = stringBuilder.toString();
		Object result = null;
		if (redisLockUtil.setLock(lockKey, lockKey)) {
			try {
				result = point.proceed();
				redisLockUtil.releaseLock(lockKey, lockKey);
			} catch (Exception e) {
				log.info("[QrOrderConfirmRepeatAspect] 存款订单处理中，多次回调，防止重复确认 arg[] {} ", point.getArgs());
				redisLockUtil.releaseLock(lockKey, lockKey);
				throw e;
			}
		} else {
			log.info("[QrOrderConfirmRepeatAspect] 存款订单处理中，多次回调 ,防止重复确认 arg[] {} ", point.getArgs());
			Thread.sleep(500L);
		}
		return result;
	}
}
