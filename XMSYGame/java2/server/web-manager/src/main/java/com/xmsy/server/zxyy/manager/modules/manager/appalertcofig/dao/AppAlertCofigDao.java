package com.xmsy.server.zxyy.manager.modules.manager.appalertcofig.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xmsy.server.zxyy.manager.modules.app.configuration.param.AppAlertConfigParam;
import com.xmsy.server.zxyy.manager.modules.manager.appalertcofig.entity.AppAlertCofigEntity;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

/**
 * app弹框配置
 * 
 * @author aleng
 * @email xxxxx
 * @date 2019-03-07 14:27:10
 */
@Mapper
public interface AppAlertCofigDao extends BaseMapper<AppAlertCofigEntity> {

	List<AppAlertConfigParam> selectListForWeb();
	
}
