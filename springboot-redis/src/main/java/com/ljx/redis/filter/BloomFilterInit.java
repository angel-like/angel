package com.ljx.redis.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * 布隆过滤器
 *
 * @Author Xu, Luqin
 * @Create 2024/11/2 18:59
 * @Version 1.0
 */
@Component
@Slf4j
public class BloomFilterInit {
    @Autowired
    private RedisTemplate redisTemplate;
    private final static String WHITELIST_KEY = "whitelistCustomer";
    private final static String customerIdKey = "customer:13";

    /**
     * 布隆过滤器白名单初始化工具类，又开始就设置一部分数据为白名单所有
     * 白名单业务默认规定：布隆过滤器有，Redis是极大可能有
     * 白名单：whitelistCustomer
     */
    @PostConstruct
    public void init(){
        //1. 白名单客户计算hashvalue，加入到布隆过滤器  由于存在计算出来负数的可能，需要取绝对值
        Integer hashCode = Math.abs(customerIdKey.hashCode());
        //2. 通过hashValue和2^32取余，获得对应的下标坑位
        Long index = (long)(hashCode % Math.pow(2, 32));

        log.info("customerId: {}, 对应的index是： {}", customerIdKey, index);
        //3. 设置Redis 里面的bitmap对应白名单类型的坑位，并设置为1
        redisTemplate.opsForValue().setBit(WHITELIST_KEY, index, true);
    }

//    @PostConstruct
//    public void init() {
//        // 1 白名单客户加载到布隆过滤器
//        String key = "customer:12";
//        // 2 计算hashvalue，由于存在计算出来负数的可能，需要取绝对值
//        int hashValue = Math.abs(key.hashCode());
//        // 3 通过hashValue和2^32取余，获得对应的下标坑位
//        long index = (long) (hashValue % Math.pow(2, 32));
//        log.info(key + "对应的坑位index：{}", index);
//        // 4 设置Redis 里面的bitmap对应白名单类型的坑位，并设置为1
//        redisTemplate.opsForValue().setBit("whitelistCustomer", index, true);
//
//    }
}
