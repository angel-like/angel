package com.xmsy.server.zxyy.webhome.modules.manager.sys.dao;

import java.util.List;

import com.xmsy.server.zxyy.webhome.base.BaseDao;
import com.xmsy.server.zxyy.webhome.modules.manager.sys.entity.SysRoleMenuEntity;

/**
 * 角色与菜单对应关系
 * 
 * @author aleng
 * @email xxxxxx
 * @date 2016年9月18日 上午9:33:46
 */
public interface SysRoleMenuDao extends BaseDao<SysRoleMenuEntity> {
	
	/**
	 * 根据角色ID，获取菜单ID列表
	 */
	List<Long> queryMenuIdList(Long roleId);

	/**
	 * 根据角色ID数组，批量删除
	 */
	int deleteBatch(Long[] roleIds);

	List<SysRoleMenuEntity> selectBatchRooleIds(Long[] roleIds);
}
