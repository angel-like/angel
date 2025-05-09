package com.xmsy.server.zxyy.manager.common.aspect;

import java.lang.reflect.Method;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.druid.util.StringUtils;
import com.google.gson.Gson;
import com.xmsy.server.zxyy.manager.common.annotation.SysLog;
import com.xmsy.server.zxyy.manager.common.utils.HttpContextUtils;
import com.xmsy.server.zxyy.manager.modules.manager.sys.entity.SysLogEntity;
import com.xmsy.server.zxyy.manager.modules.manager.sys.entity.SysUserEntity;
import com.xmsy.server.zxyy.manager.modules.manager.sys.form.SysLoginForm;
import com.xmsy.server.zxyy.manager.modules.manager.sys.service.SysLogService;
import com.xmsy.server.zxyy.manager.utils.IpUtil;

/**
 * 系统日志，切面处理类
 * 
 * @author aleng
 * @email xxxxxx
 * @date 2017年3月8日 上午11:07:35
 */
@Aspect
@Component
public class SysLogAspect {
	@Autowired
	private SysLogService sysLogService;

	@Pointcut("@annotation(com.xmsy.server.zxyy.manager.common.annotation.SysLog)")
	public void logPointCut() {

	}

	@Around("logPointCut()")
	public Object around(ProceedingJoinPoint point) throws Throwable {
		long beginTime = System.currentTimeMillis();
		// 执行方法
		Object result = point.proceed();
		// 执行时长(毫秒)
		long time = System.currentTimeMillis() - beginTime;
		// 保存日志
		saveSysLog(point, time);
		return result;
	}

	private void saveSysLog(ProceedingJoinPoint joinPoint, long time) {
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		Method method = signature.getMethod();

		SysLogEntity sysLog = new SysLogEntity();
		SysLog syslog = method.getAnnotation(SysLog.class);
		if (syslog != null) {
			// 注解上的描述
			sysLog.setOperation(syslog.value());
		}

		// 请求的方法名
		String className = joinPoint.getTarget().getClass().getName();
		String methodName = signature.getName();
		sysLog.setMethod(className + "." + methodName + "()");

		// 请求的参数
		Object[] args = joinPoint.getArgs();
		try {
			String params = new Gson().toJson(args[0]);
			sysLog.setParams(params);
		} catch (Exception e) {

		}
		// 获取request
		HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
		// 设置IP地址
		sysLog.setIp(IpUtil.getIPAddress(request));
		String username = null;
		if (!StringUtils.isEmpty(methodName) && methodName.equals("login")) {
			SysLoginForm entity = (SysLoginForm) args[0];
			username = entity.getUsername();
		} else {
			if (null == SecurityUtils.getSubject().getPrincipal()) {
				return;
			}
			username = ((SysUserEntity) SecurityUtils.getSubject().getPrincipal()).getUsername();
		}
		// 用户名
		sysLog.setUsername(username);
		sysLog.setTime(time);
		sysLog.setCreateDate(new Date());
		// 保存系统日志
		sysLogService.insert(sysLog);
	}
}
