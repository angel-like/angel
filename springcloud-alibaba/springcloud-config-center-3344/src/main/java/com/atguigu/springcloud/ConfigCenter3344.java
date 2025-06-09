package com.atguigu.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.config.server.EnableConfigServer;

/**
 * @author lixiaolong
 *  curl -X POST "http://localhost:3344/actuator/bus-refresh"  去刷新config客户端配置值信息
 */
@SpringBootApplication
@EnableConfigServer
//@ServletComponentScan("com.atguigu.springcloud.filter")
public class ConfigCenter3344 {

    public static void main(String[] args) {
        SpringApplication.run(ConfigCenter3344.class, args);
        System.out.println("-------------ConfigCenter3344启动成功-------------");

    }

}
