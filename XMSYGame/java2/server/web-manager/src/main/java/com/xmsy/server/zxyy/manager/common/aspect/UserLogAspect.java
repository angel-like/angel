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

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xmsy.server.zxyy.manager.common.annotation.UserLog;
import com.xmsy.server.zxyy.manager.common.utils.HttpContextUtils;
import com.xmsy.server.zxyy.manager.modules.manager.sys.entity.SysUserEntity;
import com.xmsy.server.zxyy.manager.modules.manager.userlog.entity.UserLogEntity;
import com.xmsy.server.zxyy.manager.modules.manager.userlog.service.UserLogService;
import com.xmsy.server.zxyy.manager.utils.IpUtil;

/**
 * 系统日志，切面处理类
 * 
 * @author lpp
 * @email xxxxxx
 * @date 2019年01月04日 上午15:07:35
 */
@Aspect
@Component
public class UserLogAspect {
	@Autowired
	private UserLogService userLogService;

	@Pointcut("@annotation(com.xmsy.server.zxyy.manager.common.annotation.UserLog)")
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

		UserLogEntity logEntity = new UserLogEntity();
		UserLog userLog = method.getAnnotation(UserLog.class);
		if (userLog != null) {
			// 注解上的描述
			logEntity.setOperation(userLog.value());
		}

		// 请求的方法名
		String className = joinPoint.getTarget().getClass().getName();
		String methodName = signature.getName();
		logEntity.setMethod(className + "." + methodName + "()");

		// 请求的参数
		Object[] args = joinPoint.getArgs();
		try {
			JSONObject param = (JSONObject) JSON.toJSON(args[0]);
			logEntity.setParams(param.toString());
			JSONObject userOperater = (JSONObject) param.get("userOperater");
			logEntity.setMemberId(userOperater.getLong("memberId"));
			logEntity.setMemberAccount(userOperater.getString("memberAccount"));
			logEntity.setRemark(userOperater.getString("remark"));
		} catch (Exception e) {

		}

		// 获取request
		HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
		// 设置IP地址
		logEntity.setIp(IpUtil.getIPAddress(request));

		// 用户名
		String username = ((SysUserEntity) SecurityUtils.getSubject().getPrincipal()).getUsername();
		logEntity.setUsername(username);

		logEntity.setTime(time);
		logEntity.setCreateDate(new Date());
		// 保存日志
		userLogService.insert(logEntity);
	}
}
