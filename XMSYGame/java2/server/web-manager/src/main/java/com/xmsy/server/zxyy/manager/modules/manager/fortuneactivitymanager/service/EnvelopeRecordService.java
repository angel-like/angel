package com.xmsy.server.zxyy.manager.modules.manager.fortuneactivitymanager.service;


import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.xmsy.common.define.page.PageParam;
import com.xmsy.server.zxyy.manager.modules.manager.fortuneactivitymanager.entity.EnvelopeRecordEntity;

/**
 * 天降财神红包记录
 *
 * @author ahui
 * @email sunlightcs@gmail.com
 * @date 2019-12-12 11:38:04
 */
public interface EnvelopeRecordService extends IService<EnvelopeRecordEntity> {
    Page<EnvelopeRecordEntity> count(PageParam pageParam, EnvelopeRecordEntity envelopeRecordEntity);

}

