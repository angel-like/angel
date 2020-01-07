package com.xmsy.server.zxyy.webhome.filter;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.xmsy.server.zxyy.webhome.common.exception.RRException;
import com.xmsy.server.zxyy.webhome.common.utils.ErrorCode;
import com.xmsy.server.zxyy.webhome.modules.manager.ipblacklist.entity.IpBlacklistEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.ipblacklist.service.IpBlacklistService;
import com.xmsy.server.zxyy.webhome.utils.IpUtil;

/**
 * token校验
 * 
 * @author Administrator
 *
 */
@Component
public class IpInterceptor implements HandlerInterceptor {
	@Autowired
    private IpBlacklistService ipBlacklistService;
	
	// 在请求处理之前进行调用（Controller方法调用之前
	@Override
	public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o)
			throws Exception {
		String ip = IpUtil.getIPAddress(httpServletRequest);
		if(!StringUtils.isEmpty(ip)) {
			List<IpBlacklistEntity> list=ipBlacklistService.selectList(new EntityWrapper<IpBlacklistEntity>(new IpBlacklistEntity()));
			if(!CollectionUtils.isEmpty(list)) {
				for(IpBlacklistEntity entity:list) {
					if(!StringUtils.isEmpty(entity.getIp())&&entity.getIp().equals(ip)) {
						throw new RRException(ErrorCode.BlackListErrorCode.IP_BLACK_ERRO.getErrMsg(),
								ErrorCode.BlackListErrorCode.IP_BLACK_ERRO.getCode());
					}
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