package com.xmsy.server.zxyy.webhome.modules.manager.userrecommendationtree.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;


import com.xmsy.server.zxyy.webhome.modules.manager.userrecommendationtree.dao.UserRecommendationTreeDao;
import com.xmsy.server.zxyy.webhome.modules.manager.userrecommendationtree.entity.UserRecommendationTreeEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.userrecommendationtree.service.UserRecommendationTreeService;


@Service("userRecommendationTreeService")
public class UserRecommendationTreeServiceImpl extends ServiceImpl<UserRecommendationTreeDao, UserRecommendationTreeEntity> implements UserRecommendationTreeService {

	@Override
	public boolean insertBatch(Long userId, Long superiorsId) {
		// 新增用户上级关系树
		List<UserRecommendationTreeEntity> list = new ArrayList<UserRecommendationTreeEntity>();
		// 用户与上级关系
		list.add(new UserRecommendationTreeEntity()
				.setParantUserId(superiorsId)
				.setUserId(userId)
				.setUserParantDistance(1));
		// 获取到上级所有关系,不包含自己本身
		List<UserRecommendationTreeEntity> parentList = selectList(new EntityWrapper<UserRecommendationTreeEntity>(
				new UserRecommendationTreeEntity().setUserId(superiorsId)).ne("user_parant_distance", 0));
		if (CollectionUtils.isNotEmpty(parentList)) {
			for (UserRecommendationTreeEntity treeEntity : parentList) {
				UserRecommendationTreeEntity tree = new UserRecommendationTreeEntity();
				tree.setUserId(userId);
				tree.setParantUserId(treeEntity.getParantUserId());
				tree.setUserParantDistance(treeEntity.getUserParantDistance() + 1);// 当前用户与他的上级的上级关系需要增加1
				list.add(tree);
			}
		}
		return insertBatch(list);
	}


}
