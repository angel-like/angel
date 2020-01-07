package com.xmsy.server.zxyy.manager.filter;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.xmsy.server.zxyy.manager.common.exception.RRException;
import com.xmsy.server.zxyy.manager.common.utils.ErrorCode;
import com.xmsy.server.zxyy.manager.modules.manager.userblacklist.entity.UserBlacklistEntity;
import com.xmsy.server.zxyy.manager.modules.manager.userblacklist.service.UserBlacklistService;
import com.xmsy.server.zxyy.manager.utils.JwtUtil;

/**
 * token校验
 * 
 * @author Administrator
 *
 */
@Component
public class UserInterceptor implements HandlerInterceptor {
	@Autowired
	private UserBlacklistService userBlacklistService;

	// 在请求处理之前进行调用（Controller方法调用之前
	@Override
	public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o)
			throws Exception {
		String token = httpServletRequest.getHeader("token");
		String userToken = JwtUtil.getUserId(token);
		// 判断是否是大厅请求
		if ("hallrequesthome".equals(userToken)) {
			return true;
		}
		Long userId = Long.valueOf(JwtUtil.getUserId(token));
		List<UserBlacklistEntity> list = userBlacklistService.selectList(new EntityWrapper<UserBlacklistEntity>(new UserBlacklistEntity()));
		if (!CollectionUtils.isEmpty(list)) {
			for (UserBlacklistEntity entity : list) {
				if (entity.getUserId() != null && entity.getUserId() != 0 && entity.getUserId().equals(userId)) {
					throw new RRException(ErrorCode.BlackListErrorCode.USER_BLACK_ERRO.getErrMsg(),
							ErrorCode.BlackListErrorCode.USER_BLACK_ERRO.getCode());
				}
			}
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