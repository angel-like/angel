package com.xmsy.server.zxyy.manager.modules.manager.orderrecharge.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.xmsy.common.bean.message.RechargeRebateMessage;
import com.xmsy.common.define.page.PageParam;
import com.xmsy.server.zxyy.manager.modules.manager.orderrecharge.entity.OrderBankRechargeLockingEntity;
import com.xmsy.server.zxyy.manager.modules.manager.orderrecharge.entity.OrderRechargeEntity;
import com.xmsy.server.zxyy.manager.modules.manager.orderrecharge.entity.OrderRechargeStatisticsThree;
import com.xmsy.server.zxyy.manager.modules.manager.orderrecharge.entity.OrderRechargeStatisticsTwo;
import com.xmsy.server.zxyy.manager.modules.manager.statistics.entity.RechargeReport;
import com.xmsy.server.zxyy.manager.modules.manager.statistics.entity.RechargeTableReport;
import com.xmsy.server.zxyy.manager.modules.manager.user.entity.UserEntity;
import com.xmsy.server.zxyy.manager.modules.manager.user.param.UserParamFour;
import com.xmsy.server.zxyy.manager.modules.web.user.result.UserOrderRechargeExchangeParam;

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
	// 人工充值保存订单
	void saveAdministratorRechargeV2(OrderRechargeEntity orderRecharge, int transactionType, UserEntity user,BigDecimal discountMultiple
			,BigDecimal rechargeMultiple)
			throws Exception;
	// 人工充值优惠保存订单
	void saveAdministratorRechargeV3(OrderRechargeEntity orderRecharge, int transactionType, UserEntity user,BigDecimal discountMultiple
			,BigDecimal rechargeMultiple,Integer type )
			throws Exception;
	/**
	 * 查看个人存款记录
	 * @param user
	 * @return
	 */
	List<OrderRechargeEntity> depositRecord(UserParamFour user);

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
	RechargeRebateMessage orderRechargeConfirm(OrderRechargeEntity orderRecharge) throws Exception;

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
	/**
	 * 计算层级优惠
	 * @param amount
	 * @param isFirst
	 * @param hierarchyId
	 * @return
	 */
	Map<String, Object> countRechargePreferential(BigDecimal amount,boolean isFirst,Long hierarchyId) ;
	/**
	 * 计算层级优惠和vip等级优惠
	 * @param userId
	 * @param amount
	 * @param isFirst
	 * @param hierarchyId
	 * @return
	 */
	Map<String, Object> countRechargePreferential(String account,BigDecimal amount,boolean isFirst,Long hierarchyId) ;

	/**
	 * 获取最新一条取款中的金额和时间
	 * @param userId
	 * @return
	 */
	OrderRechargeStatisticsThree getLastRechargeAmountByUserId(Long userId);

	/**
	 * 根据会员id统计总的取款金额 取款次数等
	 * @param userI
	 * @return
	 */
	OrderRechargeStatisticsTwo orderRechargeNum(Long userId);
	/**
	 * 为了获取csv下载数据而进行的查询
	 * @param orderRecharge
	 * @return
	 */
	List<Map<String,Object>> selectListOrderRecharge(@Param("orderRecharge")OrderRechargeEntity orderRecharge);

}
