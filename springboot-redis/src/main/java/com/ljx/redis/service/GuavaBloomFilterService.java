package com.ljx.redis.service;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * ClassName: GuavaBloomFilterService
 * Package: com.ljx.redis.service
 * Description:
 *
 * @Author Xu, Luqin
 * @Create 2024/11/2 20:59
 * @Version 1.0
 */
@Slf4j
@Service
public class GuavaBloomFilterService {
    private final static int _1W = 10000;
    private final static int SIZE = 100 * _1W;
    private final static int TEST_SIZE = 10 * _1W;
    private final static double fpp = 0.01;

    public void guavaBloomFilter() {
        BloomFilter<Integer> bloomFilter = BloomFilter.create(Funnels.integerFunnel(), SIZE, fpp);
        for (int i = 1; i <= SIZE; i++) {
            bloomFilter.put(i);
        }

        log.info("--------------------");

        List<Integer> list = new ArrayList<>(TEST_SIZE);
        int count = 0;
        for (int i = SIZE + 1; i <= SIZE + TEST_SIZE; i++) {
            if (bloomFilter.mightContain(i)) {
                count++;
                log.info("BloomFilter误判了第： {} 个", i);
            }
        }

        BigDecimal rate = new BigDecimal(count* 100 / TEST_SIZE );

        log.info("------- 一共误判了{}个", count);
        log.info("------- 概率是： {}%", rate);
    }
}
