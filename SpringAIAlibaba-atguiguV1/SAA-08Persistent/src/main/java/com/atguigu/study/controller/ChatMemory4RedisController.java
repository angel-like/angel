package com.atguigu.study.controller;

import jakarta.annotation.Resource;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.function.Consumer;

/**
 * @auther zzyybs@126.com
 * @create 2025-07-28 18:40
 * @Description TODO
 */
@RestController
public class ChatMemory4RedisController
{
    @Resource(name = "qwenChatClient")
    private ChatClient qwenChatClient;

    @Resource(name = "deepseekChatClient")
    private ChatClient deepseekChatClient;
    /**
     * zzyybs@126.com
     * @param msg
     * @param userId
     * @return
     */
    @GetMapping("/chatmemory/chat")
    public String chat(String msg, String userId)
    {
        /*return qwenChatClient.prompt(msg).advisors(new Consumer<ChatClient.AdvisorSpec>()
        {
            @Override
            public void accept(ChatClient.AdvisorSpec advisorSpec)
            {
                advisorSpec.param(CONVERSATION_ID, userId);
            }
        }).call().content();*/


        return qwenChatClient
                .prompt(msg)
                //这里就是记录上下文对话的
                .advisors(advisorSpec -> advisorSpec.param(ChatMemory.CONVERSATION_ID, userId))
                .call()
                .content();

    }

    /**
     */
    @GetMapping("/chatmemory/deepChat")
    public Flux<String> deepChat(String msg, String userId)
    {
        /*return qwenChatClient.prompt(msg).advisors(new Consumer<ChatClient.AdvisorSpec>()
        {
            @Override
            public void accept(ChatClient.AdvisorSpec advisorSpec)
            {
                advisorSpec.param(CONVERSATION_ID, userId);
            }
        }).call().content();*/


        return deepseekChatClient
                .prompt(msg)
                //这里就是记录上下文对话的
                .advisors(advisorSpec -> advisorSpec.param(ChatMemory.CONVERSATION_ID, userId))
                .stream()
                .content();

    }
}
