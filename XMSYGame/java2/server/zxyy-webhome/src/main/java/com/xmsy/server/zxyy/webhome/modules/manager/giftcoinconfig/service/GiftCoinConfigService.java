package com.xmsy.server.zxyy.webhome.modules.manager.giftcoinconfig.service;

import java.util.List;

import com.baomidou.mybatisplus.service.IService;
import com.xmsy.server.zxyy.webhome.modules.manager.giftcoinconfig.entity.GiftCoinConfigEntity;


/**
 * 金币奖励配置
 *
 * @author xiaoling
 * @email xxxxx
 * @date 2019-05-21 11:00:51
 */
public interface GiftCoinConfigService extends IService<GiftCoinConfigEntity> {
	List<GiftCoinConfigEntity> getEncouragementConfig();
}

