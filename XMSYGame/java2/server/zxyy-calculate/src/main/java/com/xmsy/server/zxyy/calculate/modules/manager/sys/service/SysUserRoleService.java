package com.xmsy.server.zxyy.calculate.modules.manager.sys.service;

import java.util.List;

import com.baomidou.mybatisplus.service.IService;
import com.xmsy.server.zxyy.calculate.modules.manager.sys.entity.SysUserRoleEntity;



/**
 * 用户与角色对应关系
 * 
 * @author aleng
 * @email xxxxxx
 * @date 2016年9月18日 上午9:43:24
 */
public interface SysUserRoleService extends IService<SysUserRoleEntity> {
	
	void saveOrUpdate(Long userId, List<Long> roleIdList);
	
	/**
	 * 根据用户ID，获取角色ID列表
	 */
	List<Long> queryRoleIdList(Long userId);
	
	/**
	 * 根据角色id获取用户
	 */
	List<Long> queryUserIdList(Long roleId);

	/**
	 * 根据角色ID数组，批量删除
	 */
	int deleteBatch(Long[] roleIds);
}
