package com.xmsy.server.zxyy.webhome.modules.manager.usertransactionrecord.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.xmsy.server.zxyy.webhome.modules.manager.usertransactionrecord.entity.UserTransactionRecordEntity;

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

}
