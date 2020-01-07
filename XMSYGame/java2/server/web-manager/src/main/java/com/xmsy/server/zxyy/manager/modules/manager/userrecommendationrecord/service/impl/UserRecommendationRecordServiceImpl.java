package com.xmsy.server.zxyy.manager.modules.manager.userrecommendationrecord.service.impl;

import java.math.BigDecimal;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xmsy.common.define.page.PageParam;
import com.xmsy.server.zxyy.manager.common.utils.PageUtils;
import com.xmsy.server.zxyy.manager.common.utils.Query;
import com.xmsy.server.zxyy.manager.modules.manager.userrecommendationrecord.dao.UserRecommendationRecordDao;
import com.xmsy.server.zxyy.manager.modules.manager.userrecommendationrecord.entity.UserRecommendationRecordEntity;
import com.xmsy.server.zxyy.manager.modules.manager.userrecommendationrecord.service.UserRecommendationRecordService;
import com.xmsy.server.zxyy.manager.modules.web.user.result.UserRecommendationRecordResult;

@Transactional
@Service("userRecommendationService")
public class UserRecommendationRecordServiceImpl
		extends ServiceImpl<UserRecommendationRecordDao, UserRecommendationRecordEntity>
		implements UserRecommendationRecordService {

	@Override
	public PageUtils queryPage(Map<String, Object> params) {
		Page<UserRecommendationRecordEntity> page = this.selectPage(
				new Query<UserRecommendationRecordEntity>(params).getPage(),
				new EntityWrapper<UserRecommendationRecordEntity>());

		return new PageUtils(page);
	}

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
		baseMapper.insertGetId(entity);
		return entity;
	}

	/**
	 * 获取用户下级推荐详情
	 */
	@Override
	public Page<UserRecommendationRecordResult> selectRecommendationListForUserId(Long userId, PageParam pageParam,
			String account) {
		Page<UserRecommendationRecordResult> page = new Page<>(pageParam.getPage(), pageParam.getLimit());// 当前页，总条数 构造
																											// page 对象
		return page.setRecords(baseMapper.selectRecommendationListForUserId(page, userId, account));
	}

	@Override
	public void updateRecordAmount(UserRecommendationRecordEntity entity) {
		baseMapper.updateRecordAmount(entity);
	}

}
