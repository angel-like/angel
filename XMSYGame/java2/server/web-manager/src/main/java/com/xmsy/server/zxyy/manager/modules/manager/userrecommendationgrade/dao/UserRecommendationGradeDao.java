package com.xmsy.server.zxyy.manager.modules.manager.userrecommendationgrade.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xmsy.server.zxyy.manager.modules.manager.userrecommendationgrade.entity.UserRecommendationGradeEntity;

/**
 * 用户推荐等级
 * 
 * @author xiaoliu
 * @email xxxxx
 * @date 2019-06-19
 */
@Mapper
public interface UserRecommendationGradeDao extends BaseMapper<UserRecommendationGradeEntity> {

	/**
	 * 根据用户打码获取等级名
	 * 
	 * @param totalBet
	 * @return
	 */
	String getName(@Param("totalBet") Long totalBet);

}
