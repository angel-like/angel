package com.xmsy.server.zxyy.manager.modules.manager.hierarchypaychannelconfigrelationship.dao;

import com.xmsy.server.zxyy.manager.modules.manager.hierarchypaychannelconfigrelationship.entity.HierarchyPaychannelconfigRelationshipEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 层级支付限额关系表
 * 
 * @author xiaoling
 * @email xxxxx
 * @date 2019-09-03 10:59:47
 */
@Mapper
public interface HierarchyPaychannelconfigRelationshipDao extends BaseMapper<HierarchyPaychannelconfigRelationshipEntity> {
	
	/**
	 * 删除（根据层级id）
	 * @param hierarchyId
	 */
	void deleteByPaychannelconfigId(@Param("paychannelconfigId") Long paychannelconfigId);
	
	/**
	 * 根据paychannelconfigId查询
	 */
	List<Long> selectByPaychannelconfigId(@Param("paychannelconfigId") Long paychannelconfigId);
	
}
