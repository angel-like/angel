package com.xmsy.server.zxyy.manager.modules.manager.usertransactionrecord.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.xmsy.common.define.page.PageParam;
import com.xmsy.server.zxyy.manager.common.utils.PageUtils;
import com.xmsy.server.zxyy.manager.modules.manager.user.param.UserParamFour;
import com.xmsy.server.zxyy.manager.modules.manager.user.param.UserReportParam;
import com.xmsy.server.zxyy.manager.modules.manager.usertransactionrecord.entity.UserTransactionRecordEntity;

/**
 * 资金交易明细
 *
 * @author aleng
 * @email xxxxx
 * @date 2018-12-27 17:22:43
 */
public interface UserTransactionRecordService extends IService<UserTransactionRecordEntity> {

	PageUtils queryPage(Map<String, Object> params);

	// 新增交易记录
	void insertRecord(Long userId, String account, BigDecimal amount, Integer type,Integer detailType, Long businessId);

	/**
	 *
	 * .获取交易记录
	 *
	 * @param record
	 * @param pageParam
	 * @param startTime
	 * @param endTime
	 * @param type
	 * @return
	 */
	Page<UserTransactionRecordEntity> getTransactionRecords(UserTransactionRecordEntity record, PageParam pageParam, String startTime,
			String endTime,String type);

	/**
	 * 获取金额总数
	 * @param user
	 * @return
	 */
	List<UserReportParam> getAmountSum(UserParamFour user);
	Map<String,Object> getDisAmount(String startDate, String endDate);
	}
