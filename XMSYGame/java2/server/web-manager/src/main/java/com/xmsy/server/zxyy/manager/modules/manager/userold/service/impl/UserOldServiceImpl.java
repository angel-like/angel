package com.xmsy.server.zxyy.manager.modules.manager.userold.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xmsy.common.define.page.PageParam;
import com.xmsy.server.zxyy.manager.common.utils.PageUtils;
import com.xmsy.server.zxyy.manager.modules.manager.userold.dao.UserOldDao;
import com.xmsy.server.zxyy.manager.modules.manager.userold.entity.UserOldEntity;
import com.xmsy.server.zxyy.manager.modules.manager.userold.service.UserOldService;


@Service("userOldService")
public class UserOldServiceImpl extends ServiceImpl<UserOldDao, UserOldEntity> implements UserOldService {

	@Override
	public PageUtils findUserPage(UserOldEntity UserOldEntity, PageParam pageParam) {
		Page<Map<String, Object>> page = new Page<>(pageParam.getPage(), pageParam.getLimit());
		
		page.setRecords(this.baseMapper.findUserPage(UserOldEntity, page));
		List<Map<String, Object>> userList = page.getRecords();
		userList.size();
		return new PageUtils(page);
	}

	@Override
	public Integer updateUserRemark(UserOldEntity userOldEntity) {
		return  this.baseMapper.updateUserRemark(userOldEntity);
	}


}
