package com.xmsy.server.zxyy.manager.common.aspect;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import com.xmsy.common.define.exception.OrderRepeatException;
import com.xmsy.network.redis.utils.RedisLockUtil;
import com.xmsy.server.zxyy.manager.utils.JwtUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * 充值订单重复提交
 *
 * @author aleng
 * @email xxxxxx
 * @date 2017-07-17 23:30
 */
@Slf4j
@Aspect
@Configuration
public class RechargeOrderRepeatAspect {

	@Autowired
	private RedisLockUtil redisLockUtil;

	public static final String OPERATE_NAME = "RechargeOrder";

	@Pointcut("@annotation(com.xmsy.server.zxyy.manager.common.annotation.RechargeOrderRepeat)")
	public void orderRepeatPointCut() {

	}

	@Around("orderRepeatPointCut()")
	public Object around(ProceedingJoinPoint point) throws Throwable {
		Object[] args = point.getArgs();
		HttpServletRequest httpServletRequest = (HttpServletRequest) args[1];
		String token = httpServletRequest.getHeader("token");
		String userId = JwtUtil.getUserId(token);// 通过token获取人员ID
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
				log.error("[RechargeOrderRepeatAspect] 银行存款订单处理中，防止重复提交 arg[] {} ", point.getArgs());
				redisLockUtil.releaseLock(lockKey, lockKey);
				throw e;
			}
		} else {
			throw new OrderRepeatException();
		}
		return result;
	}
}
