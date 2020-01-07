package com.xmsy.server.zxyy.calculate.modules.manager.userlogin.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xmsy.server.zxyy.calculate.modules.manager.userlogin.dao.UserLoginDao;
import com.xmsy.server.zxyy.calculate.modules.manager.userlogin.entity.UserLoginEntity;
import com.xmsy.server.zxyy.calculate.modules.manager.userlogin.service.UserLoginService;

@Transactional
@Service("userLoginService")
public class UserLoginServiceImpl extends ServiceImpl<UserLoginDao, UserLoginEntity> implements UserLoginService {
	// 新增用户登陆记录
	@Override
	public void insert(Long userId, String ip, String registerDeviceCode, String deviceType, String token,
			String type) {
		UserLoginEntity entity = new UserLoginEntity();
		entity.setUserId(userId);
		entity.setIp(ip);
		entity.setDeviceCode(registerDeviceCode);
		entity.setLoginStatus(type);
		entity.setDeviceType(deviceType);
		entity.setToken(token);
		baseMapper.insert(entity);
	}
}
