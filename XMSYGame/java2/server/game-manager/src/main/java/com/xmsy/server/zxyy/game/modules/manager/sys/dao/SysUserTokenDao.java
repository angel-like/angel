package com.xmsy.server.zxyy.game.modules.manager.sys.dao;

import com.xmsy.server.zxyy.game.base.BaseDao;
import com.xmsy.server.zxyy.game.modules.manager.sys.entity.SysUserTokenEntity;

/**
 * 系统用户Token
 * 
 * @author aleng
 * @email xxxxxx
 * @date 2017-03-23 15:22:07
 */
public interface SysUserTokenDao extends BaseDao<SysUserTokenEntity> {

    SysUserTokenEntity queryByToken(String token);
	
}
