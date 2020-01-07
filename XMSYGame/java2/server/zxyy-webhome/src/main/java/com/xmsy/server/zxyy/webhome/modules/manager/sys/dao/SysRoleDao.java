package com.xmsy.server.zxyy.webhome.modules.manager.sys.dao;

import java.util.List;

import com.xmsy.server.zxyy.webhome.base.BaseDao;
import com.xmsy.server.zxyy.webhome.modules.manager.sys.entity.SysRoleEntity;

/**
 * 角色管理
 * 
 * @author aleng
 * @email xxxxxx
 * @date 2016年9月18日 上午9:33:33
 */
public interface SysRoleDao extends BaseDao<SysRoleEntity> {
	
	/**
	 * 查询用户创建的角色ID列表
	 */
	List<Long> queryRoleIdList(Long createUserId);
}
