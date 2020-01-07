package com.xmsy.server.zxyy.manager.modules.manager.usertransactionrecord.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.xmsy.server.zxyy.manager.modules.manager.user.param.UserParamFour;
import com.xmsy.server.zxyy.manager.modules.manager.user.param.UserReportParam;
import com.xmsy.server.zxyy.manager.modules.manager.usertransactionrecord.entity.UserTransactionRecordEntity;

/**
 * 资金交易明细
 *
 * @author lpp
 * @email xxxxx
 * @date 2019-01-17 16:48:50
 */
@Mapper
public interface UserTransactionRecordDao extends BaseMapper<UserTransactionRecordEntity> {

	List<UserTransactionRecordEntity> getTransactionRecords(Pagination page,
			@Param("record") UserTransactionRecordEntity record, @Param("startTime") String startTime,
			@Param("endTime") String endTime, @Param("type") String type);

	/**
	 * 获取金额总数（每天，不同类型）
	 */
	List<UserReportParam> getAmountSum(@Param("user") UserParamFour user);
	Map<String,Object> getDisAmount(@Param("startDate")String startDate,@Param("endDate") String endDate);
}
