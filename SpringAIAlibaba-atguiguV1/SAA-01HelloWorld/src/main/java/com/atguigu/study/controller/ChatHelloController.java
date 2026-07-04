package com.atguigu.study.controller;

import com.alibaba.cloud.ai.dashscope.api.DashScopeApi;
import jakarta.annotation.Resource;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

/**
 * @auther zzyybs@126.com
 * @create 2025-07-22 0:47
 */
@RestController
public class ChatHelloController
{

    @Resource // 对话模型，调用阿里云百炼平台
    private ChatModel chatModel;

    /**
     * 通用调用
     * @param msg
     * @return
     */
    @GetMapping(value = "/hello/dochat")
    public String doChat(@RequestParam(name = "msg",defaultValue="你是谁") String msg)
    {
        String result = chatModel.call(msg);
        return result;
    }

    /**
     * 流式返回调用
     * @param msg
     * @return
     */
    @GetMapping(value = "/hello/streamchat")
    public Flux<String> stream(@RequestParam(name = "msg",defaultValue="你是谁") String msg)
    {
        return chatModel.stream(msg);
    }
}
