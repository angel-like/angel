package com.xmsy.server.zxyy.manager.modules.manager.webhomealertcofig.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xmsy.server.zxyy.manager.modules.manager.webhomealertcofig.entity.WebhomeAlertCofigEntity;
import com.xmsy.server.zxyy.manager.modules.web.homepage.entity.WebhomeAlertEntity;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

/**
 * 官网弹窗配置表

 * 
 * @author xiaoliu
 * @email xxxxx
 * @date 2019-05-03 10:23:36
 */
@Mapper
public interface WebhomeAlertCofigDao extends BaseMapper<WebhomeAlertCofigEntity> {

	List<WebhomeAlertEntity> getAlertAd();
	
}
