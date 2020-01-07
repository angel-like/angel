package com.xmsy.server.zxyy.webhome.modules.manager.ordertakemoney.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.xmsy.common.define.page.PageParam;
import com.xmsy.server.zxyy.webhome.modules.app.take.result.OrderCommissionTakeMoneyResult;
import com.xmsy.server.zxyy.webhome.modules.app.take.result.OrderTakeMoneyResult;
import com.xmsy.server.zxyy.webhome.modules.manager.ordertakemoney.entity.OrderTakeMoneyEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.ordertakemoney.entity.OrderTakeMoneyLockingEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.statistics.entity.TakeMoneyTableReport;
import com.xmsy.server.zxyy.webhome.modules.manager.user.entity.UserEntity;

/**
 * 取款记录表
 *
 * @author aleng
 * @email xxxxx
 * @date 2019-01-28 15:26:30
 */
public interface OrderTakeMoneyService extends IService<OrderTakeMoneyEntity> {

	/**
	 * 新增现金取款订单
	 * 
	 * @param takeMoneyRecordEntity
	 * @param UserStatisticsEntity
	 * @throws Exception
	 */
	UserEntity saveTakeMoneyOrder(OrderTakeMoneyEntity takeMoneyRecordEntity, UserEntity user) throws Exception;

	/**
	 * 新增佣金现金取款订单
	 * 
	 * @param takeMoneyRecordEntity
	 * @param UserStatisticsEntity
	 */
	void saveTakeCommissionOrder(OrderTakeMoneyEntity orderTakeMoneyEntity, UserEntity user);

	// 提款订单确认
	void orderTakeMoneyConfirm(OrderTakeMoneyEntity orderTakeMoneyEntity) throws Exception;

	// 提款订单取消
	void orderTakeMoneyCancel(OrderTakeMoneyEntity orderTakeMoneyEntity) throws Exception;

	// 提款订单取消下注
	void orderTakeMoneyBetCancel(OrderTakeMoneyEntity orderTakeMoneyEntity) throws Exception;

	// 查询用户取款记录
	Page<OrderTakeMoneyResult> getTakeMoneyRecord(PageParam pageParam, Long userId, String startTime, String endTime);

	// 取款表格报表
	List<TakeMoneyTableReport> getTakeMoneyTableReport(@Param("startTime") String startTime,
			@Param("endTime") String endTime);

	boolean updateAll(OrderTakeMoneyEntity orderTakeMoneyEntity);

	int batchLocking(OrderTakeMoneyLockingEntity orderTakeMoneyEntity);

	Page<OrderCommissionTakeMoneyResult> commissionTakeMoneyRecord(PageParam pageParam, Long userId, String startTime,
			String endTime);
}
