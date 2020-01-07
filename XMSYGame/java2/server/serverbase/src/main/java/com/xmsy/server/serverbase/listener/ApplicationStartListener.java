package com.xmsy.server.serverbase.listener;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import com.xmsy.server.serverbase.result.GlobalJsonReturn;

@WebListener
public class ApplicationStartListener implements ServletContextListener {

	@Autowired
	private RequestMappingHandlerAdapter requestMappingHandlerAdapter;
	@Resource
	private GlobalJsonReturn globalJsonReturn;

	@Override
	public void contextInitialized(ServletContextEvent servletContextEvent) {
		List<HandlerMethodReturnValueHandler> handlerlist = new ArrayList<HandlerMethodReturnValueHandler>();
		handlerlist.addAll(requestMappingHandlerAdapter.getReturnValueHandlers());
		// 把自定义的统一json返回解析放到最前面，最早解析
		handlerlist.add(0, globalJsonReturn);
		requestMappingHandlerAdapter.setReturnValueHandlers(handlerlist);
		Assert.notNull(requestMappingHandlerAdapter, "[contextInitialized] handlerAdapter is empty");
		// 加载系统配置项
	}

	@Override
	public void contextDestroyed(ServletContextEvent servletContextEvent) {
		System.out.println("contextDestroyed");
	}
}
