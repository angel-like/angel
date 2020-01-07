package com.xmsy.server.zxyy.manager.modules.manager.sys.dao;

import java.util.List;

import com.xmsy.server.zxyy.manager.base.BaseDao;
import com.xmsy.server.zxyy.manager.modules.manager.sys.entity.SysUserRoleEntity;

/**
 * 用户与角色对应关系
 * 
 * @author aleng
 * @email xxxxxx
 * @date 2016年9月18日 上午9:34:46
 */
public interface SysUserRoleDao extends BaseDao<SysUserRoleEntity> {
	
	/**
	 * 根据用户ID，获取角色ID列表
	 */
	List<Long> queryRoleIdList(Long userId);


	/**
	 * 根据角色ID数组，批量删除
	 */
	int deleteBatch(Long[] roleIds);
}
