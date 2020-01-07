package com.xmsy.server.zxyy.robot.modules.manager.sys.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.service.IService;
import com.xmsy.server.zxyy.robot.common.utils.PageUtils;
import com.xmsy.server.zxyy.robot.modules.manager.sys.entity.SysRoleEntity;


/**
 * 角色
 * 
 * @author aleng
 * @email xxxxxx
 * @date 2016年9月18日 上午9:42:52
 */
public interface SysRoleService extends IService<SysRoleEntity> {

	PageUtils queryPage(Map<String, Object> params);

	void save(SysRoleEntity role);

	void update(SysRoleEntity role);

	void deleteBatch(Long[] roleIds);

	
	/**
	 * 查询用户创建的角色ID列表
	 */
	List<Long> queryRoleIdList(Long createUserId);
}
