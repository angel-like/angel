package com.xmsy.server.zxyy.webhome.common.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import com.xmsy.common.define.exception.OrderRepeatException;
import com.xmsy.network.redis.utils.RedisLockUtil;
import com.xmsy.server.zxyy.webhome.modules.app.login.param.LoginParams;
import com.xmsy.server.zxyy.webhome.modules.web.login.entity.LoginEntity;

import lombok.extern.slf4j.Slf4j;

/**
 * 重复登入
 *
 * @author aleng
 * @email xxxxxx
 * @date 2017-07-17 23:30
 */
@Slf4j
@Aspect
@Configuration
public class LoginRepeatAspect {

	@Autowired
	private RedisLockUtil redisLockUtil;

	@Pointcut("@annotation(com.xmsy.server.zxyy.webhome.common.annotation.LoginRepeat)")
	public void loginRepeatPointCut() {

	}

	@Around("loginRepeatPointCut()")
	public Object around(ProceedingJoinPoint point) throws Throwable {
		StringBuilder stringBuilder = new StringBuilder();
		String lockKey = "";
		boolean lockResult = false;
		Object[] args = point.getArgs();
		if (args[0] instanceof LoginParams) {
			LoginParams param = (LoginParams) args[0];
			stringBuilder.append(param.getAccount());
			stringBuilder.append(param.getLoginPassWord());
			lockKey = stringBuilder.toString();
			lockResult = redisLockUtil.setLock(lockKey, lockKey);
			log.debug("[APP登入操作获取锁] lockKey {} lockResult {}", lockKey, lockResult);
		} else {
			LoginEntity param = (LoginEntity) args[0];
			stringBuilder.append(param.getAccount());
			stringBuilder.append(param.getLoginPassWord());
			lockKey = stringBuilder.toString();
			lockResult = redisLockUtil.setLock(lockKey, lockKey);
			log.debug("[web登入操作获取锁] lockKey {} lockResult {}", lockKey, lockResult);
		}
		Object result = null;
		if (lockResult) {
			try {
				result = point.proceed();
				redisLockUtil.releaseLock(lockKey, lockKey);
			} catch (Exception e) {
				log.error("[LoginRepeatAspect] 登入防止重复登入 arg[] {} ", point.getArgs());
				redisLockUtil.releaseLock(lockKey, lockKey);
				throw e;
			}
		} else {
			throw new OrderRepeatException("网络繁忙，请重试");
		}
		return result;
	}
}
