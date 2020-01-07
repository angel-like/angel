package com.xmsy.server.zxyy.webhome.modules.manager.userrecommendationrecord.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.xmsy.server.zxyy.webhome.modules.manager.userrecommendationrecord.entity.UserRecommendationRecordEntity;
import com.xmsy.server.zxyy.webhome.modules.web.user.result.UserRecommendationRecordResult;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 用户推荐记录表
 * 
 * @author xiaoliu
 * @email xxxxx
 * @date 2018-12-26 19:15:51
 */
@Mapper
public interface UserRecommendationRecordDao extends BaseMapper<UserRecommendationRecordEntity> {

	// 新增用户推荐记录并返回ID
	void insertGetId(UserRecommendationRecordEntity entity);

	// 通过用户id获取下级推荐详情
	List<UserRecommendationRecordResult> selectRecommendationListForUserId(Pagination page,
			@Param("userId") Long userId, @Param("account") String account);

	// 根据用户Id修改推荐记录
	void updateRecordAmount(UserRecommendationRecordEntity entity);
	
	Integer updateRecommendationRecordAmount(UserRecommendationRecordEntity entity);
	Integer updateRecommendationRecordAmountForZero(@Param("userId")Long userId);
}
