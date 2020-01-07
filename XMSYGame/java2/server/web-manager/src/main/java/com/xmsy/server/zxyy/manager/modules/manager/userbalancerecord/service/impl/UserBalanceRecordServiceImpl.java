package com.xmsy.server.zxyy.manager.modules.manager.userbalancerecord.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xmsy.server.zxyy.manager.modules.manager.userbalancerecord.dao.UserBalanceRecordDao;
import com.xmsy.server.zxyy.manager.modules.manager.userbalancerecord.entity.UserBalanceRecordEntity;
import com.xmsy.server.zxyy.manager.modules.manager.userbalancerecord.service.UserBalanceRecordService;


@Service("userBalanceRecordService")
public class UserBalanceRecordServiceImpl extends ServiceImpl<UserBalanceRecordDao, UserBalanceRecordEntity> implements UserBalanceRecordService {

	@Override
	public List<UserBalanceRecordEntity> getPageList(UserBalanceRecordEntity userBalanceRecordEntity, Pagination page) {
		return baseMapper.findPageList(userBalanceRecordEntity, page);
	}

}
