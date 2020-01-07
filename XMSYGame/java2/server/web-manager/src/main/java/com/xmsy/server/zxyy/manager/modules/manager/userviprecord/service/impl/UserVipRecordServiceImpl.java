package com.xmsy.server.zxyy.manager.modules.manager.userviprecord.service.impl;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xmsy.common.define.page.PageParam;
import com.xmsy.server.zxyy.manager.common.utils.PageUtils;
import com.xmsy.server.zxyy.manager.modules.manager.userviprecord.dao.UserVipRecordDao;
import com.xmsy.server.zxyy.manager.modules.manager.userviprecord.entity.UserVipRecordEntity;
import com.xmsy.server.zxyy.manager.modules.manager.userviprecord.service.UserVipRecordService;


@Service("userVipRecordService")
public class UserVipRecordServiceImpl extends ServiceImpl<UserVipRecordDao, UserVipRecordEntity> implements UserVipRecordService {

	@Override
	public PageUtils findUserVipRecordPage(UserVipRecordEntity userVipRecord, PageParam pageParam) {
		Page<Map<String, Object>> page = new Page<>(pageParam.getPage(), pageParam.getLimit());
		page.setRecords(this.baseMapper.findUserVipRecordPage(userVipRecord, page));
		return new PageUtils(page);
	}


}
