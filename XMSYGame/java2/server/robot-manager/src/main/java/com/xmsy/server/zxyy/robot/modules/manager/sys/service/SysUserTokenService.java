package com.xmsy.server.zxyy.robot.modules.manager.sys.service;

import com.baomidou.mybatisplus.service.IService;
import com.xmsy.server.zxyy.robot.common.utils.R;
import com.xmsy.server.zxyy.robot.modules.manager.sys.entity.SysUserTokenEntity;

/**
 * 用户Token
 * 
 * @author aleng
 * @email xxxxxx
 * @date 2017-03-23 15:22:07
 */
public interface SysUserTokenService extends IService<SysUserTokenEntity> {

	/**
	 * 生成token
	 * @param userId  用户ID
	 */
	R createToken(long userId);

	/**
	 * 退出，修改token值
	 * @param userId  用户ID
	 */
	void logout(long userId);

}
