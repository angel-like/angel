package com.atguigu.study.config;

import com.alibaba.cloud.ai.dashscope.api.DashScopeApi;
import com.alibaba.cloud.ai.dashscope.chat.DashScopeChatModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @auther zzyybs@126.com
 * @create 2025-07-22 0:51
 */
@Configuration
public class SaaLLMConfig
{

    /**
     * 方式1:${}
     * 持有yml文件配置：spring.ai.dashscope.api-key=${aliQwen-api}  ，
     * △这里其实无需要在做个配置，已经自动装配了
     */
    /*@Value("${spring.ai.dashscope.api-key}")
    private String apiKey;

    @Bean
    public DashScopeApi dashScopeApi()
    {
        return DashScopeApi.builder().apiKey(apiKey).build();
    }*/



    /**
     * 方式2:System.getenv("环境变量")
     * 持有yml文件配置：spring.ai.dashscope.api-key=${aliQwen-api}
     * @return
     */
//    @Bean
//    public DashScopeApi dashScopeApi()
//    {
//        return DashScopeApi.builder()
//                .apiKey(System.getenv("aliQwen-api"))
//            .build();
//    }

}
