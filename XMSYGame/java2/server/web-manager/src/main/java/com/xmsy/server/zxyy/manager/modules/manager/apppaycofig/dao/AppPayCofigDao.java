package com.xmsy.server.zxyy.manager.modules.manager.apppaycofig.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xmsy.server.zxyy.manager.modules.app.configuration.param.ConfigUrationParam;
import com.xmsy.server.zxyy.manager.modules.manager.apppaycofig.entity.AppPayCofigEntity;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

/**
 * app支付配置
 * 
 * @author aleng
 * @email xxxxx
 * @date 2019-03-04 10:32:45
 */
@Mapper
public interface AppPayCofigDao extends BaseMapper<AppPayCofigEntity> {

	List<ConfigUrationParam> selectPayConfigList();
	
}
