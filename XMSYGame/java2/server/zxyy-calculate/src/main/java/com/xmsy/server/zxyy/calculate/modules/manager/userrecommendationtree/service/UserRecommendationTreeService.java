package com.xmsy.server.zxyy.calculate.modules.manager.userrecommendationtree.service;

import com.baomidou.mybatisplus.service.IService;
import com.xmsy.server.zxyy.calculate.modules.manager.userrecommendationtree.entity.UserRecommendationTreeEntity;

/**
 * 用户推荐关系表
 *
 * @author aleng
 * @email xxxxx
 * @date 2019-06-05 15:35:08
 */
public interface UserRecommendationTreeService extends IService<UserRecommendationTreeEntity> {

	boolean insertBatch(Long userId, Long superiorsId);

}
