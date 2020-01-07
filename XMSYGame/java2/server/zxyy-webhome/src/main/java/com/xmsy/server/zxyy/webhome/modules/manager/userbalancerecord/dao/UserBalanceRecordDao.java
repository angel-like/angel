package com.xmsy.server.zxyy.webhome.modules.manager.userbalancerecord.dao;

import com.xmsy.server.zxyy.webhome.modules.manager.userbalancerecord.entity.UserBalanceRecordEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;

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
	/**
	 * 通过用户id查询 用户账户金额存取记录表		新版本 + 理财id的条件
	 * 		条件是  1. 类型：存入
	 * 			 2.是否生效：未生效的  
	 * 			 3.money>takeMoney
	 * @param userId
	 * @In Dao
	 */
	 List<UserBalanceRecordEntity> getListUserBalanceRecord(@Param("userBalanceRecord") UserBalanceRecordEntity userBalanceRecord);
}
