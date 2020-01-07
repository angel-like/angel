package com.xmsy.server.zxyy.manager.modules.manager.userrebatecommissionrecord.dao;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.xmsy.server.zxyy.manager.modules.manager.userrebatecommissionrecord.entity.UserRebateCommissionDateDetailsEntity;
import com.xmsy.server.zxyy.manager.modules.manager.userrebatecommissionrecord.entity.UserRebateCommissionDetailsEntity;
import com.xmsy.server.zxyy.manager.modules.manager.userrebatecommissionrecord.entity.UserRebateCommissionRecordEntity;
import com.xmsy.server.zxyy.manager.modules.manager.userrebatecommissionrecord.entity.UserRebateCommissionSumEntity;

/**
 * 用户佣金返利记录
 *
 * @author aleng
 * @email xxxxx
 * @date 2019-06-12 14:38:44
 */
@Mapper
public interface UserRebateCommissionRecordDao extends BaseMapper<UserRebateCommissionRecordEntity> {
	List<Map<String, Object>> getList();
	List<UserRebateCommissionDetailsEntity> getDetailsList(Pagination page,
			@Param("entity") UserRebateCommissionDetailsEntity userrebatecommissionrecord);
	List<UserRebateCommissionDetailsEntity> getDetailsListForSimplify(Pagination page,
			@Param("entity") UserRebateCommissionDetailsEntity userrebatecommissionrecord);


	List<Map<String, Object>> findUserTotalBetCoin(@Param("idList") Collection<Long> idList);

	List<UserRebateCommissionDateDetailsEntity> dateList(Page<UserRebateCommissionDateDetailsEntity> page,
			@Param("entity") UserRebateCommissionDateDetailsEntity userRebateCommissionDateDetailsEntity);

	/**
	 * 获取用户佣金详情
	 *
	 * @param userId
	 *            用户ID
	 * @param queryDate
	 *            指定日期
	 * @param startTime
	 *            区间开始时间
	 * @param endTime
	 *            区间结束时间
	 * @return dateCommission 指定日期的佣金总额,intervalCommission 区间日期的佣金总额,totalCommission
	 *         佣金总额
	 *
	 */
	UserRebateCommissionSumEntity selectSumCommission(@Param("userId") Long userId,
			@Param("queryDate") String queryDate, @Param("startDate") String startDate,
			@Param("endDate") String endDate);

}
