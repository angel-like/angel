package com.xmsy.server.zxyy.manager.modules.manager.userhierarchy.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xmsy.server.zxyy.manager.modules.manager.userhierarchy.entity.UserHierarchyEntity;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 用户层级表
 * 
 * @author aleng
 * @email xxxxx
 * @date 2019-01-02 11:57:53
 */
@Mapper
public interface UserHierarchyDao extends BaseMapper<UserHierarchyEntity> {
	Integer updateType(@Param("id")Long id,@Param("type")Boolean type);
	/**
	 * 新增层级并返回层级ID
	 * 
	 * @param userEntity
	 */
	void insertGetId(UserHierarchyEntity userHierarchy);
	String getHierarchyName(String hierarchyId);
}
