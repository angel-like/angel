package com.atguigu.study.controller;

import com.alibaba.cloud.ai.dashscope.api.DashScopeApi;
import jakarta.annotation.Resource;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @auther zzyy
 * @create 2025-07-23 18:20
 */
@RestController
public class ChatModelController
{
    @Resource //支持自动注入
    private ChatModel dashScopeChatModel;

    /*@Resource //ChatClient 不支持自动注入
    private ChatClient chatClient;*/

    /**
     * http://localhost:8003/chatmodel/dochat
     * @param msg
     * @return
     */
    @GetMapping("/chatmodel/dochat")
    public String doChat(@RequestParam(name = "msg",defaultValue = "你是谁") String msg)
    {
        String result = dashScopeChatModel.call(msg);
        System.out.println("响应：" + result);
        return result;
    }
}
