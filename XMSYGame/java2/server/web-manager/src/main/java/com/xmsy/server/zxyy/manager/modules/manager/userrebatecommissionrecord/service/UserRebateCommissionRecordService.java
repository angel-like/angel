package com.xmsy.server.zxyy.manager.modules.manager.userrebatecommissionrecord.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.xmsy.common.define.page.PageParam;
import com.xmsy.server.zxyy.manager.modules.manager.userrebatecommissionrecord.entity.UserRebateCommissionDateDetailsEntity;
import com.xmsy.server.zxyy.manager.modules.manager.userrebatecommissionrecord.entity.UserRebateCommissionDetailsEntity;
import com.xmsy.server.zxyy.manager.modules.manager.userrebatecommissionrecord.entity.UserRebateCommissionRecordEntity;
import com.xmsy.server.zxyy.manager.modules.manager.userrebatecommissionrecord.entity.UserRebateCommissionSumEntity;

import java.util.List;
import java.util.Map;

/**
 * 用户佣金返利记录
 *
 * @author aleng
 * @email xxxxx
 * @date 2019-06-12 14:38:44
 */
public interface UserRebateCommissionRecordService extends IService<UserRebateCommissionRecordEntity> {
	List<Map<String, Object>> getList();
	Page<UserRebateCommissionDetailsEntity> getDetailsList(UserRebateCommissionDetailsEntity userrebatecommissionrecord,
			PageParam pageParam);

	Page<UserRebateCommissionDateDetailsEntity> dateList(
			UserRebateCommissionDateDetailsEntity userRebateCommissionDateDetailsEntity, PageParam pageParam);

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
	UserRebateCommissionSumEntity selectSumCommission(Long userId, String queryDate, String startDate, String endDate);

}
