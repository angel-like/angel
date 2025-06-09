package com.atguigu.springcloud.alibaba.service.impl;

import com.atguigu.springcloud.alibaba.dao.OrderDao;
import com.atguigu.springcloud.alibaba.domain.Order;
import com.atguigu.springcloud.alibaba.service.AccountService;
import com.atguigu.springcloud.alibaba.service.OrderService;
import com.atguigu.springcloud.alibaba.service.StorageService;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author lixiaolong
 * @date 2020-02-26 15:20
 */
@Service
@Slf4j
public class OrderServiceImpl implements OrderService {
    @Resource
    private OrderDao orderDao;
    @Resource
    private StorageService storageService;
    @Resource
    private AccountService accountService;

    /**
     * 创建订单->调用库存服务扣减库存->调用账户服务扣减账户余额->修改订单状态
     * 简单说：下订单->扣库存->减余额->改状态
     * 注释掉 @GlobalTransactional 的时候，需要注意下方这个方法里面手动模拟了延时，也需要注释掉
     * com.atguigu.springcloud.alibaba.service.impl.AccountServiceImpl#decrease(java.lang.Long, java.math.BigDecimal)
     */
    @Override
    @GlobalTransactional(name = "fsp-create-order", rollbackFor = Exception.class)
    public void create(Order order) {
        log.info("----->开始新建订单");
        //1 新建订单  (添加 @GlobalTransactional 后，还是会去数据库创建一条数据，回滚时会把对应数据删除)
        long id = orderDao.create(order);

        //2 扣减库存  (添加 @GlobalTransactional 后，还是会去数据库更新数据，回滚时会把对应更新撤销)
        log.info("----->订单微服务开始调用库存，做扣减Count");
        storageService.decrease(order.getProductId(), order.getCount());
        log.info("----->订单微服务开始调用库存，做扣减end");

        //3 扣减账户
        log.info("----->订单微服务开始调用账户，做扣减Money");
        accountService.decrease(order.getUserId(), order.getMoney());
        log.info("----->订单微服务开始调用账户，做扣减end");

        //4 修改订单状态，从零到1,1代表已经完成,这里应该是通过订单id去修改状态,新建订单里获取的订单id
        log.info("----->修改订单状态开始");
        //orderDao.update(order.getUserId(), 0);
        orderDao.updateById(order.getId(), 0);
        log.info("----->修改订单状态结束");

        log.info("----->下订单结束了，O(∩_∩)O哈哈~");

    }
}
