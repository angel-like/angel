package com.itheima.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.demo.service.UserService;
@Controller
@RequestMapping("/user")
public class UserController {
	
	@Reference//引用       其他项目的接口实现类注解
	private UserService UserService;
	
	@RequestMapping("/showName")
	@ResponseBody    //http://localhost:8082/user/showName.do    获取到服务的  "dubbox名称----阿雄"
	public String showName() {
		
		return UserService.getName();
	}
}
