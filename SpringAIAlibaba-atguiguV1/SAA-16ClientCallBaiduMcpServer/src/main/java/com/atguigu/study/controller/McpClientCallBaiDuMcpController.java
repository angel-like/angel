package com.atguigu.study.controller;

import jakarta.annotation.Resource;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

/**
 * @auther zzyybs@126.com
 * @create 2025-08-01 15:57
 * @Description TODO
 */
@RestController
public class McpClientCallBaiDuMcpController
{
    @Resource
    private ChatClient chatClient; //添加了MCP调用能力

    @Resource
    private ChatModel chatModel; //没有添加MCP调用能力

    /**
     * 添加了MCP调用能力
     * http://localhost:8016/mcp/chat?msg=查询北纬39.9042东经116.4074天气
     * http://localhost:8016/mcp/chat?msg=查询61.149.121.66归属地
     * http://localhost:8016/mcp/chat?msg=查询昌平到天安门路线规划
     * @param msg
     * @return
     */
    @GetMapping("/mcp/chat")
    public Flux<String> chat(String msg)
    {
        return chatClient.prompt(msg).stream().content();
    }

    /**
     * 没有添加MCP调用能力
     * http://localhost:8016/mcp/chat2?msg=查询北纬39.9042东经116.4074天气
     * @param msg
     * @return
     */
    @RequestMapping("/mcp/chat2")
    public Flux<String> chat2(String msg)
    {
        return chatModel.stream(msg);
    }
}

