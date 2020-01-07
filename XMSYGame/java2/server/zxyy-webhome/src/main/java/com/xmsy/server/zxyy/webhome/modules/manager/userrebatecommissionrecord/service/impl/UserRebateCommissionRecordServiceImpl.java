package com.xmsy.server.zxyy.webhome.modules.manager.userrebatecommissionrecord.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xmsy.server.zxyy.webhome.modules.manager.userrebatecommissionrecord.dao.UserRebateCommissionRecordDao;
import com.xmsy.server.zxyy.webhome.modules.manager.userrebatecommissionrecord.entity.UserRebateCommissionRecordEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.userrebatecommissionrecord.entity.UserRebateCommissionSumEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.userrebatecommissionrecord.service.UserRebateCommissionRecordService;

@Service("userRebateCommissionRecordService")
public class UserRebateCommissionRecordServiceImpl
		extends ServiceImpl<UserRebateCommissionRecordDao, UserRebateCommissionRecordEntity>
		implements UserRebateCommissionRecordService {
	/**
	 * 获取用户指定时间的佣金总额
	 */
	@Override
	public UserRebateCommissionSumEntity selectSumCommission(Long userId, String queryDate, String startDate,
			String endDate) {
		// TODO Auto-generated method stub
		return baseMapper.selectSumCommission(userId, queryDate, startDate, endDate);
	}

}
