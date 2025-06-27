package com.ljx.redis.service;

import com.ljx.redis.entity.Customer;
import com.ljx.redis.mapper.CustomerMapper;
import com.ljx.redis.utils.CheckUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * ClassName: CustomerService
 * Package: com.ljx.redis.service
 * Description:
 *
 * @Author Xu, Luqin
 * @Create 2024/11/2 18:39
 * @Version 1.0
 */
@Service
@Slf4j
public class CustomerService {
    @Autowired
    private RedisTemplate redisTemplate;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    CustomerMapper customerMapper;
    @Autowired
    private CheckUtils checkUtils;
    private final static String CACHE_CUSTOMER_KEY = "customer:";
    private final static String WHITELIST_KEY = "whitelistCustomer";


    /**
     * 添加客户
     * 1.先添加到MySQL
     * 2.回写Redis
     *
     * @param customer
     */
    public void addCustomer(Customer customer) {
        int rows = customerMapper.insertSelective(customer);
        if (rows > 0) {

            log.info("在MySQL中插入数据： {}", customer);

            String key = CACHE_CUSTOMER_KEY + customer.getId();
            redisTemplate.opsForValue().set(key, customer);

            log.info("在Redis中回写数据：{} => {}", key, customer);
        }
    }

    /**
     * 根据id查询用户信息
     * 1.先查询Redis缓存
     *  2.如果没有查询到数据， 则查询MySQL数据库
     *    3.如果在MySQL查询到数据，则将查询到的数据回写都Redis缓存中
     * @param id
     * @return
     */
    public Customer findCustomerById(int id) {
        Customer customer = null;

        String key = CACHE_CUSTOMER_KEY + id;
        customer = (Customer) redisTemplate.opsForValue().get(key);

        if(customer == null){
            customer = customerMapper.selectByPrimaryKey(id);

            if (customer != null){
                redisTemplate.opsForValue().set(key, customer);
            }

        }
        return customer;
    }

    /**
     * 根据id查询用户信息, 加上布隆过滤器
     * 0.查询布隆过滤器是否存在，如果不存在，则返回空
     * 1.如果存在，先查询Redis缓存
     * 2.如果没有查询到数据， 则查询MySQL数据库
     * 3.如果在MySQL查询到数据，则将查询到的数据回写都Redis缓存中
     * @param id
     * @return
     */
    public Customer findCustomerByIdWithBloomFilter(int id) {
        Customer customer = null;
        String key = CACHE_CUSTOMER_KEY + id;

        if (!checkUtils.checkWithBloomFilter(WHITELIST_KEY, key)){
            log.info("白名单没有这个用户： {}", key);
            return null;
        }

        customer = (Customer) redisTemplate.opsForValue().get(key);

        if(customer == null){
            customer = customerMapper.selectByPrimaryKey(id);

            if (customer != null){
                redisTemplate.opsForValue().set(key, customer);
            }

        }
        return customer;
    }
}
