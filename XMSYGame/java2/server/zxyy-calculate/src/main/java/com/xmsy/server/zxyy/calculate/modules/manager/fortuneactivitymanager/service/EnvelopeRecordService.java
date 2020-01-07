package com.xmsy.server.zxyy.calculate.modules.manager.fortuneactivitymanager.service;


import com.baomidou.mybatisplus.service.IService;
import com.xmsy.server.zxyy.calculate.modules.manager.fortuneactivitymanager.entity.EnvelopeRecordEntity;
import com.xmsy.server.zxyy.calculate.modules.manager.fortuneactivitymanager.entity.EnvelopeTaskConfigEntity;
import com.xmsy.server.zxyy.calculate.modules.manager.fortuneactivitymanager.entity.FortuneActiviConfigEntity;

/**
 * 天降财神红包记录
 *
 * @author ahui
 * @email sunlightcs@gmail.com
 * @date 2019-12-12 11:38:04
 */
public interface EnvelopeRecordService extends IService<EnvelopeRecordEntity> {
	/**
	 * 通过   有效打码     获取新增红包个数  
	 * @param fortuneActivi   活动实体类
	 * @param EnvelopeTaskParam  红包事件实体
	 */
	Integer gainValidBetEnvelope(Long userId,FortuneActiviConfigEntity fortuneActivi,EnvelopeTaskConfigEntity envelopeTask);
	/**
	 * 通过   充值金额    获取新增红包个数  
	 * @param userId
	 * @param fortuneActivi
	 * @param envelopeTask
	 * @param unEnvelopeNum
	 * @return
	 */
	Integer gainRechargeAmountEnvelope(Long userId, FortuneActiviConfigEntity fortuneActivi,
			EnvelopeTaskConfigEntity envelopeTask, Long rechargeAmount);
}

