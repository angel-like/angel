package com.atguigu.springcloud.alibaba.dao;

import com.atguigu.springcloud.alibaba.domain.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author lixiaolong
 * @date 2020-02-26 15:17
 */
@Mapper
public interface OrderDao {
    //1 新建订单
    long create(Order order);

    //2 修改订单状态，从零改为1    这里应该是通过订单id去修改状态
    void update(@Param("userId") Long userId, @Param("status") Integer status);

    //2 修改订单状态，从零改为1    这里应该是通过订单id去修改状态
    void updateById(@Param("id") Long Id, @Param("status") Integer status);
}