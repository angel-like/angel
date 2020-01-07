package com.xmsy.server.zxyy.webhome.modules.manager.userbetrecord.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xmsy.server.zxyy.webhome.common.utils.DateUtils;
import com.xmsy.server.zxyy.webhome.modules.manager.userbetrecord.dao.UserBetRecordDao;
import com.xmsy.server.zxyy.webhome.modules.manager.userbetrecord.entity.UserBetRecordEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.userbetrecord.service.UserBetRecordService;

@Service("userBetRecordService")
public class UserBetRecordServiceImpl extends ServiceImpl<UserBetRecordDao, UserBetRecordEntity>
		implements UserBetRecordService {

	// 修改用户的每日盈利
	@Override
	public void updateUserEveryDateBet(Long userId, String userAccount, Long betCoins, String queryDate,boolean isGmUser ) {
		// 通过用户ID获取用户时间倒序第一条数据
		UserBetRecordEntity betRecord = baseMapper.selectEntity(userId);
		// 如果有数据，并且是今天的数据，那么就修改
		if (betRecord != null) {
			if (queryDate.equals(DateUtils.format(betRecord.getCreateTime()))) {
				baseMapper.updateById(userId, betCoins, queryDate);
			} else {// 否则新增，并加上昨天的历史有效下注
				UserBetRecordEntity entity = new UserBetRecordEntity();
				entity.setUserId(userId);
				entity.setUserAccount(userAccount);
				entity.setBetCoins(betCoins);
				entity.setGmUser(isGmUser);
				entity.setOldBet(betCoins + betRecord.getOldBet());
				baseMapper.insert(entity);
			}

		} else {// 没有的话就新增
			UserBetRecordEntity entity = new UserBetRecordEntity();
			entity.setUserId(userId);
			entity.setUserAccount(userAccount);
			entity.setBetCoins(betCoins);
			entity.setGmUser(isGmUser);
			entity.setOldBet(betCoins);
			baseMapper.insert(entity);
		}

	}

	@Override
	public Long selectRecord(Long userId) {
		UserBetRecordEntity userBetRecordEntity = this.baseMapper.selectBetCoins(userId);
		if(userBetRecordEntity==null){
			return  0l;
		}
		Long betCoins = userBetRecordEntity.getBetCoins()/100;
		return betCoins;
	}

}
