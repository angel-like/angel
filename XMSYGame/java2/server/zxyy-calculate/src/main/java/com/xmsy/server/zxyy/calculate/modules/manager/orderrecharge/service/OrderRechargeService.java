package com.xmsy.server.zxyy.calculate.modules.manager.orderrecharge.service;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.service.IService;
import com.xmsy.server.zxyy.calculate.modules.manager.orderrecharge.entity.OrderRechargeEntity;
import com.xmsy.server.zxyy.calculate.modules.manager.user.entity.UserEntity;

/**
 * 充值订单表
 *
 * @author aleng
 * @email xxxxx
 * @date 2019-01-29 15:55:35
 */
public interface OrderRechargeService extends IService<OrderRechargeEntity> {

	// 第三方充值回调修改订单信息
	Integer updateOrderRecharge(String merchantOrderNo, String orderNo, BigDecimal userNeedBet, String rechargeTime);

	// 人工充值保存订单
	void saveAdministratorRecharge(OrderRechargeEntity orderRecharge, int transactionType, UserEntity user)
			throws Exception;

	/**
	 * 订单撤销
	 * 
	 * @param rechargeId
	 * @param ip
	 * @param sysUserId
	 * @param sysUserName
	 */
	void revokeRecharge(Long rechargeId);

	// 充值订单确认
	boolean orderRechargeConfirm(OrderRechargeEntity orderRecharge) throws Exception;
	/**
	 * 通过会员id +时间范围 查询用户充值总额    =》天降财神红包使用
	 * @param userId
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	Long getRechargeAmount(Long userId, String startTime, String endTime);
}
