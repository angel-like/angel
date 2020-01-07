package com.xmsy.server.zxyy.calculate.modules.manager.userrecommendationrecord.service.impl;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xmsy.server.zxyy.calculate.modules.manager.userrecommendationrecord.dao.UserRecommendationRecordDao;
import com.xmsy.server.zxyy.calculate.modules.manager.userrecommendationrecord.entity.UserRecommendationRecordEntity;
import com.xmsy.server.zxyy.calculate.modules.manager.userrecommendationrecord.service.UserRecommendationRecordService;

@Transactional
@Service("userRecommendationRecordService")
public class UserRecommendationRecordServiceImpl
		extends ServiceImpl<UserRecommendationRecordDao, UserRecommendationRecordEntity>
		implements UserRecommendationRecordService {

	/**
	 * 新增推荐记录
	 */
	@Override
	public UserRecommendationRecordEntity insertRecord(Long userId, String account, String recommendationCode,
			Long superiorsId, String superiorsAccount) {
		UserRecommendationRecordEntity entity = new UserRecommendationRecordEntity();
		entity.setUserId(userId);
		entity.setUserAccount(account);
		entity.setPromoterId(superiorsId);
		entity.setPromoterAccount(superiorsAccount);
		entity.setRecommendationCode(recommendationCode);
		entity.setPromotingProfit(BigDecimal.ZERO);
		baseMapper.insert(entity);
		return entity;
	}
}
