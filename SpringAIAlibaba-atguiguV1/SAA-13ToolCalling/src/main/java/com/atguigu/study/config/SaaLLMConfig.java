package com.atguigu.study.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @auther zzyybs@126.com
 * @create 2025-07-31 20:47
 * @Description TODO
 */
@Configuration
public class SaaLLMConfig
{
    @Bean
    public ChatClient chatClient(ChatModel chatModel)
    {
        return ChatClient.builder(chatModel).build();
    }
}
























