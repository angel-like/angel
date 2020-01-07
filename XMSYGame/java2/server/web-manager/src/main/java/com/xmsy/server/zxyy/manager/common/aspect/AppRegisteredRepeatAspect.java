package com.xmsy.server.zxyy.manager.common.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import com.xmsy.common.define.exception.OrderRepeatException;
import com.xmsy.network.redis.utils.RedisLockUtil;
import com.xmsy.server.zxyy.manager.modules.app.login.param.OAuth2Params;
import com.xmsy.server.zxyy.manager.modules.app.login.param.PhoneRegisterParams;
import com.xmsy.server.zxyy.manager.modules.app.login.param.RegisterParams;

import lombok.extern.slf4j.Slf4j;

/**
 * 重复注册
 *
 * @author aleng
 * @email xxxxxx
 * @date 2017-07-17 23:30
 */
@Slf4j
@Aspect
@Configuration
public class AppRegisteredRepeatAspect {

	@Autowired
	private RedisLockUtil redisLockUtil;

	@Pointcut("@annotation(com.xmsy.server.zxyy.manager.common.annotation.AppRegisteredRepeat)")
	public void registeredRepeatPointCut() {

	}

	@Around("registeredRepeatPointCut()")
	public Object around(ProceedingJoinPoint point) throws Throwable {
		Object[] args = point.getArgs();
		StringBuilder stringBuilder = new StringBuilder();
		if (args[0] instanceof RegisterParams) {
			RegisterParams param = (RegisterParams) args[0];
			stringBuilder.append(param.getAccount());
			stringBuilder.append(param.getLoginPassWord());
		} else if (args[0] instanceof PhoneRegisterParams) {
			PhoneRegisterParams param = (PhoneRegisterParams) args[0];
			stringBuilder.append(param.getAccount());
			stringBuilder.append(param.getLoginPassWord());
		} else if (args[0] instanceof OAuth2Params) {
			OAuth2Params param = (OAuth2Params) args[0];
			stringBuilder.append(param.getCode());
			stringBuilder.append(param.getDeviceCode());
		}
		String lockKey = stringBuilder.toString();
		Object result = null;
		if (redisLockUtil.setLock(lockKey, lockKey)) {
			try {
				result = point.proceed();
				redisLockUtil.releaseLock(lockKey, lockKey);
			} catch (Exception e) {
				log.error("[AppRegisteredRepeatAspect] app注册防止重复注册 arg[] {} ", point.getArgs());
				redisLockUtil.releaseLock(lockKey, lockKey);
				throw e;
			}
		} else {
			throw new OrderRepeatException("注册中，请勿重复点击");
		}
		return result;
	}
}
