package com.xmsy.server.zxyy.webhome.modules.manager.giftbagexchangerecord.service.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xmsy.server.zxyy.webhome.common.exception.RRException;
import com.xmsy.server.zxyy.webhome.common.utils.ErrorCode;
import com.xmsy.server.zxyy.webhome.modules.manager.giftbagexchangerecord.dao.GiftBagExchangeRecordDao;
import com.xmsy.server.zxyy.webhome.modules.manager.giftbagexchangerecord.entity.GiftBagExchangeRecordEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.giftbagexchangerecord.service.GiftBagExchangeRecordService;
import com.xmsy.server.zxyy.webhome.modules.manager.user.entity.UserEntity;
import com.xmsy.server.zxyy.webhome.utils.DateUtils;

import java.math.BigDecimal;


@Service("giftBagExchangeRecordService")
public class GiftBagExchangeRecordServiceImpl extends ServiceImpl<GiftBagExchangeRecordDao, GiftBagExchangeRecordEntity> implements GiftBagExchangeRecordService {

	@Override
	public void addGiftBagExchangeRecord(UserEntity user, String exchangeCode, BigDecimal acountMoney) {
		GiftBagExchangeRecordEntity  giftBagRecord= new GiftBagExchangeRecordEntity()
		.setUserId(user.getId())
		.setUserAccount(user.getAccount())
		.setUserName(user.getUserName())
		.setExchangeCode(exchangeCode).setAcountMoney(acountMoney)
		.setExchangeTime(DateUtils.getToday());
		if(this.baseMapper.insert(giftBagRecord)<=0) {
			throw new RRException(ErrorCode.ShopErrorCode.INSERT_GIFT_EXCHANGE_RECORD.getErrMsg(),
					ErrorCode.ShopErrorCode.INSERT_GIFT_EXCHANGE_RECORD.getCode());
		}
		
	}


}
