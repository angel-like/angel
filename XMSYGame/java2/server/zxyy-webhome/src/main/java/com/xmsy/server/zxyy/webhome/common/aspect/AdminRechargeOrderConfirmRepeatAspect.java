package com.xmsy.server.zxyy.webhome.common.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xmsy.common.define.exception.OrderRepeatException;
import com.xmsy.network.redis.utils.RedisLockUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * 人工充值订单重复确认
 *
 * @author adu
 * @email xxxxxx
 * @date 2019-03-22 16:30
 */
@Slf4j
@Aspect
@Configuration
public class AdminRechargeOrderConfirmRepeatAspect {

	public static final String OPERATE_NAME = "AdminRechargeOrderConfirm";

	@Autowired
	private RedisLockUtil redisLockUtil;

	@Pointcut("@annotation(com.xmsy.server.zxyy.webhome.common.annotation.AdminRechargeOrderConfirmRepeat)")
	public void orderRepeatPointCut() {

	}

	@Around("orderRepeatPointCut()")
	public Object around(ProceedingJoinPoint point) throws Throwable {
		Object[] args = point.getArgs();
		JSONObject param= (JSONObject) JSON.toJSON(args[0]);
		Long id = param.getLong("id");
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(OPERATE_NAME);
		stringBuilder.append(id);
		
		String lockKey = stringBuilder.toString();
		log.info("AdminRechargeOrderConfirmRepeatAspect lockKey{}",lockKey);
		Object result = null;
		if (redisLockUtil.setLock(lockKey, lockKey)) {
			log.info("AdminRechargeOrderConfirmRepeatAspect setLock true");
			try {
				result = point.proceed();
				boolean isReleaseLock = redisLockUtil.releaseLock(lockKey, lockKey);
				log.info("AdminRechargeOrderConfirmRepeatAspect releaseLock {}",isReleaseLock);
			} catch (Exception e) {
				log.error("[RechargeOrderConfirmRepeatAspect] 存款订单处理中，防止重复确认 arg[] {} ", point.getArgs());
				redisLockUtil.releaseLock(lockKey, lockKey);
				throw e;
			}
		} else {
			log.info("AdminRechargeOrderConfirmRepeatAspect setLock false");
			throw new OrderRepeatException();
		}
		return result;
	}
}
