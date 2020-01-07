package com.xmsy.server.zxyy.manager.modules.manager.userbalance.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xmsy.server.zxyy.manager.modules.manager.userbalance.dao.UserBalanceDao;
import com.xmsy.server.zxyy.manager.modules.manager.userbalance.entity.UserBalanceEntity;
import com.xmsy.server.zxyy.manager.modules.manager.userbalance.service.UserBalanceService;

@Service("userBalanceService")
public class UserBalanceServiceImpl extends ServiceImpl<UserBalanceDao, UserBalanceEntity> implements UserBalanceService {

	@Override
	public List<UserBalanceEntity> findUserBalanceByUserId(List<Long> idList) {
		return this.baseMapper.findUserBalanceByUserId(idList);
	}

	@Override
	public Long AllPrize(UserBalanceEntity userBalance) {
		return baseMapper.AllPrize(userBalance);
	}

	@Override
	public Long AllMoney(UserBalanceEntity userBalance) {
		return baseMapper.AllMoney(userBalance);
	}

	@Override
	public Long AllProfitYesterday(UserBalanceEntity userBalance) {
		return baseMapper.AllProfitYesterday(userBalance);
	}

}
