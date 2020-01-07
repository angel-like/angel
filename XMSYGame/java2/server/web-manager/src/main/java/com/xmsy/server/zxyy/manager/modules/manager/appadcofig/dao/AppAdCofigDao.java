package com.xmsy.server.zxyy.manager.modules.manager.appadcofig.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xmsy.server.zxyy.manager.modules.app.configuration.param.AppAdConfigParam;
import com.xmsy.server.zxyy.manager.modules.manager.appadcofig.entity.AppAdCofigEntity;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

/**
 * app广告配置
 * 
 * @author aleng
 * @email xxxxx
 * @date 2019-03-04 19:20:35
 */
@Mapper
public interface AppAdCofigDao extends BaseMapper<AppAdCofigEntity> {
	
	List<AppAdConfigParam> selectAppAdListForApp();
	
}
