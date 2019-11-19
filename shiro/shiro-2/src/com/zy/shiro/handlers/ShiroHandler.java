package com.zy.shiro.handlers;

import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zy.shiro.services.ShiroService;
@Controller
@RequestMapping("/shiro")
public class ShiroHandler {
	@Autowired
	private ShiroService shiroService;
	
	@RequestMapping("/test")
	public String testShiroAnnotation(HttpSession session) {
		session.setAttribute("key", "value12345");
		shiroService.testMethod();
		return "redirect:/list.jsp";
	}
	
	@RequestMapping("/login")
	public String login(String username,String password) {
		Subject subject = SecurityUtils.getSubject();
		// 2.测试当前的用户是否已经被认证. 即是否已经登录. 
		  if (!subject.isAuthenticated()) {
	        	// (1).把用户名和密码封装为 UsernamePasswordToken 对象    这个用户名密码已经在配置文件里配置了，不需要用到数据库查询啥的
	            UsernamePasswordToken token = new UsernamePasswordToken(username, password);
	            // (2).rememberme 记住我    记住token，shiro里面记住这个账户密码
	            token.setRememberMe(true);//类似淘宝，不登录能访问一些页面，要访问私人资料要登录
	            try {
	            	// (3).执行登录. 
	            	subject.login(token);//进入realms包下的 ShiroRealm.java里的doGetAuthenticationInfo方法
	            } 
	            // 所有认证时异常的父类. 
	            catch (AuthenticationException ae) {
	                System.out.println("登录失败:"+ae.getMessage());
	            }
	        }
		return "redirect:/list.jsp";
	}
}
