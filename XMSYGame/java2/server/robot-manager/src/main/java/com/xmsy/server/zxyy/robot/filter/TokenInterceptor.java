package com.xmsy.server.zxyy.robot.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.xmsy.server.zxyy.robot.common.exception.RRException;
import com.xmsy.server.zxyy.robot.common.utils.ErrorCode;
import com.xmsy.server.zxyy.robot.constant.SysConstant;

/**
 * token校验
 * 
 * @author Administrator
 *
 */
@Component
public class TokenInterceptor implements HandlerInterceptor {
	// 在请求处理之前进行调用（Controller方法调用之前
	@Override
	public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o)
			throws Exception {
		String token = httpServletRequest.getHeader("token");// 获取到前台传来的token
		if (httpServletRequest.getMethod().equals(RequestMethod.OPTIONS.name())) {
			httpServletResponse.setStatus(HttpStatus.OK.value());
			return false;
		}
		if (StringUtils.isEmpty(token)) {
			throw new RRException(ErrorCode.TokenCode.TOKEN_IS_NULL.getErrMsg(),
					ErrorCode.TokenCode.TOKEN_IS_NULL.getCode());
		}
		if (!SysConstant.TOKEN.equals(token)) {
			throw new RRException(ErrorCode.TokenCode.TOKEN_INVALID_LOSE.getErrMsg(),
					ErrorCode.TokenCode.TOKEN_INVALID_LOSE.getCode());
		}
		return true;
	}

	// 请求处理之后进行调用，但是在视图被渲染之前（Controller方法调用之后）
	@Override
	public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o,
			ModelAndView modelAndView) throws Exception {
	}

	// 在整个请求结束之后被调用，也就是在DispatcherServlet 渲染了对应的视图之后执行（主要是用于进行资源清理工作）
	@Override
	public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
			Object o, Exception e) throws Exception {
	}
}