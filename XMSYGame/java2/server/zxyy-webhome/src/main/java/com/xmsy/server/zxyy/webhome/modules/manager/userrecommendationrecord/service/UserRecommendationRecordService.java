package com.xmsy.server.zxyy.webhome.modules.manager.userrecommendationrecord.service;

import java.util.Map;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.xmsy.common.define.page.PageParam;
import com.xmsy.server.zxyy.webhome.common.utils.PageUtils;
import com.xmsy.server.zxyy.webhome.modules.manager.userrecommendationrecord.entity.UserRecommendationRecordEntity;
import com.xmsy.server.zxyy.webhome.modules.web.user.result.UserRecommendationRecordResult;

/**
 * 用户推荐记录表
 *
 * @author aleng
 * @email xxxxx
 * @date 2018-12-26 19:15:51
 */
public interface UserRecommendationRecordService extends IService<UserRecommendationRecordEntity> {

	PageUtils queryPage(Map<String, Object> params);

	/**
	 * 新增推荐记录
	 * 
	 * @param userId(被邀请人id)
	 * @param account(被邀请账号)
	 * @param recommendationCode(邀请码)
	 * @param superiorsId(推荐人Id)
	 * @param superiorsAccount(推荐人账号)
	 * @param amount(收益)
	 * @return
	 */
	UserRecommendationRecordEntity insertRecord(Long userId, String account, String recommendationCode,
			Long superiorsId, String superiorsAccount);

	/**
	 * 获取用户推荐记录
	 * 
	 * @param userId
	 * @return
	 */
	Page<UserRecommendationRecordResult> selectRecommendationListForUserId(Long userId, PageParam pageParam,
			String account);

	/**
	 * 修改推荐金额
	 * 
	 * @param userId
	 * @return
	 */
	void updateRecordAmount(UserRecommendationRecordEntity entity);
}
