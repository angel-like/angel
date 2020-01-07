package com.xmsy.server.zxyy.calculate.modules.manager.useragenthierarchy.dao;

import com.xmsy.server.zxyy.calculate.modules.manager.useragenthierarchy.entity.UserAgentHierarchyEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 用户代理层级设置
 * 
 * @author aleng
 * @email xxxxx
 * @date 2019-01-24 19:54:08
 */
@Mapper
public interface UserAgentHierarchyDao extends BaseMapper<UserAgentHierarchyEntity> {

	Integer updateType(@Param("id") Long id, @Param("type") Boolean type);

}
