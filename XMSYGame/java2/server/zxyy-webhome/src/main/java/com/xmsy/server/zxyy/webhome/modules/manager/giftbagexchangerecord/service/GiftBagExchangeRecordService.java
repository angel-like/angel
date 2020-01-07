package com.xmsy.server.zxyy.webhome.modules.manager.giftbagexchangerecord.service;

import com.baomidou.mybatisplus.service.IService;
import com.xmsy.server.zxyy.webhome.modules.manager.giftbagexchangerecord.entity.GiftBagExchangeRecordEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.user.entity.UserEntity;

import java.math.BigDecimal;


/**
 * 礼包兑换记录表
 *
 * @author axiong
 * @email xxxxx
 * @date 2019-07-18 15:14:51
 */
public interface GiftBagExchangeRecordService extends IService<GiftBagExchangeRecordEntity> {
	/**
	 * 添加一条礼包兑换记录
	 */
	public void addGiftBagExchangeRecord(UserEntity user, String exchangeCode, BigDecimal acountMoney);
}

