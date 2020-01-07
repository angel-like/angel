package com.xmsy.server.zxyy.calculate.modules.manager.userhierarchy.service;

import com.baomidou.mybatisplus.service.IService;
import com.xmsy.server.zxyy.calculate.modules.manager.userhierarchy.entity.UserHierarchyEntity;

/**
 * 用户层级表
 *
 * @author aleng
 * @email xxxxx
 * @date 2019-01-02 11:57:53
 */
public interface UserHierarchyService extends IService<UserHierarchyEntity> {

    //获取默认层级
	UserHierarchyEntity getDefaultHierarchy();
}

