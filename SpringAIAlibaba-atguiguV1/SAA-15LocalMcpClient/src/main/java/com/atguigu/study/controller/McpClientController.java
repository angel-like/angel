package com.atguigu.study.controller;

import jakarta.annotation.Resource;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

/**
 * @auther zzyybs@126.com
 * @create 2025-07-31 21:14
 * @Description TODO
 */

@RestController
public class McpClientController
{
    @Resource
    private ChatClient chatClient;//使用mcp支持

    @Resource
    private ChatModel chatModel;//没有纳入tool支持，普通调用

    // http://localhost:8015/mcpclient/chat?msg=上海
    @GetMapping("/mcpclient/chat")
    public Flux<String> chat(@RequestParam(name = "msg",defaultValue = "北京") String msg)
    {
        System.out.println("使用了mcp");
        return chatClient.prompt(msg).stream().content();
    }

    @RequestMapping("/mcpclient/chat2")
    public Flux<String> chat2(@RequestParam(name = "msg",defaultValue = "北京") String msg)
    {
        System.out.println("未使用mcp");
        return chatModel.stream(msg);
    }
}
