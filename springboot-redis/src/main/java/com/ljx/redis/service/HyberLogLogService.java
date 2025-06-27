package com.ljx.redis.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import sun.util.resources.cldr.es.CalendarData_es_PY;

import javax.annotation.PostConstruct;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * ClassName: HyberLogLogService
 * Package: com.ljx.redis.service
 * Description:
 *
 * @Author Xu, Luqin
 * @Create 2024/11/2 11:31
 * @Version 1.0
 */
@Service
@Slf4j
public class HyberLogLogService {

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 模拟后台有用户点击首页，每个用户来自不同ip地址
     */
//    @PostConstruct
    public void init() {
        new Thread(() -> {
            for (int i = 0; i < 200; i++) {
                String ip = null;
                Random random = new Random();
                ip = random.nextInt(256) + "." +
                        random.nextInt(256) + "." +
                        random.nextInt(256);

                Long hll = redisTemplate.opsForHyperLogLog().add("hll", ip);
                log.info("ip = {}, 该ip访问首页, 次数为：{}", ip, hll);
                try { TimeUnit.SECONDS.sleep(3); } catch (InterruptedException e) { throw new RuntimeException(e); }

            }
        }, "t1").start();
    }

    public Long getUv() {
        return redisTemplate.opsForHyperLogLog().size("hll");
    }
}
