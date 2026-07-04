package com.atguigu.study.config;

import com.alibaba.cloud.ai.dashscope.api.DashScopeApi;
import com.alibaba.cloud.ai.dashscope.chat.DashScopeChatModel;
import com.alibaba.cloud.ai.dashscope.chat.DashScopeChatOptions;
import com.alibaba.cloud.ai.memory.redis.RedisChatMemoryRepository;
import jakarta.annotation.Resource;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @auther zzyybs@126.com
 * @create 2025-07-25 18:53
 * @Description ChatModel+ChatClient+多模型共存
 */
@Configuration
public class SaaLLMConfig
{
    // 模型名称常量定义
    private final String  DEEPSEEK_MODEL = "deepseek-v3";
    private final String QWEN_MODEL = "qwen-plus";

    @Bean(name = "deepseek")
    public ChatModel deepSeek()
    {
        return DashScopeChatModel.builder()
                        .dashScopeApi(DashScopeApi.builder()
                                    .apiKey("sk-b05dbad575444daca72a05e3e3b960b3")
                                .build())
                .defaultOptions(
                        DashScopeChatOptions.builder().withModel(DEEPSEEK_MODEL).build()
                )
                .build();
    }

    @Bean(name = "qwen")
    public ChatModel qwen()
    {
        return DashScopeChatModel.builder().dashScopeApi(DashScopeApi.builder()
                        .apiKey("sk-b05dbad575444daca72a05e3e3b960b3")
                        .build())
                .defaultOptions(
                        DashScopeChatOptions.builder()
                                .withModel(QWEN_MODEL)
                                .build()
                )
                .build();
    }

    /**
     * △注意： 这里增加了ChatMemory的记忆功能
     */
    @Bean(name = "qwenChatClient")
    public ChatClient qwenChatClient(@Qualifier("qwen") ChatModel qwen,
                                     RedisChatMemoryRepository redisChatMemoryRepository)
    {
        MessageWindowChatMemory windowChatMemory = MessageWindowChatMemory.builder()
                            .chatMemoryRepository(redisChatMemoryRepository)
                            .maxMessages(10)
                        .build();

        return ChatClient.builder(qwen)
                    .defaultOptions(ChatOptions.builder().model(QWEN_MODEL).build())
                    //这里添加了Advisors顾问增强器
                    .defaultAdvisors(MessageChatMemoryAdvisor.builder(windowChatMemory).build())
                .build();
    }



    /**
     * 家庭作业，按照上述模范qwen完成基于deepseek的模型存储
     * @param deepSeek
     * @return
     */
    @Bean(name = "deepseekChatClient")
    public ChatClient deepseekChatClient(@Qualifier("deepseek") ChatModel deepSeek,RedisChatMemoryRepository redisChatMemoryRepository)
    {
        //1.注入redisChatMemoryRepository到windowChatMemory
        MessageWindowChatMemory windowChatMemory= MessageWindowChatMemory.builder().chatMemoryRepository(redisChatMemoryRepository).maxMessages(10).build();
        return  ChatClient.builder(deepSeek)
                //2.windowChatMemory注入到Advisors顾问增强器
                .defaultOptions(ChatOptions.builder().model(DEEPSEEK_MODEL).build())
                .defaultAdvisors(MessageChatMemoryAdvisor.builder(windowChatMemory).build())
                .build();
    }
}