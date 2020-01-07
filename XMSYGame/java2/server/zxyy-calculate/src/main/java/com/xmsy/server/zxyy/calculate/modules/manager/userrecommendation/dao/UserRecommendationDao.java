package com.xmsy.server.zxyy.calculate.modules.manager.userrecommendation.dao;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xmsy.server.zxyy.calculate.modules.manager.userrecommendation.entity.UserRecommendationEntity;

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
	void updateSuperiors(@Param("id")Long id);
	/**
	 * 根据ID增加金币
	 * @param entity
	 * @return
	 */
	void updateSuperiorsAmount(UserRecommendationEntity entity);
	
	/**
	 * 获取对应的代理商层级信息
	 * @param userId
	 * @return
	 */
	Map<String, Object> getAgentHierarchyByUserId(@Param("userId")Long userId);
	
}
