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
 * @create 2025-07-23 19:31
 */
@RestController
public class ChatClientControllerV2
{
    @Resource
    private ChatModel chatModel;
    @Resource
    private ChatClient dashScopechatClientv2;

    /**
     * http://localhost:8003/chatclientv2/dochat
     * @param msg
     * @return
     */
    @GetMapping("/chatclientv2/dochat")
    public String doChat(@RequestParam(name = "msg",defaultValue = "你是谁") String msg)
    {
        String result = dashScopechatClientv2.prompt().user(msg).call().content();
        System.out.println("ChatClient响应：" + result);
        return result;
    }

    /**
     * http://localhost:8003/chatmodelv2/dochat
     * @param msg
     * @return
     */
    @GetMapping("/chatmodelv2/dochat")
    public String doChat2(@RequestParam(name = "msg",defaultValue = "你是谁") String msg)
    {
        String result = chatModel.call(msg);
        System.out.println("ChatModel响应：" + result);
        return result;
    }

}
