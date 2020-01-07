package com.xmsy.server.zxyy.webhome.modules.manager.webhomealertcofig.service;

import java.util.List;

import com.baomidou.mybatisplus.service.IService;
import com.xmsy.server.zxyy.webhome.modules.manager.webhomealertcofig.entity.WebhomeAlertCofigEntity;
import com.xmsy.server.zxyy.webhome.modules.web.homepage.entity.WebhomeAlertEntity;


/**
 * 官网弹窗配置表

 *
 * @author xiaoliu
 * @email xxxxx
 * @date 2019-05-03 10:23:36
 */
public interface WebhomeAlertCofigService extends IService<WebhomeAlertCofigEntity> {

	List<WebhomeAlertEntity> getAlertAd();

}

