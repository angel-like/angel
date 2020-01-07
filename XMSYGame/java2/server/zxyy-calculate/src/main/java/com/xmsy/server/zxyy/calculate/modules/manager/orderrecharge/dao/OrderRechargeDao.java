package com.xmsy.server.zxyy.calculate.modules.manager.orderrecharge.dao;

import java.math.BigDecimal;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xmsy.server.zxyy.calculate.modules.manager.orderrecharge.entity.OrderRechargeEntity;

/**
 * 充值订单表
 *
 * @author aleng
 * @email xxxxx
 * @date 2019-01-29 15:55:35
 */
@Mapper
public interface OrderRechargeDao extends BaseMapper<OrderRechargeEntity> {

	// 更新充值订单
	Integer updateOrderRecharge(@Param("merchantOrderNo") String merchantOrderNo, @Param("orderNo") String orderNo,
			@Param("userNeedBet") BigDecimal userNeedBet, @Param("rechargeTime") String rechargeTime);
	Integer updateOrderRechargeByOrderNo(@Param("merchantOrderNo") String merchantOrderNo, @Param("orderNo") String orderNo,
								@Param("userNeedBet") BigDecimal userNeedBet, @Param("rechargeTime") String rechargeTime,@Param("discountAmount") BigDecimal discountAmount,@Param("fristrecharge") Boolean fristrecharge );

	Long sumRechargeAmountByUserId(@Param("userId") Long userId);
	/**
	 * 通过会员id +时间范围 查询用户充值总额    =》天降财神红包使用
	 * @param userId
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	Long getRechargeAmount(@Param("userId") Long userId, @Param("startTime") String startTime,
			@Param("endTime") String endTime);
}
