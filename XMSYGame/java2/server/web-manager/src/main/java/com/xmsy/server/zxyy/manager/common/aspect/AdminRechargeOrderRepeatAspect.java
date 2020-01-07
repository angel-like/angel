package com.xmsy.server.zxyy.manager.common.aspect;

import org.apache.shiro.SecurityUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import com.xmsy.common.define.exception.OrderRepeatException;
import com.xmsy.network.redis.utils.RedisLockUtil;
import com.xmsy.server.zxyy.manager.modules.manager.sys.entity.SysUserEntity;

import lombok.extern.slf4j.Slf4j;

/**
 * 人工充值订单重复提交
 *
 * @author adu
 * @email xxxxxx
 * @date 2019-03-22 16:30
 */
@Slf4j
@Aspect
@Configuration
public class AdminRechargeOrderRepeatAspect {

	@Autowired
	private RedisLockUtil redisLockUtil;

	public static final String OPERATE_NAME = "AdminRechargeOrder";

	@Pointcut("@annotation(com.xmsy.server.zxyy.manager.common.annotation.AdminRechargeOrderRepeat)")
	public void orderRepeatPointCut() {

	}

	@Around("orderRepeatPointCut()")
	public Object around(ProceedingJoinPoint point) throws Throwable {
		String userId = ((SysUserEntity) SecurityUtils.getSubject().getPrincipal()).getUsername();
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(OPERATE_NAME);
		stringBuilder.append(userId);
		String lockKey = stringBuilder.toString();
		Object result = null;
		log.info("AdminRechargeOrderRepeatAspect lockKey : {}", lockKey);
		if (redisLockUtil.setLock(lockKey, lockKey)) {
			log.info("AdminRechargeOrderRepeatAspect setLock : true");
			try {
				result = point.proceed();
				boolean isReleaseLock = redisLockUtil.releaseLock(lockKey, lockKey);
				log.info("AdminRechargeOrderRepeatAspect releaseLock : {}", isReleaseLock);
			} catch (Exception e) {
				log.error("[AdminRechargeOrderRepeatAspect] 人工充值订单处理中，防止重复提交 arg[] {} ", point.getArgs());
				redisLockUtil.releaseLock(lockKey, lockKey);
				throw e;
			}
		} else {
			log.info("AdminRechargeOrderRepeatAspect setLock : false");
			throw new OrderRepeatException();
		}
		return result;
	}
}
