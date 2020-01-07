package com.xmsy.server.zxyy.webhome.modules.manager.userbetrecord.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xmsy.server.zxyy.webhome.modules.manager.userbetrecord.entity.UserBetRecordEntity;

/**
 * 用户每日有效下注
 * 
 * @author xiaoliu
 * @email xxxxx
 * @date 2019-06-08 10:51:52
 */
@Mapper
public interface UserBetRecordDao extends BaseMapper<UserBetRecordEntity> {

	UserBetRecordEntity selectEntity(@Param("userId") Long userId);
	UserBetRecordEntity    selectBetCoins(@Param("userId") Long userId);

	// 根据Id修改有效打码
	boolean updateById(@Param("userId") Long userId, @Param("betCoins") Long betCoins,
			@Param("queryDate") String queryDate);

}
