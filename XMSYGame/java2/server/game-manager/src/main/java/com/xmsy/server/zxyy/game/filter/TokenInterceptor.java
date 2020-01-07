package com.xmsy.server.zxyy.game.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.xmsy.server.zxyy.game.common.exception.RRException;
import com.xmsy.server.zxyy.game.common.utils.ErrorCode;

/**
 * token校验
 * 
 * @author Administrator
 *
 */
@Component
@PropertySource({ "classpath:definition.properties" })
public class TokenInterceptor implements HandlerInterceptor {
	// 在请求处理之前进行调用（Controller方法调用之前
	// 大厅请求token
	@Value("${hall-request-token}")
	private String hallRequestToken;
	// 后台请求token
	@Value("${backstage-request-token}")
	private String backstageRequestToken;

	@Override
	public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o)
			throws Exception {
		String token = httpServletRequest.getHeader("token");// 获取到前台传来的token
		if (httpServletRequest.getMethod().equals(RequestMethod.OPTIONS.name())) {
			httpServletResponse.setStatus(HttpStatus.OK.value());
			return false;
		}
		if (StringUtils.isEmpty(token)) {
			System.out.println("请求url: "+httpServletRequest.getRequestURI());
			throw new RRException("还未登录,请先登录", ErrorCode.invalid);
		}
		if (!token.equals(backstageRequestToken) && !token.equals(hallRequestToken)) {
			System.out.println("请求url: "+httpServletRequest.getRequestURI());
			throw new RRException("登录失效,请重新登录", ErrorCode.lose);
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