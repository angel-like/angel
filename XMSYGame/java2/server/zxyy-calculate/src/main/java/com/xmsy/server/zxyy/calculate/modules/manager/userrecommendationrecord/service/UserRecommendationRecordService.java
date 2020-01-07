package com.xmsy.server.zxyy.calculate.modules.manager.userrecommendationrecord.service;

import com.baomidou.mybatisplus.service.IService;
import com.xmsy.server.zxyy.calculate.modules.manager.userrecommendationrecord.entity.UserRecommendationRecordEntity;

/**
 * 用户推荐记录表
 *
 * @author aleng
 * @email xxxxx
 * @date 2018-12-26 19:15:51
 */
public interface UserRecommendationRecordService extends IService<UserRecommendationRecordEntity> {


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
}
