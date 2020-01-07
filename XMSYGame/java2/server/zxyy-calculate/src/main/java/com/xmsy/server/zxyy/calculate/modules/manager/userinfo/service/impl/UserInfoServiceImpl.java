package com.xmsy.server.zxyy.calculate.modules.manager.userinfo.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xmsy.server.zxyy.calculate.modules.manager.userinfo.dao.UserInfoDao;
import com.xmsy.server.zxyy.calculate.modules.manager.userinfo.entity.UserInfoEntity;
import com.xmsy.server.zxyy.calculate.modules.manager.userinfo.service.UserInfoService;

@Transactional
@Service("userInfoService")
public class UserInfoServiceImpl extends ServiceImpl<UserInfoDao, UserInfoEntity> implements UserInfoService {


	@Override
	public Integer updateUserBaseInfo(UserInfoEntity entity) {
		return this.baseMapper.updateUserBaseInfo(entity);
	}

	@Override
	public void updateUserAddressByUserId(UserInfoEntity entity) {
		this.baseMapper.updateUserAddressByUserId(entity);
		
	}

}
