package com.atguigu.study.controller;

import jakarta.annotation.Resource;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @auther zzyybs@126.com
 * @create 2025-07-23 19:22
 * 知识出处：
 * https://java2ai.com/docs/1.0.0.2/tutorials/basics/chat-client/?spm=5176.29160081.0.0.2856aa5cmUTyXC#%E5%88%9B%E5%BB%BA-chatclient
 */
@RestController
public class ChatClientController
{
    private final ChatClient dashScopeChatClient;

    /** zzyybs@126.com
     * ChatClient不支持自动输入，依赖ChatModel对象接口，ChatClient.builder(dashScopeChatModel).build();
     * @param dashScopeChatModel
     */
    public ChatClientController(ChatModel dashScopeChatModel)
    {
        this.dashScopeChatClient = ChatClient.builder(dashScopeChatModel).build();
    }

    @GetMapping("/chatclient/dochat")
    public String doChat(@RequestParam(name = "msg",defaultValue = "2加9等于几") String msg)
    {
        return dashScopeChatClient.prompt().user(msg).call().content();
    }
}
