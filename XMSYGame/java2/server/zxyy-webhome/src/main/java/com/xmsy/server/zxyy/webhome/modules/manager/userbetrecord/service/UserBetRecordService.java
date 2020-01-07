package com.xmsy.server.zxyy.webhome.modules.manager.userbetrecord.service;

import com.baomidou.mybatisplus.service.IService;
import com.xmsy.server.zxyy.webhome.modules.manager.userbetrecord.entity.UserBetRecordEntity;

/**
 * 用户每日有效下注
 *
 * @author aleng
 * @email xxxxx
 * @date 2019-06-08 10:51:52
 */
public interface UserBetRecordService extends IService<UserBetRecordEntity> {

	void updateUserEveryDateBet(Long userId, String userAccount, Long betCoins, String queryDate,boolean isGmUser );
	  Long selectRecord(Long id);

}
