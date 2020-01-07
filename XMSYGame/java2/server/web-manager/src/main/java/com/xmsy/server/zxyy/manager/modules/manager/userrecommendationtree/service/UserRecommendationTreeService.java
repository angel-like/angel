package com.xmsy.server.zxyy.manager.modules.manager.userrecommendationtree.service;

import com.baomidou.mybatisplus.service.IService;
import com.xmsy.server.zxyy.manager.modules.manager.userrecommendationtree.entity.UserRecommendationTreeEntity;


/**
 * 用户推荐关系表
 *
 * @author aleng
 * @email xxxxx
 * @date 2019-06-05 15:35:08
 */
public interface UserRecommendationTreeService extends IService<UserRecommendationTreeEntity> {
	/**
	 * 新增代理关系树
	 * @param userId
	 * @param superiorsId
	 * @return
	 */
	boolean insertBatch(Long userId, Long superiorsId);
}

