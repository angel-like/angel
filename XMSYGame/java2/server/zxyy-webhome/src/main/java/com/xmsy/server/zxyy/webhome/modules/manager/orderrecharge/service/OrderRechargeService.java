package com.xmsy.server.zxyy.webhome.modules.manager.orderrecharge.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.xmsy.common.define.page.PageParam;
import com.xmsy.server.zxyy.webhome.modules.manager.orderrecharge.entity.OrderBankRechargeLockingEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.orderrecharge.entity.OrderRechargeEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.statistics.entity.RechargeReport;
import com.xmsy.server.zxyy.webhome.modules.manager.statistics.entity.RechargeTableReport;
import com.xmsy.server.zxyy.webhome.modules.manager.user.entity.UserEntity;
import com.xmsy.server.zxyy.webhome.modules.web.user.result.UserOrderRechargeExchangeParam;

/**
 * 充值订单表
 *
 * @author aleng
 * @email xxxxx
 * @date 2019-01-29 15:55:35
 */
public interface OrderRechargeService extends IService<OrderRechargeEntity> {

	// 第三方充值回调修改订单信息
	Integer updateOrderRecharge(String merchantOrderNo, String orderNo, Integer completed, Integer uncomfirmed,
			String rechargeTime);

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
	void orderRechargeConfirm(OrderRechargeEntity orderRecharge) throws Exception;

	// 充值报表
	RechargeReport getRechargeReport(String startTime, String endTime);

	// 充值表格报表
	List<RechargeTableReport> getRechargeTableReport(String startTime, String endTime);

	Page<UserOrderRechargeExchangeParam> rechargeExamineList(Long userId, PageParam pageParam, String startTime,
			String endTime);

	// 根据充值类型获取指定时间充值总额
	int sumAmountForDate(String date, Integer rechargeType);

	// 根据时间获取充值总额与充值总单数(成功)
	Map<String, Object> sumRechargeAmount(String startDate, String endDate, Integer rechargeType);

	// 修改
	boolean updateAll(OrderRechargeEntity orderRecharge);

	int batchLocking(OrderBankRechargeLockingEntity lockingEntity);

	Map<String, Object> countRechargePreferential(BigDecimal amount,boolean isFirst,Long hierarchyId) ;
	
	//查询当前充值金额
	Long selectAllAmount(Long userId);
	
}
