package com.xmsy.server.zxyy.webhome.modules.manager.fortuneactivitymanager.service;


import java.util.List;

import com.baomidou.mybatisplus.service.IService;
import com.xmsy.server.zxyy.webhome.modules.manager.fortuneactivitymanager.entity.EnvelopeRecordEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.fortuneactivitymanager.entity.FortuneActiviConfigEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.user.entity.UserEntity;

/**
 * 天降财神红包记录
 *
 * @author ahui
 * @email sunlightcs@gmail.com
 * @date 2019-12-12 11:38:04
 */
public interface EnvelopeRecordService extends IService<EnvelopeRecordEntity> {
	/**
	 * 开启一个红包
	 * @param envelopeRecord
	 */
	Long openEnvelopeOne(EnvelopeRecordEntity envelopeRecord,FortuneActiviConfigEntity fortuneActiviConfig,UserEntity user);
	/**
	 * 开启全部红包
	 * @param envelopeRecord
	 */
	Long openEnvelopeAll(List<EnvelopeRecordEntity> envelopeRecordlist,FortuneActiviConfigEntity fortuneActiviConfig,UserEntity user);
}

