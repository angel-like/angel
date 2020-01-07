package com.xmsy.server.zxyy.robot.common.utils;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class HttpContextUtils {

	public static HttpServletRequest getHttpServletRequest() {
		return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
	}

	public static String getDomain(){
		HttpServletRequest request = getHttpServletRequest();
		StringBuffer url = request.getRequestURL();
		return url.delete(url.length() - request.getRequestURI().length(), url.length()).toString();
	}

	public static String getOrigin(){
		HttpServletRequest request = getHttpServletRequest();
		return request.getHeader("Origin");
	}
	
}
