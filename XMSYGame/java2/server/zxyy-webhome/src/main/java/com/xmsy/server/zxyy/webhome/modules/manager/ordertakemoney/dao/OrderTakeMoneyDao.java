package com.xmsy.server.zxyy.webhome.modules.manager.ordertakemoney.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.xmsy.server.zxyy.webhome.modules.app.take.result.OrderCommissionTakeMoneyResult;
import com.xmsy.server.zxyy.webhome.modules.app.take.result.OrderTakeMoneyResult;
import com.xmsy.server.zxyy.webhome.modules.manager.ordertakemoney.entity.OrderTakeMoneyEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.ordertakemoney.entity.OrderTakeMoneyStatistics;
import com.xmsy.server.zxyy.webhome.modules.manager.statistics.entity.TakeMoneyReport;
import com.xmsy.server.zxyy.webhome.modules.manager.statistics.entity.TakeMoneyTableReport;

/**
 * 取款记录表
 * 
 * @author aleng
 * @email xxxxx
 * @date 2019-01-28 15:26:30
 */
@Mapper
public interface OrderTakeMoneyDao extends BaseMapper<OrderTakeMoneyEntity> {
	// 充值报表
	TakeMoneyReport getTakeMoneyReport(@Param("startTime") String startTime, @Param("endTime") String endTime);

	// 获取取款记录
	List<OrderTakeMoneyResult> getTakeMoneyRecord(Pagination page, @Param("userId") Long userId,
			@Param("startTime") String startTime, @Param("endTime") String endTime);

	// 取款表格报表
	List<TakeMoneyTableReport> getTakeMoneyTableReport(@Param("startTime") String startTime,
			@Param("endTime") String endTime);

	/**
	 * 根据会员id统计总的取款金额 取款次数
	 * 
	 * @param userId
	 * @return
	 */
	OrderTakeMoneyStatistics orderTakeStatistics(@Param("userId") Long userId);

	/**
	 * 根据会员id统计总的取款金额 取款次数
	 * 
	 * @param userId
	 * @return
	 */
	OrderTakeMoneyEntity getLastTakeMoney(@Param("userId") Long userId);

	boolean updateAll(@Param("entity") OrderTakeMoneyEntity orderTakeMoneyEntity);

	List<OrderCommissionTakeMoneyResult> commissionTakeMoneyRecord(Pagination page, @Param("userId") Long userId,
			@Param("startTime") String startTime, @Param("endTime") String endTime);
}
