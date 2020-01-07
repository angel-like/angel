package com.xmsy.server.zxyy.webhome.modules.manager.userbalancerate.service.impl;


import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.plugins.Page;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xmsy.common.define.page.PageParam;
import com.xmsy.server.zxyy.webhome.common.utils.PageUtils;
import com.xmsy.server.zxyy.webhome.modules.manager.userbalancerate.dao.UserBalanceRateDao;
import com.xmsy.server.zxyy.webhome.modules.manager.userbalancerate.entity.UserBalanceRateEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.userbalancerate.service.UserBalanceRateService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Transactional
@Service("userBalanceRateService")
public class UserBalanceRateServiceImpl extends ServiceImpl<UserBalanceRateDao, UserBalanceRateEntity> implements UserBalanceRateService {
	@Autowired
	private UserBalanceRateDao userBalanceRateDao;
	@Override
	public PageUtils findUserRecordPageForTime(UserBalanceRateEntity userBalanceRateParam, PageParam pageParam) {
		Page<Map<String, Object>> page = new Page<>(pageParam.getPage(), pageParam.getLimit());
		log.info("[findUserPage] params {}", userBalanceRateParam);
		page.setRecords(userBalanceRateDao.findUserPageForTime(userBalanceRateParam, page));
		
		return new PageUtils(page);
	}


}
