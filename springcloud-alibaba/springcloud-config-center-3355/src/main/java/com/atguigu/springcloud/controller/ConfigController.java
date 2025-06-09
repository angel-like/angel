package com.atguigu.springcloud.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lixiaolong
 * @RefreshScope 实现热加载 自动获取刷新内容
 */
@RestController
@RefreshScope
public class ConfigController {

    @Value("${config.info}")//这里配的就是 git上配置文件里面的内容
    private String configInfo;

    @GetMapping("/configInfo")
    public String getConfigInfo() {
        return configInfo;
    }
}
