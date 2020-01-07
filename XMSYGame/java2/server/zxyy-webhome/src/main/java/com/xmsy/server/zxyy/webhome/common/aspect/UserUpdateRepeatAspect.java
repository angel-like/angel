package com.xmsy.server.zxyy.webhome.common.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import com.xmsy.common.define.exception.OrderRepeatException;
import com.xmsy.network.redis.utils.RedisLockUtil;
import com.xmsy.server.zxyy.webhome.utils.JwtUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * 用户信息重复更新
 *
 * @author aleng
 * @email xxxxxx
 * @date 2017-07-17 23:30
 */
@Slf4j
@Aspect
@Configuration
public class UserUpdateRepeatAspect {

	@Autowired
	private RedisLockUtil redisLockUtil;

	public static final String OPERATE_NAME = "userUpdate";

	@Pointcut("@annotation(com.xmsy.server.zxyy.webhome.common.annotation.UserUpdateRepeat)")
	public void userUpdateRepeatPointCut() {

	}

	@Around("userUpdateRepeatPointCut()")
	public Object around(ProceedingJoinPoint point) throws Throwable {
		Object[] args = point.getArgs();
		String token = String.valueOf(args[0]);
		Long userId = Long.valueOf(JwtUtil.getUserId(token));
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(OPERATE_NAME);
		stringBuilder.append(userId);
		String lockKey = stringBuilder.toString();
		Object result = null;
		if (redisLockUtil.setLock(lockKey, lockKey)) {
			try {
				result = point.proceed();
				redisLockUtil.releaseLock(lockKey, lockKey);
			} catch (Exception e) {
				log.error("[UserUpdateRepeatAspect] 用户重复修改用户信息防止重复提交 arg[] {} ", point.getArgs());
				redisLockUtil.releaseLock(lockKey, lockKey);
				throw e;
			}
		} else {
			throw new OrderRepeatException();
		}
		return result;
	}
}
