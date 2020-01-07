package com.xmsy.server.zxyy.webhome.modules.manager.paychannelconfig.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.xmsy.common.define.page.PageParam;
import com.xmsy.server.zxyy.webhome.modules.manager.paychannelconfig.entity.PayChannelConfigEntity;

/**
 * 支付渠道配置
 *
 * @author aleng
 * @email xxxxx
 * @date 2019-04-10 14:20:07
 */
public interface PayChannelConfigService extends IService<PayChannelConfigEntity> {

	/**
	 * 获取支付金额配置列表
	 * 
	 * @param paychannelconfig
	 * @param pageParam
	 * @return
	 */
	Page<PayChannelConfigEntity> getPayChannelConfigs(PayChannelConfigEntity paychannelconfig, PageParam pageParam);
}
