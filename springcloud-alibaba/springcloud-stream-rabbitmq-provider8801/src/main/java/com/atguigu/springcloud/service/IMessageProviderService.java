package com.atguigu.springcloud.service;

/**
 * @author lixiaolong
 */
public interface IMessageProviderService {
    /**
     * 定义消息的推送管道
     *
     * @return
     */
    String send();
}
