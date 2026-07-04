package com.atguigu.study.config;

import com.atguigu.study.service.WeatherService;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @auther zzyybs@126.com
 * @create 2025-07-31 21:08
 * @Description TODO
 */

@Configuration
public class McpServerConfig
{
    /**
     * 将工具方法暴露给外部 mcp client 调用
     * @param weatherService
     * @return
     */
    @Bean
    public ToolCallbackProvider weatherTools(WeatherService weatherService)
    {
        return MethodToolCallbackProvider.builder()
                .toolObjects(weatherService)
                .build();
    }
}

