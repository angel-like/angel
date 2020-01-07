package com.xmsy.server.zxyy.manager.common.aspect;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.google.common.collect.ImmutableList;
import com.xmsy.network.redis.utils.RedisLockUtil;
import com.xmsy.server.zxyy.manager.common.annotation.Limit;
import com.xmsy.server.zxyy.manager.common.utils.LimitType;

import lombok.extern.slf4j.Slf4j;

/**
 * 限流逻辑
 * 
 * @author Administrator
 *
 */
@Slf4j
@Aspect
@Configuration
public class LimitAspect {

	@Autowired
	private RedisLockUtil redisLockUtil;

	@Pointcut("@annotation(com.xmsy.server.zxyy.manager.common.annotation.Limit)")
	public void limitPointCut() {

	}

	@Around("limitPointCut()")
	public Object interceptor(ProceedingJoinPoint pjp) {
		MethodSignature signature = (MethodSignature) pjp.getSignature();
		Method method = signature.getMethod();
		Limit limitAnnotation = method.getAnnotation(Limit.class);
		LimitType limitType = limitAnnotation.limitType();
		String name = limitAnnotation.name();
		String key;
		int limitPeriod = limitAnnotation.period();
		int limitCount = limitAnnotation.count();
		switch (limitType) {
		case IP:
			key = getIpAddress();
			break;
		case CUSTOMER:
			key = limitAnnotation.key();
			break;
		default:
			key = StringUtils.upperCase(method.getName());
		}
		ImmutableList<String> keys = ImmutableList.of(StringUtils.join(limitAnnotation.prefix(), key));
		try {
			String luaScript = buildLuaScript();
			RedisScript<Number> redisScript = new DefaultRedisScript<>(luaScript, Number.class);
			Integer count = redisLockUtil.execute(redisScript, keys, limitCount, limitPeriod);
			log.info("Access try count is {} for name={} and key = {}", count, name, key);
			if (count != null && count.intValue() <= limitCount) {
				return pjp.proceed();
			} else {
				throw new RuntimeException("You have been dragged into the blacklist");
			}
		} catch (Throwable e) {
			if (e instanceof RuntimeException) {
				throw new RuntimeException(e.getLocalizedMessage());
			}
			throw new RuntimeException("server exception");
		}
	}

	/**
	 * 限流 脚本
	 *
	 * @return lua脚本
	 */
	public String buildLuaScript() {
		StringBuilder lua = new StringBuilder();
		lua.append("local c");
		lua.append("\nc = redis.call('get',KEYS[1])");
		// 调用不超过最大值，则直接返回
		lua.append("\nif c and tonumber(c) > tonumber(ARGV[1]) then");
		lua.append("\nreturn c;");
		lua.append("\nend");
		// 执行计算器自加
		lua.append("\nc = redis.call('incr',KEYS[1])");
		lua.append("\nif tonumber(c) == 1 then");
		// 从第一次调用开始限流，设置对应键值的过期
		lua.append("\nredis.call('expire',KEYS[1],ARGV[2])");
		lua.append("\nend");
		lua.append("\nreturn c;");
		return lua.toString();
	}

	private static final String UNKNOWN = "unknown";

	/**
	 * 获取IP地址
	 * 
	 * @return
	 */
	public String getIpAddress() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

}
