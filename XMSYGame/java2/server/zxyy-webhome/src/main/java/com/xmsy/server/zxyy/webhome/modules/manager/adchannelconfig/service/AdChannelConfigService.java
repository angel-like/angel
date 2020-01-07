package com.xmsy.server.zxyy.webhome.modules.manager.adchannelconfig.service;

import com.baomidou.mybatisplus.service.IService;
import com.xmsy.server.zxyy.webhome.modules.manager.adchannelconfig.entity.AdChannelConfigEntity;


/**
 * 渠道配置表
 *
 * @author axiong
 * @email xxxxx
 * @date 2019-07-02 17:05:09
 */
public interface AdChannelConfigService extends IService<AdChannelConfigEntity> {
	/**
	 * 通过渠道码查询对应的渠道配置
	 * @param channelCode
	 * @return
	 */
	AdChannelConfigEntity findAdChannelByChannelCode(String channelCode);
}

