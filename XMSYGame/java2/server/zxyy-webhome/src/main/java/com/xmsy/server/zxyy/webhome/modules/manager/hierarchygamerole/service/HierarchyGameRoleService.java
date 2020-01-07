package com.xmsy.server.zxyy.webhome.modules.manager.hierarchygamerole.service;

import com.baomidou.mybatisplus.service.IService;
import com.xmsy.server.zxyy.webhome.modules.manager.hierarchygamerole.entity.HierarchyGameRoleEntity;


/**
 * 层级游戏权限关联表
 *
 * @author xiaoliu
 * @email xxxxx
 * @date 2019-03-09 14:26:36
 */
public interface HierarchyGameRoleService extends IService<HierarchyGameRoleEntity> {

	void deleteByHierarchyId(Long hierarchyId);

}

