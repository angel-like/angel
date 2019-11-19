package com.itheima.springboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
@Controller
public class Hello2Controller {
	@RequestMapping("hello2")//路径直接http://localhost:8080/hello   不要写项目名
    @ResponseBody//返回给页面字符数据
    public String hello() {
        return "hello2 world！第二个！";
    }
}
