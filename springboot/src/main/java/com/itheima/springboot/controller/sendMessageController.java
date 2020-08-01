package com.itheima.springboot.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhenzi.sms.ZhenziSmsClient;

@Controller
public class sendMessageController {
	
	@RequestMapping("sendMessage")//路径直接http://localhost:8080/sendMessage   不要写项目名
    @ResponseBody//返回给页面字符数据
    public String message(@RequestParam Integer mobile) throws Exception {
		//2.初始化client
		ZhenziSmsClient client = new ZhenziSmsClient("https://sms_developer.zhenzikj.com", "appId", "appSecret");
		//3.发送短信
		Map<String, Object> params = new HashMap<String, Object>();
		   params.put("number", mobile);
		   params.put("templateId", "0");
		   String[] templateParams = new String[2];
		   templateParams[0] = "3421";
		   templateParams[1] = "5分钟";
		   params.put("templateParams", templateParams);
		   String result = client.send(params);
        return result;//返回结果 自己去解析
    }
}
