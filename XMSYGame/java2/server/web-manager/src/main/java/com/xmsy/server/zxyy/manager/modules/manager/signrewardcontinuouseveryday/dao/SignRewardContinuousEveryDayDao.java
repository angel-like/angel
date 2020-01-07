package com.xmsy.server.zxyy.manager.modules.manager.signrewardcontinuouseveryday.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.xmsy.server.zxyy.manager.modules.app.user.param.SignSignDetailEntity;
import com.xmsy.server.zxyy.manager.modules.manager.signrewardcontinuouseveryday.entity.SignRewardContinuousEveryDayEntity;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 每天签到奖励
 * 
 * @author aleng
 * @email xxxxx
 * @date 2019-05-13 15:50:02
 */
@Mapper
public interface SignRewardContinuousEveryDayDao extends BaseMapper<SignRewardContinuousEveryDayEntity> {
	
	List<SignSignDetailEntity> getSignDetails(@Param("userId") Long userId,@Param("startDate") String startDate,@Param("endDate") String endDate) ;
	
	/**
	 * 日常簽到页面分页查询
	 * 
	 * @param param
	 * @return
	 */
	List<Map<String, Object>> findSignRewardContinuousEveryDayPage(@Param("signRewardContinuousEveryDay") SignRewardContinuousEveryDayEntity signRewardContinuousEveryDay, Pagination page);
	
	
}
