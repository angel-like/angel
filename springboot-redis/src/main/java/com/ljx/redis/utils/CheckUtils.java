package com.ljx.redis.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.awt.print.Book;

/**
 * ClassName: CheckUtils
 * Package: com.ljx.redis.utils
 * Description:
 *
 * @Author Xu, Luqin
 * @Create 2024/11/2 19:08
 * @Version 1.0
 */
@Slf4j
@Component
public class CheckUtils {
    @Autowired
    private RedisTemplate redisTemplate;
    public boolean checkWithBloomFilter(String checkItem, String key){
        int hashCode = Math.abs(key.hashCode());
        Long index = (long) (hashCode % Math.pow(2, 32));
        Boolean ifExists = redisTemplate.opsForValue().getBit(checkItem, index);

        log.info("---> key: {}, 对应的坑位是： {}， 是否存在：{}", key, index, ifExists);

        return ifExists;
    }
}
