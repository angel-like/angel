package com.atguigu.springcloud.alibaba.service;


/**
 * @author lixiaolong
 */
public interface StorageService {
    /**
     * 扣减库存
     *
     * @param productId
     * @param count
     */
    void decrease(Long productId, Integer count);
}
