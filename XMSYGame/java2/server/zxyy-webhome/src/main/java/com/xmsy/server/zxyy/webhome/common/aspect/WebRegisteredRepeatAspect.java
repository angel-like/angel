package com.xmsy.server.zxyy.webhome.common.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import com.xmsy.common.define.exception.OrderRepeatException;
import com.xmsy.network.redis.utils.RedisLockUtil;
import com.xmsy.server.zxyy.webhome.modules.web.login.entity.PhoneRegisterEntity;
import com.xmsy.server.zxyy.webhome.modules.web.login.entity.RegisterEntity;

import lombok.extern.slf4j.Slf4j;

/**
 * 官网重复注册
 *
 * @author aleng
 * @email xxxxxx
 * @date 2017-07-17 23:30
 */
@Slf4j
@Aspect
@Configuration
public class WebRegisteredRepeatAspect {

	@Autowired
	private RedisLockUtil redisLockUtil;

	@Pointcut("@annotation(com.xmsy.server.zxyy.webhome.common.annotation.WebRegisteredRepeat)")
	public void registeredRepeatPointCut() {

	}

	@Around("registeredRepeatPointCut()")
	public Object around(ProceedingJoinPoint point) throws Throwable {
		Object[] args = point.getArgs();
		StringBuilder stringBuilder = new StringBuilder();
		if (args[0] instanceof RegisterEntity) {
			RegisterEntity entity = (RegisterEntity) args[0];
			stringBuilder.append(entity.getAccount());
			stringBuilder.append(entity.getLoginPassWord());
		} else if (args[0] instanceof PhoneRegisterEntity) {
			PhoneRegisterEntity entity = (PhoneRegisterEntity) args[0];
			stringBuilder.append(entity.getAccount());
			stringBuilder.append(entity.getLoginPassWord());
		}
		String lockKey = stringBuilder.toString();
		Object result = null;
		if (redisLockUtil.setLock(lockKey, lockKey)) {
			try {
				result = point.proceed();
				redisLockUtil.releaseLock(lockKey, lockKey);
			} catch (Exception e) {
				log.error("[WebRegisteredRepeatAspect] web注册防止重复注册 arg[] {} ", point.getArgs());
				redisLockUtil.releaseLock(lockKey, lockKey);
				throw e;
			}
		} else {
			throw new OrderRepeatException("网络繁忙，请重试");
		}
		return result;
	}
}
