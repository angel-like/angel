package com.xmsy.server.zxyy.manager.modules.manager.giftbagexchangerecord.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;


import com.xmsy.server.zxyy.manager.modules.manager.giftbagexchangerecord.dao.GiftBagExchangeRecordDao;
import com.xmsy.server.zxyy.manager.modules.manager.giftbagexchangerecord.entity.GiftBagExchangeRecordEntity;
import com.xmsy.server.zxyy.manager.modules.manager.giftbagexchangerecord.entity.GiftBagExchangeRecordParam;
import com.xmsy.server.zxyy.manager.modules.manager.giftbagexchangerecord.service.GiftBagExchangeRecordService;


@Service("giftBagExchangeRecordService")
public class GiftBagExchangeRecordServiceImpl extends ServiceImpl<GiftBagExchangeRecordDao, GiftBagExchangeRecordEntity> implements GiftBagExchangeRecordService {

	@Override
	public List<GiftBagExchangeRecordParam> getTotalExchangeAmount(String startTime, String endTime) {
		return this.baseMapper.getTotalExchangeAmount(startTime,endTime);
	}


}
