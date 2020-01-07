package com.xmsy.server.zxyy.webhome.modules.manager.userrecommendation.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xmsy.server.zxyy.webhome.modules.app.ranking.param.AppShareRankingParam;
import com.xmsy.server.zxyy.webhome.modules.app.user.param.AppRecommenderResultParam;
import com.xmsy.server.zxyy.webhome.modules.app.user.param.UserRecommenderResultParam;
import com.xmsy.server.zxyy.webhome.modules.manager.userrecommendation.entity.UserRecommendationEntity;

/**
 * 用户推荐码表
 * 
 * @author aleng
 * @email xxxxx
 * @date 2018-12-26 19:15:50
 */
@Mapper
public interface UserRecommendationDao extends BaseMapper<UserRecommendationEntity> {
	// 根据Id，修改邀请总人数
	void updateSuperiors(@Param("id") Long id);

	/**
	 * 查找没有邀请码的会员
	 * 
	 * @return
	 */
	List<Map<String, Object>> findNotRecommendationCodeUser();

	/**
	 * 物理删除
	 * 
	 * @param id
	 * @return
	 */
	Integer deletePhysicalById(@Param("id") Long id);

	/**
	 * 检查邀请码是否唯一
	 * 
	 * @param code
	 * @param id
	 * @return
	 */
	Integer checkCode(@Param("code") String code, @Param("id") Long id);

	/**
	 * 根据ID增加金币
	 * 
	 * @param entity
	 * @return
	 */
	Integer updateSuperiorsAmount(UserRecommendationEntity entity);

	/**
	 * 根据ID增加金币
	 * 
	 * @param entity
	 * @return
	 */
	Integer updateSuperiorsAmountForZero(@Param("userId") Long userId);

	/**
	 * 排行榜,前20
	 * 
	 * @param entity
	 * @return
	 */
	List<AppShareRankingParam> shareRanking();

	/**
	 * 通过用户id获取用户邀请码及代理信息
	 * 
	 * @param userId
	 * @return
	 */
	UserRecommenderResultParam selectUserRecommendation(@Param("userId") Long userId);

	// 获取用户代理信息
	AppRecommenderResultParam selectUserRecommendationInfo(@Param("userId") Long userId);
	/**
	 * 通过用户id 修改推荐人的推荐详情 
	 * @param userId
	 * @return
	 */
	Integer updateByUserId(@Param("userId")Long userId);
}
