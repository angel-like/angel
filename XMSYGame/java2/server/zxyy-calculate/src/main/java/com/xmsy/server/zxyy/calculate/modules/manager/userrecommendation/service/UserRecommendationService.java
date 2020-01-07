package com.xmsy.server.zxyy.calculate.modules.manager.userrecommendation.service;

import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.service.IService;
import com.xmsy.server.zxyy.calculate.modules.manager.userrecommendation.entity.UserRecommendationEntity;

/**
 * 用户推荐码表
 *
 * @author aleng
 * @email xxxxx
 * @date 2018-12-26 19:15:50
 */
public interface UserRecommendationService extends IService<UserRecommendationEntity> {

	// 为用户新增推广码
	void insertRecommendationCode(Long userId, String account, String recommendationCode, Long agentHierarchyId);
	
	// 根据Id修改邀请总人数(+1)
	void updateSuperiors(Long id);
	
	/**
	 * 获取对应的代理商层级信息
	 * @param userId
	 * @return
	 */
	Map<String, Object> getAgentHierarchyByUserId(@Param("userId")Long userId);
}
