package com.ljx.redis.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

@Service
@Slf4j
public class OrderService {

    @Resource
    private RedisTemplate redisTemplate;
    private static final String ORDER_KEY = "order:";

    public void addOrder(){
        Integer keyId = ThreadLocalRandom.current().nextInt(1000) + 1;
        String value = "京东订单：" + UUID.randomUUID().toString();
        String key = ORDER_KEY + keyId;
        redisTemplate.opsForValue().set(key, value);
        log.info("{} 订单生成成功：{}", key ,value);
    }

    public String getOrder(Integer keyId){
        return (String) redisTemplate.opsForValue().get(ORDER_KEY + keyId);
    }
}
