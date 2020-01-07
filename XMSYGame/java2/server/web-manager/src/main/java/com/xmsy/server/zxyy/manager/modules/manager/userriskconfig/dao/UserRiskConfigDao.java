package com.xmsy.server.zxyy.manager.modules.manager.userriskconfig.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xmsy.server.zxyy.manager.modules.manager.userriskconfig.entity.UserRiskConfigEntity;

import org.apache.ibatis.annotations.Mapper;

/**
 * 用户风控配置表
 * 
 * @author xiaoling
 * @email xxxxx
 * @date 2019-05-05 11:28:56
 */
@Mapper
public interface UserRiskConfigDao extends BaseMapper<UserRiskConfigEntity> {
	
	/**
	 * 删除（真删除）
	 * @param id
	 * @return
	 */
	Long physicalDelete(Long id);
}
