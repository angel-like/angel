package com.xmsy.server.zxyy.webhome.listener;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

/**
 * 启动加载游戏信息到缓存
 * @author adu
 * @date 2019-02-25 16:04:27
 */
public class ApplicationStartup implements ApplicationListener<ContextRefreshedEvent> {
	   @Override
	    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
		  
		   
	    }
}

