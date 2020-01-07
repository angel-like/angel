package com.xmsy.server.zxyy.calculate.modules.manager.uservip.service;

import com.baomidou.mybatisplus.service.IService;
import com.xmsy.server.zxyy.calculate.modules.manager.user.entity.UserEntity;
import com.xmsy.server.zxyy.calculate.modules.manager.uservip.entity.UserVipEntity;


/**
 * 用户vip等级表
 *
 * @author xiaoling
 * @email xxxxx
 * @date 2019-06-10 15:18:13
 */
public interface UserVipService extends IService<UserVipEntity> {
	void userUpgradeVip(UserEntity user);
	void userUpgradeVip(Long userId);
}

