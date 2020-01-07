package com.xmsy.server.zxyy.manager.modules.manager.signrewardcontinuouseveryday.service.impl;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xmsy.common.define.page.PageParam;
import com.xmsy.server.zxyy.manager.common.utils.PageUtils;
import com.xmsy.server.zxyy.manager.modules.manager.signrewardcontinuouseveryday.dao.SignRewardContinuousEveryDayDao;
import com.xmsy.server.zxyy.manager.modules.manager.signrewardcontinuouseveryday.entity.SignRewardContinuousEveryDayEntity;
import com.xmsy.server.zxyy.manager.modules.manager.signrewardcontinuouseveryday.service.SignRewardContinuousEveryDayService;


@Service("signRewardContinuousEveryDayService")
public class SignRewardContinuousEveryDayServiceImpl extends ServiceImpl<SignRewardContinuousEveryDayDao, SignRewardContinuousEveryDayEntity> implements SignRewardContinuousEveryDayService {

	@Override
	public PageUtils findSignRewardContinuousEveryDayPage(
			SignRewardContinuousEveryDayEntity signRewardContinuousEveryDay, PageParam pageParam) {
		Page<Map<String, Object>> page = new Page<>(pageParam.getPage(), pageParam.getLimit());
		page.setRecords(this.baseMapper.findSignRewardContinuousEveryDayPage(signRewardContinuousEveryDay, page));
		return new PageUtils(page);
	}


}
