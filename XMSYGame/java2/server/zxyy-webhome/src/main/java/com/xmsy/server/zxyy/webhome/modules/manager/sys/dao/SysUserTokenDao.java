package com.xmsy.server.zxyy.webhome.modules.manager.sys.dao;

import com.xmsy.server.zxyy.webhome.base.BaseDao;
import com.xmsy.server.zxyy.webhome.modules.manager.sys.entity.SysUserTokenEntity;

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
