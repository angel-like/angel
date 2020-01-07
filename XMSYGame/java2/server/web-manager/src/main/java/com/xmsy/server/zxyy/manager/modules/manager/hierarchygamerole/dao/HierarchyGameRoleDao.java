package com.xmsy.server.zxyy.manager.modules.manager.hierarchygamerole.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xmsy.server.zxyy.manager.modules.manager.hierarchygamerole.entity.HierarchyGameRoleEntity;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 层级游戏权限关联表
 * 
 * @author xiaoliu
 * @email xxxxx
 * @date 2019-03-09 14:26:36
 */
@Mapper
public interface HierarchyGameRoleDao extends BaseMapper<HierarchyGameRoleEntity> {

	void deleteByHierarchyId(@Param("hierarchyId") Long hierarchyId);
	
	void deleteByGameId(@Param("id") Long id);
	
	
	
}
