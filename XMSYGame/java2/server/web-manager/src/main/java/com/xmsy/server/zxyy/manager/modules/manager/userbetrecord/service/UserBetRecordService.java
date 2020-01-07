package com.xmsy.server.zxyy.manager.modules.manager.userbetrecord.service;

import com.baomidou.mybatisplus.service.IService;
import com.xmsy.server.zxyy.manager.modules.manager.userbetrecord.entity.UserBetRecordEntity;

/**
 * 用户每日有效下注
 *
 * @author aleng
 * @email xxxxx
 * @date 2019-06-08 10:51:52
 */
public interface UserBetRecordService extends IService<UserBetRecordEntity> {

	Long sumUserBet(Long id);

}
