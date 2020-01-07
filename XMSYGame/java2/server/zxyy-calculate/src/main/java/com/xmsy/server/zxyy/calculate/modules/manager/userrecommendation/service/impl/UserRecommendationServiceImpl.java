package com.xmsy.server.zxyy.calculate.modules.manager.userrecommendation.service.impl;

import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xmsy.server.zxyy.calculate.modules.manager.userrecommendation.dao.UserRecommendationDao;
import com.xmsy.server.zxyy.calculate.modules.manager.userrecommendation.entity.UserRecommendationEntity;
import com.xmsy.server.zxyy.calculate.modules.manager.userrecommendation.service.UserRecommendationService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Transactional
@Service("userRecommendationService")
public class UserRecommendationServiceImpl extends ServiceImpl<UserRecommendationDao, UserRecommendationEntity>
		implements UserRecommendationService {

	/**
	 * 新增用户推广码
	 */
	@Override
	public void insertRecommendationCode(Long userId, String account, String recommendationCode,
			Long agentHierarchyId) {
		UserRecommendationEntity recommendationCodeEntity = new UserRecommendationEntity();
		recommendationCodeEntity.setRecommendationCode(recommendationCode);
		recommendationCodeEntity.setUserId(userId);
		recommendationCodeEntity.setUserAccount(account);
		recommendationCodeEntity.setAgentHierarchyId(agentHierarchyId);
		log.info("[insertRecommendationCode] recommendationCodeEntity {}", recommendationCodeEntity);
		baseMapper.insert(recommendationCodeEntity);
	}

	/**
	 * 根据Id，修改邀请总人数
	 */
	@Override
	public void updateSuperiors(Long id) {
		baseMapper.updateSuperiors(id);
	}
	
	/**
	 * 获取对应的代理商层级信息
	 */
	@Override
	public Map<String, Object> getAgentHierarchyByUserId(Long userId) {
		return baseMapper.getAgentHierarchyByUserId(userId);
	}
}
