package com.xmsy.server.zxyy.manager.modules.manager.luckyconfig.service;

import com.baomidou.mybatisplus.service.IService;
import com.xmsy.server.zxyy.manager.modules.manager.luckyconfig.entity.LuckyConfigEntity;

import java.util.List;


/**
 * 幸运转盘
 *
 * @author ayang
 * @email xxxxx
 * @date 2019-11-21 12:58:04
 */
public interface LuckyConfigService extends IService<LuckyConfigEntity> {
    List<LuckyConfigEntity> getAllconfig(Long luckyId);
    List<String>getNames(Long luckyId);
    List<LuckyConfigEntity>getSettings(Long luckyId);

    void updateConfig(List<LuckyConfigEntity> configList);
    void updateConfigById(LuckyConfigEntity luckyConfigEntity);
}

