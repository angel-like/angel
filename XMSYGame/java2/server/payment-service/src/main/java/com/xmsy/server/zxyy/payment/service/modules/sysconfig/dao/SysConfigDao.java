package com.xmsy.server.zxyy.payment.service.modules.sysconfig.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xmsy.server.zxyy.payment.service.modules.sysconfig.entity.SysConfigEntity;

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

}
