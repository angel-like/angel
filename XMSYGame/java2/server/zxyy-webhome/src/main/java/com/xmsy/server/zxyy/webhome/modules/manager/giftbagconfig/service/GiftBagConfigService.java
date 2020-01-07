package com.xmsy.server.zxyy.webhome.modules.manager.giftbagconfig.service;

import com.baomidou.mybatisplus.service.IService;
import com.xmsy.server.zxyy.webhome.modules.manager.giftbagconfig.entity.GiftBagConfigEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.user.entity.UserEntity;


/**
 * 礼包配置表
 *
 * @author axiong
 * @email xxxxx
 * @date 2019-07-15 10:13:06
 */
public interface GiftBagConfigService extends IService<GiftBagConfigEntity> {
	/**
	 * 校验兑换码
	 */
	public void verificationExchangeCode(GiftBagConfigEntity giftBagConfigEntity,UserEntity user);
	/**
	 * 通过id 
	 *   修改礼包兑换表的兑换数量  以及    领取状态
	 */
	public void updateGiftBagConfig(Long id,Integer exchangeNum);
}

