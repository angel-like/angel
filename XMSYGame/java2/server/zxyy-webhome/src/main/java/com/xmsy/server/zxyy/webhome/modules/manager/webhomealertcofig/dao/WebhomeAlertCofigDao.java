package com.xmsy.server.zxyy.webhome.modules.manager.webhomealertcofig.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xmsy.server.zxyy.webhome.modules.manager.webhomealertcofig.entity.WebhomeAlertCofigEntity;
import com.xmsy.server.zxyy.webhome.modules.web.homepage.entity.WebhomeAlertEntity;

/**
 * 官网弹窗配置表
 * 
 * 
 * @author xiaoliu
 * @email xxxxx
 * @date 2019-05-03 10:23:36
 */
@Mapper
public interface WebhomeAlertCofigDao extends BaseMapper<WebhomeAlertCofigEntity> {

	List<WebhomeAlertEntity> getAlertAd();

}
