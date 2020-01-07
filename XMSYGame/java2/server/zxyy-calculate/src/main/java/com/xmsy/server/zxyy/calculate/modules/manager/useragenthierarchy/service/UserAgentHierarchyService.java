package com.xmsy.server.zxyy.calculate.modules.manager.useragenthierarchy.service;

import com.baomidou.mybatisplus.service.IService;
import com.xmsy.server.zxyy.calculate.modules.manager.useragenthierarchy.entity.UserAgentHierarchyEntity;

/**
 * 用户代理层级设置
 *
 * @author aleng
 * @email xxxxx
 * @date 2019-01-24 19:54:08
 */
public interface UserAgentHierarchyService extends IService<UserAgentHierarchyEntity> {

	// 获取默认代理层级
	UserAgentHierarchyEntity getDefaultHierarchy();
}
