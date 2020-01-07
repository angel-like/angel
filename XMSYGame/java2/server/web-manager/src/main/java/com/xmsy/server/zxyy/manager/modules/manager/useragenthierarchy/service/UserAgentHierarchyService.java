package com.xmsy.server.zxyy.manager.modules.manager.useragenthierarchy.service;

import com.baomidou.mybatisplus.service.IService;
import com.xmsy.server.zxyy.manager.modules.manager.useragenthierarchy.entity.UserAgentHierarchyEntity;

/**
 * 用户代理层级设置
 *
 * @author aleng
 * @email xxxxx
 * @date 2019-01-24 19:54:08
 */
public interface UserAgentHierarchyService extends IService<UserAgentHierarchyEntity> {

	/**
	 * 设置默认
	 * 
	 * @param id
	 */
	void setDefault(Long id);

	// 获取默认代理层级
	UserAgentHierarchyEntity getDefaultHierarchy();
}
