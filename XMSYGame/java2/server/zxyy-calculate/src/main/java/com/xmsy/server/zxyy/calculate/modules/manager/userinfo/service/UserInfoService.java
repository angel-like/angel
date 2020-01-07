package com.xmsy.server.zxyy.calculate.modules.manager.userinfo.service;

import com.baomidou.mybatisplus.service.IService;
import com.xmsy.server.zxyy.calculate.modules.manager.userinfo.entity.UserInfoEntity;

/**
 * 用户基本信息表
 *
 * @author lpp
 * @email xxxxx
 * @date 2019-01-15 10:49:21
 */
public interface UserInfoService extends IService<UserInfoEntity> {

    /**
	 * 更新用户基本信息 没有内容更新为空
	 * @param entity
	 * @return
	 */
	Integer updateUserBaseInfo(UserInfoEntity entity);
	
	void updateUserAddressByUserId(UserInfoEntity entity);
}

