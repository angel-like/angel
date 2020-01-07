package com.xmsy.server.zxyy.manager.modules.manager.userrecommendationgrade.service;

import com.baomidou.mybatisplus.service.IService;
import com.xmsy.server.zxyy.manager.modules.manager.userrecommendationgrade.entity.UserRecommendationGradeEntity;

/**
 * 用户推荐等级
 *
 * @author aleng
 * @email xxxxx
 * @date 2019-06-06 15:36:01
 */
public interface UserRecommendationGradeService extends IService<UserRecommendationGradeEntity> {

	/**
	 * 通过打码量返回对应等级
	 * 
	 * @param totalBet
	 * @return
	 */
	String getName(Long totalBet);

}
