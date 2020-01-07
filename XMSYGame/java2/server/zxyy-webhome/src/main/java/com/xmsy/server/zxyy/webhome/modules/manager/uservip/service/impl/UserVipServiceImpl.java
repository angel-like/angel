package com.xmsy.server.zxyy.webhome.modules.manager.uservip.service.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;


import com.xmsy.server.zxyy.webhome.modules.manager.uservip.dao.UserVipDao;
import com.xmsy.server.zxyy.webhome.modules.manager.uservip.entity.UserVipEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.uservip.service.UserVipService;


@Service("userVipService")
public class UserVipServiceImpl extends ServiceImpl<UserVipDao, UserVipEntity> implements UserVipService {

	@Override
	public UserVipEntity selectNextVip(Integer sort) {
		return this.baseMapper.selectNextVip(sort);
	}


}
