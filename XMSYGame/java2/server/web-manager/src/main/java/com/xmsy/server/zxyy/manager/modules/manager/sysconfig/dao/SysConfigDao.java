package com.xmsy.server.zxyy.manager.modules.manager.sysconfig.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xmsy.server.zxyy.manager.modules.manager.sysconfig.entity.SysConfigEntity;

/**
 * 系统配置表
 * 
 * @author aleng
 * @email xxxxx
 * @date 2019-05-21 15:26:43
 */
@Mapper
public interface SysConfigDao extends BaseMapper<SysConfigEntity> {

	public String findSysconfigValue(@Param("parent") String parentKey, @Param("paramKey") String paramKey);
	/**
	 * 保存系统支付参数
	 * @param sysConfig
	 * @return
	 */
	public Integer savePayParamDao(@Param("sysConfig") SysConfigEntity sysConfig);
}
