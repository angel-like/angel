package com.xmsy.server.zxyy.manager.modules.manager.giftbagexchangerecord.service;

import java.util.List;

import com.baomidou.mybatisplus.service.IService;
import com.xmsy.server.zxyy.manager.modules.manager.giftbagexchangerecord.entity.GiftBagExchangeRecordEntity;
import com.xmsy.server.zxyy.manager.modules.manager.giftbagexchangerecord.entity.GiftBagExchangeRecordParam;


/**
 * 礼包兑换记录表
 *
 * @author axiong
 * @email xxxxx
 * @date 2019-07-18 15:14:51
 */
public interface GiftBagExchangeRecordService extends IService<GiftBagExchangeRecordEntity> {
	//查询时间范围内的兑换总额
	List<GiftBagExchangeRecordParam> getTotalExchangeAmount(String startTime,String endTime);

}

