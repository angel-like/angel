package com.zy.shiro.factory;

import java.util.LinkedHashMap;

public class FilterChainDefinitionMapBuilder {
	
	public LinkedHashMap<String, String> buildFilterChainDefinitionMap(){
		LinkedHashMap<String, String> map = new LinkedHashMap<>();
		
		map.put("/login.jsp", "anon");
		map.put("/shiro/login", "anon");
		map.put("/shiro/logout", "logout");
		map.put("/user.jsp", "authc,roles[123]");//authc一定要认证才能访问，token.setRememberMe(true)访问不了
		map.put("/admin.jsp", "authc,roles[admin]");
		map.put("/list.jsp", "user");//token.setRememberMe(true)可以访问
		
		map.put("/**", "authc");
		
		return map;
	}
}
