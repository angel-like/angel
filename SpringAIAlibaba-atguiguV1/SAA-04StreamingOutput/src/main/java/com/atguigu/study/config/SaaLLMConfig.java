package com.atguigu.study.config;

import com.alibaba.cloud.ai.dashscope.api.DashScopeApi;
import com.alibaba.cloud.ai.dashscope.chat.DashScopeChatModel;
import com.alibaba.cloud.ai.dashscope.chat.DashScopeChatOptions;
import org.springframework.ai.chat.client.ChatClient;
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
    // 模型名称常量定义，一套系统多模型共存
    private final String DEEPSEEK_MODEL = "deepseek-v3";
    private final String QWEN_MODEL = "qwen-max";

    @Bean(name = "deepseek")
    public ChatModel deepSeek()
    {
        return DashScopeChatModel.builder()
                .dashScopeApi(DashScopeApi.builder().apiKey("sk-b05dbad575444daca72a05e3e3b960b3").build())
                .defaultOptions(DashScopeChatOptions.builder().withModel(DEEPSEEK_MODEL).build())
                .build();
    }

    @Bean(name = "qwen")
    public ChatModel qwen()
    {
        return DashScopeChatModel.builder()
                //.dashScopeApi(DashScopeApi.builder().apiKey(System.getenv("aliQwen-api")).build()) //这种方式需要配环境变量
                .dashScopeApi(DashScopeApi.builder().apiKey("sk-b05dbad575444daca72a05e3e3b960b3").build())
                .defaultOptions(DashScopeChatOptions.builder().withModel(QWEN_MODEL).build())
                .build();
    }

    @Bean(name = "deepseekChatClient")
    public ChatClient deepseekChatClient(@Qualifier("deepseek") ChatModel deepseek)
    {
        return
            ChatClient.builder(deepseek)
                .defaultOptions(ChatOptions.builder().model(DEEPSEEK_MODEL).build())
            .build();
    }

    @Bean(name = "qwenChatClient")
    public ChatClient qwenChatClient(@Qualifier("qwen") ChatModel qwen)
    {
        return
                ChatClient.builder(qwen)
                        .defaultOptions(ChatOptions.builder().model(QWEN_MODEL).build())
                        .build();
    }
}