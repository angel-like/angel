package com.zy.shiro.services;

import java.net.StandardSocketOptions;
import java.util.Date;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.session.Session;
import org.springframework.stereotype.Service;

public class ShiroService {
	
	@RequiresRoles({"admin"})//给与admin权限才能访问这个方法
	public void testMethod() {
		System.out.println("测试方法，time:"+new Date());
		Session session=SecurityUtils.getSubject().getSession();
		Object val=session.getAttribute("key");
		System.out.println("session:"+val);
	}
}
