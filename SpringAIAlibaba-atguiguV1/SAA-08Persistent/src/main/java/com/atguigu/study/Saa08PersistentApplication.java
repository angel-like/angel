package com.atguigu.study;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @auther zzyybs@126.com
 * @create 2025-07-28 17:59
 * @Description
 * 1  将客户和大模型的对话问答保存进Redis进行持久化记忆留存
 */
@SpringBootApplication
public class Saa08PersistentApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(Saa08PersistentApplication.class,args);
    }
}
