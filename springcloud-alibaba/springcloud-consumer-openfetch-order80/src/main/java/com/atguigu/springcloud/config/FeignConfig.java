package com.atguigu.springcloud.config;

import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author lixiaolong
 * @date 2020-02-20 9:40
 */
@Configuration
public class FeignConfig {
    /**
     * NONE,   不显示任何日志
     * BASIC,  请求方法、url+响应状态码及执行时间
     * HEADERS, BASIC+请求和响应头信息
     * FULL;  HEADERS+ 请求和响应的正文及元数据
     * <p>
     * 日志级别
     *
     * @return
     */
    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }
}
