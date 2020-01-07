package com.xmsy.server.zxyy.manager.modules.manager.userbetrecord.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xmsy.server.zxyy.manager.modules.manager.userbetrecord.dao.UserBetRecordDao;
import com.xmsy.server.zxyy.manager.modules.manager.userbetrecord.entity.UserBetRecordEntity;
import com.xmsy.server.zxyy.manager.modules.manager.userbetrecord.service.UserBetRecordService;

@Service("userBetRecordService")
public class UserBetRecordServiceImpl extends ServiceImpl<UserBetRecordDao, UserBetRecordEntity>
		implements UserBetRecordService {

	// 获取用户总打码
	@Override
	public Long sumUserBet(Long userId) {
		return baseMapper.sumUserBet(userId);
	}

}
