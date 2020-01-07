package com.xmsy.server.zxyy.manager.modules.manager.webhomehead.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.service.IService;
import com.xmsy.server.zxyy.manager.common.utils.PageUtils;
import com.xmsy.server.zxyy.manager.modules.manager.webhomehead.entity.WebhomeHeadEntity;

/**
 * 官网头部管理
 *
 * @author aleng
 * @email xxxxx
 * @date 2019-01-24 20:28:36
 */
public interface WebhomeHeadService extends IService<WebhomeHeadEntity> {

    PageUtils queryPage(Map<String, Object> params);
	
    /**
	 * 官网获取头部菜单
	 * @return
	 */
    List<WebhomeHeadEntity> selectListByWeb();

	List<WebhomeHeadEntity> selectListBytype(String type);
}

