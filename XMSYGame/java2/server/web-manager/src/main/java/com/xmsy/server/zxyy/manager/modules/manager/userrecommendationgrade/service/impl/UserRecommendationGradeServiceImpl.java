package com.xmsy.server.zxyy.manager.modules.manager.userrecommendationgrade.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xmsy.server.zxyy.manager.modules.manager.userrecommendationgrade.dao.UserRecommendationGradeDao;
import com.xmsy.server.zxyy.manager.modules.manager.userrecommendationgrade.entity.UserRecommendationGradeEntity;
import com.xmsy.server.zxyy.manager.modules.manager.userrecommendationgrade.service.UserRecommendationGradeService;

@Service("userRecommendationGradeService")
public class UserRecommendationGradeServiceImpl
		extends ServiceImpl<UserRecommendationGradeDao, UserRecommendationGradeEntity>
		implements UserRecommendationGradeService {

	@Override
	public String getName(Long totalBet) {
		// TODO Auto-generated method stub
		return baseMapper.getName(totalBet);
	}

}
