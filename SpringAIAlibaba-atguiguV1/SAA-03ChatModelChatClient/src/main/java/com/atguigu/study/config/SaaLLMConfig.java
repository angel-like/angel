package com.atguigu.study.config;

import com.alibaba.cloud.ai.dashscope.api.DashScopeApi;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @auther zzyy
 * @create 2025-07-22 0:51
 */
@Configuration
public class SaaLLMConfig
{
    @Bean
    public DashScopeApi dashScopeApi()
    {
        return DashScopeApi.builder()
                    .apiKey("sk-b05dbad575444daca72a05e3e3b960b3")
                .build();
    }


    /**
     * 知识出处：
     * https://java2ai.com/docs/1.0.0.2/tutorials/basics/chat-client/?spm=5176.29160081.0.0.2856aa5cmUTyXC#%E5%88%9B%E5%BB%BA-chatclient
     * @param dashscopeChatModel
     * @return
     */
    @Bean
    public ChatClient chatClient(ChatModel dashscopeChatModel)
    {
        return ChatClient.builder(dashscopeChatModel).build();
    }
}