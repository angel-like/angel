package com.xmsy.server.zxyy.manager.modules.manager.userbalancerecord.dao;

import com.xmsy.server.zxyy.manager.modules.manager.userbalancerecord.entity.UserBalanceRecordEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 用户账户金额存取记录表
 * 
 * @author axiong
 * @email xxxxx
 * @date 2019-06-18 09:18:59
 */
@Mapper
public interface UserBalanceRecordDao extends BaseMapper<UserBalanceRecordEntity> {
	public List<UserBalanceRecordEntity> findPageList(@Param("userBalanceRecordEntity") UserBalanceRecordEntity userBalanceRecordEntity, Pagination page);
}
