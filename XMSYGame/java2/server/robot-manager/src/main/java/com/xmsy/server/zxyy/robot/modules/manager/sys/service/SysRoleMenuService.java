package com.xmsy.server.zxyy.robot.modules.manager.sys.service;

import java.util.List;

import com.baomidou.mybatisplus.service.IService;
import com.xmsy.server.zxyy.robot.modules.manager.sys.entity.SysRoleMenuEntity;



/**
 * 角色与菜单对应关系
 * 
 * @author aleng
 * @email xxxxxx
 * @date 2016年9月18日 上午9:42:30
 */
public interface SysRoleMenuService extends IService<SysRoleMenuEntity> {
	
	void saveOrUpdate(Long roleId, List<Long> menuIdList);
	
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
