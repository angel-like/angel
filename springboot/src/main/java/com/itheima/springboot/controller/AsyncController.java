package com.itheima.springboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AsyncController {
	@Autowired
	private com.itheima.springboot.service.AsyncService AsyncService;
	@GetMapping("hello3")//路径直接http://localhost:8080/hello   不要写项目名
	public String hello() {
		AsyncService.hello();//开启异步注解，这方法会不会立即执行，可以先跳过，直接执行下面的结果
		return "success";
	}
}
