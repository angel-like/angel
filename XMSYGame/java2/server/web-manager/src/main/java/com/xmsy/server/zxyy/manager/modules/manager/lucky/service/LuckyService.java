package com.xmsy.server.zxyy.manager.modules.manager.lucky.service;

import com.baomidou.mybatisplus.service.IService;
import com.xmsy.server.zxyy.manager.modules.manager.lucky.entity.LuckyEntity;

import java.util.List;
import java.util.Map;


/**
 * 幸运转盘
 *
 * @author ayang
 * @email xxxxx
 * @date 2019-11-21 12:58:04
 */
public interface LuckyService extends IService<LuckyEntity> {
    List<Map<String, Object>> getLuckySettings();

}

