package com.xmsy.server.zxyy.calculate.modules.manager.userlogin.service;

import com.baomidou.mybatisplus.service.IService;
import com.xmsy.server.zxyy.calculate.modules.manager.userlogin.entity.UserLoginEntity;

/**
 * 用户登陆记录表
 *
 * @author aleng
 * @email xxxxx
 * @date 2018-12-26 11:18:25
 */
public interface UserLoginService extends IService<UserLoginEntity> {

	void insert(Long userId, String ip, String registerDeviceCode, String deviceType, String token, String type);
}

