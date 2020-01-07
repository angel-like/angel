package com.xmsy.server.zxyy.calculate.modules.manager.userrecommendationrecord.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xmsy.server.zxyy.calculate.modules.manager.userrecommendationrecord.entity.UserRecommendationRecordEntity;

/**
 * 用户推荐记录表
 * 
 * @author xiaoliu
 * @email xxxxx
 * @date 2018-12-26 19:15:51
 */
@Mapper
public interface UserRecommendationRecordDao extends BaseMapper<UserRecommendationRecordEntity> {
	Integer updateRecommendationRecordAmount(UserRecommendationRecordEntity entity);
	Integer updateRecommendationRecordAmountForZero(@Param("userId")Long userId);
}
