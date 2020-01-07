package com.xmsy.server.zxyy.manager.modules.manager.fortuneactivitymanager.service;


import com.baomidou.mybatisplus.service.IService;
import com.xmsy.server.zxyy.manager.modules.manager.fortuneactivitymanager.entity.FortuneActiviConfigEntity;

import java.util.Map;

/**
 * 天降财神活动配置
 *
 * @author ahui
 * @email sunlightcs@gmail.com
 * @date 2019-12-12 11:38:04
 */
public interface FortuneActiviConfigService extends IService<FortuneActiviConfigEntity> {
    Map<String, String> getMap();

}

