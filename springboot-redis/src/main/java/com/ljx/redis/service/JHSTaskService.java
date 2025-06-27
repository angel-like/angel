package com.ljx.redis.service;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import com.ljx.redis.entity.Product;
import com.sun.org.apache.bcel.internal.generic.NEW;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * ClassName: JHSTaskService
 * Package: com.ljx.redis.service
 * Description:
 * 采用定时器将参与聚划算活动的特价商品新增进入Redis中
 *
 * @Author Xu, Luqin
 * @Create 2024/11/3 7:02
 * @Version 1.0
 */
@Service
@Slf4j
public class JHSTaskService {
    @Autowired
    private RedisTemplate redisTemplate;

    private final static String JHS_KEY = "jhs";
    private final static String JHS_KEY_A = "jhs:a";
    private final static String JHS_KEY_B = "jhs:b";

    //    @PostConstruct
    public void initJHS() {
        log.info("---> TASK --> 聚划算 定时器 启动，执行更新需要参加聚划算活动的特价商品......{}", DateUtil.now());
        new Thread(() -> {
            while (true) {
                List<Product> fromMysql = this.getProductsFromMysql();
                /**
                 * 这里有风险，不是原子性的命令
                 * --》 存在热点打爆的风险
                 * --》 解决方案： 加锁，会慢； lua脚本也没用，不是根本问题；
                 * --》 新的解决方案： 互斥更新 ； 差异失效时间
                 */
                redisTemplate.delete(JHS_KEY);
                redisTemplate.opsForList().leftPushAll(JHS_KEY, fromMysql);
                try {
                    TimeUnit.MINUTES.sleep(1);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                log.info("---> iniJHS 定时刷新......{}", DateUtil.now());
            }
        }, "t1").start();
    }


    /**
     * 差异化失效实现，解决缓存击穿
     * 1. 新建： 开辟两块缓存，先更新B 再更新A
     * 2. 查询： 先查询主缓存A，如果A没有（消失或者失效了）再去查询B
     */
    @PostConstruct
    public void initJHSAB() {
        log.info("---> TASK --> 聚划算 定时器iniJHSAB 启动，使用双缓存，执行更新需要参加聚划算活动的特价商品......{}", DateUtil.now());
        new Thread(() -> {
            while (true) {
                List<Product> fromMysql = this.getProductsFromMysql();

                // 先更新缓存B，失效时间比 主缓存A多10s
                redisTemplate.delete(JHS_KEY_B);
                redisTemplate.opsForList().leftPushAll(JHS_KEY_B, fromMysql);
                redisTemplate.expire(JHS_KEY_B, 86410L, TimeUnit.SECONDS);

                // 再更新主缓存A ，失效时间和之前一直
                redisTemplate.delete(JHS_KEY_A);
                redisTemplate.opsForList().leftPushAll(JHS_KEY_A, fromMysql);
                redisTemplate.expire(JHS_KEY_A, 86400L, TimeUnit.SECONDS);

                try {
                    TimeUnit.MINUTES.sleep(1);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                log.info("---> iniJHSAB 定时刷新双缓存AB......{}", DateUtil.now());
            }
        }, "t1").start();
    }

    // 随机取出 10000其中的20条数据，用来在聚划算前台展示
    private List<Product> getProductsFromMysql() {
        List<Product> list = new ArrayList<>();
        for (int i = 1; i <= 20; i++) {
            Random random = new Random();
            int id = random.nextInt(10000);
            Product product = new Product((long) id, "product" + i, i, "detail" + i);
            list.add(product);
        }
        return list;
    }

    public List<Product> find(int page, int size) {
        List<Product> list = null;

        int start = (page - 1) * size;
        int end = start + size;

        try {
            list = redisTemplate.opsForList().range(JHS_KEY, start, end);
            if (CollectionUtil.isEmpty(list)) {
                /**
                 * 隐患2： 当缓存查询不到时，会直接打到MySQL
                 */
                // TODO 走 DB
            }
        } catch (Exception e) {
            log.error("查询不到缓存，错误是： {}", e);
            e.printStackTrace();
            // TODO 走 DB 查询
        }

        return list;
    }

    public List<Product> findAB(int page, int size) {
        List<Product> list = null;

        int start = (page - 1) * size;
        int end = start + size;

        try {
            // 查询： 先查询主缓存A
            list = redisTemplate.opsForList().range(JHS_KEY_A, start, end);

            if (CollectionUtil.isEmpty(list)) {
                log.info("=======主缓存A已经失效了， 记得人工修补， B缓存自动延后10s");

                list = redisTemplate.opsForList().range(JHS_KEY_B, start, end);
                if (CollectionUtil.isEmpty(list)) {

                    // TODO 走 DB
                }
                log.info("查询结果是：{}", list);
            }
        } catch (Exception e) {
            //这里的异常，一般是redis瘫痪 ，或 redis网络timeout
            log.error("查询不到缓存，错误是： {}", e);
            e.printStackTrace();
            // TODO 走 DB 查询
        }

        return list;
    }
}
