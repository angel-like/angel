package com.atguigu.study.config;

import com.alibaba.cloud.ai.memory.redis.RedisChatMemoryRepository;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @auther zzyybs@126.com
 * @create 2025-07-28 18:24
 * @Description TODO
 */
@Configuration
public class RedisMemoryConfig
{
    @Value("${spring.data.redis.host}")
    private String host;
    @Value("${spring.data.redis.port}")
    private int port;

    /**
     * 这里配置要保存到redis的chatMemory类
     * @return
     */
    @Bean
    public RedisChatMemoryRepository redisChatMemoryRepository()
    {
        return RedisChatMemoryRepository.builder()
                    .host(host)
                    .port(port)
                .build();
    }
}
