package com.xmsy.server.zxyy.webhome.modules.manager.signrewardcontinuouseveryday.dao;

import com.xmsy.server.zxyy.webhome.modules.app.user.param.SignSignDetailEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.signrewardcontinuouseveryday.entity.SignRewardContinuousEveryDayEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.List;

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
	
	List<SignSignDetailEntity> getSignDetails(@Param("userId") Long userId,@Param("vipId") Long vipId,@Param("startDate") String startDate,@Param("endDate") String endDate) ;
	
	List<SignRewardContinuousEveryDayEntity> selectSignIn(@Param("vipId") Long vipId);
	
	SignRewardContinuousEveryDayEntity selectByVipId(@Param("dayNum") Integer dayNum,@Param("vipId") Long vipId);
	
}
