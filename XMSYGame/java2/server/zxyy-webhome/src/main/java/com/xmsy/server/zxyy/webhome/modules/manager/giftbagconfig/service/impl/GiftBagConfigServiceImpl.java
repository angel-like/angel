package com.xmsy.server.zxyy.webhome.modules.manager.giftbagconfig.service.impl;



import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xmsy.server.zxyy.webhome.common.exception.RRException;
import com.xmsy.server.zxyy.webhome.common.utils.ErrorCode;
import com.xmsy.server.zxyy.webhome.modules.manager.giftbagconfig.dao.GiftBagConfigDao;
import com.xmsy.server.zxyy.webhome.modules.manager.giftbagconfig.entity.GiftBagConfigEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.giftbagconfig.service.GiftBagConfigService;
import com.xmsy.server.zxyy.webhome.modules.manager.giftbagexchangerecord.entity.GiftBagExchangeRecordEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.giftbagexchangerecord.service.GiftBagExchangeRecordService;
import com.xmsy.server.zxyy.webhome.modules.manager.user.entity.UserEntity;
import com.xmsy.server.zxyy.webhome.utils.DateUtils;

@Service("giftBagConfigService")
public class GiftBagConfigServiceImpl extends ServiceImpl<GiftBagConfigDao, GiftBagConfigEntity>
		implements GiftBagConfigService {
	@Autowired
    private GiftBagExchangeRecordService giftBagExchangeRecordService;

	@Override
	public void verificationExchangeCode(GiftBagConfigEntity giftBagConfigEntity,UserEntity user) {
		// 1.判断兑换码是否存在
		if (null == giftBagConfigEntity) {
			throw new RRException(ErrorCode.ShopErrorCode.GIFT_CONFIT_ERROR.getErrMsg(),
					ErrorCode.ShopErrorCode.GIFT_CONFIT_ERROR.getCode());
		}
		// 2.判断兑换码状态是否使用
		if (!giftBagConfigEntity.getExchange()) {
			throw new RRException(ErrorCode.ShopErrorCode.GIFT_EXCHANGE_CODE_LOSE_INVALID.getErrMsg(),
					ErrorCode.ShopErrorCode.GIFT_EXCHANGE_CODE_LOSE_INVALID.getCode());
		}
		// 3.(总)兑换次数是否足够兑换
		if (giftBagConfigEntity.getExchangeNum() <= 0) {
			throw new RRException(ErrorCode.ShopErrorCode.GIFT_EXCHANGE_NUM_ERROR.getErrMsg(),
					ErrorCode.ShopErrorCode.GIFT_EXCHANGE_NUM_ERROR.getCode());
		}
		// 4.时间校验
		if (giftBagConfigEntity.getStartTime().getTime() > System.currentTimeMillis()) {//生效时间校验
			throw new RRException(ErrorCode.ShopErrorCode.GIFT_EXCHANGE_CODE_EFFECT_INVALID.getErrMsg(),
					ErrorCode.ShopErrorCode.GIFT_EXCHANGE_CODE_EFFECT_INVALID.getCode());
		}
		if (giftBagConfigEntity.getEndTime() != null
				&& giftBagConfigEntity.getEndTime().getTime() < System.currentTimeMillis()) {// 失效时间校验
			throw new RRException(ErrorCode.ShopErrorCode.GIFT_EXCHANGE_CODE_LOSE_INVALID.getErrMsg(),
					ErrorCode.ShopErrorCode.GIFT_EXCHANGE_CODE_LOSE_INVALID.getCode());
		}
		// 5.兑换账户不为空，为空就不用校验
		if (giftBagConfigEntity.getExchangeAccount() != null && !giftBagConfigEntity.getExchangeAccount().equals("")) {
			if (giftBagConfigEntity.getExchangeAccount().indexOf(user.getAccount())==-1) {// 校验兑换账户是否正确
				throw new RRException(ErrorCode.ShopErrorCode.GIFT_EXCHANGE_ACCOUNT_ERROR.getErrMsg(),
						ErrorCode.ShopErrorCode.GIFT_EXCHANGE_ACCOUNT_ERROR.getCode());
			}
		}
		// 6.单个用户限制次数
		if (giftBagConfigEntity.getLimitTimesUser()) {// 6.1多次
			//获取在周期内的 最后兑换期限
			Date date = DateUtils.addDateDays(giftBagConfigEntity.getStartTime(),giftBagConfigEntity.getPeriod());
			//6.1.1是否在周期内        不在周期内不能兑换
			if(date.getTime()<System.currentTimeMillis()) {
				throw new RRException(ErrorCode.ShopErrorCode.GIFT_EXCHANGE_TIME_OUT_ERROR.getErrMsg(),
						ErrorCode.ShopErrorCode.GIFT_EXCHANGE_TIME_OUT_ERROR.getCode());
			}
			//6.1.2查询用户在周期内已经兑换了几次
			EntityWrapper<GiftBagExchangeRecordEntity> entityWrapper = new EntityWrapper<GiftBagExchangeRecordEntity>(
					new GiftBagExchangeRecordEntity()
					.setExchangeCode(giftBagConfigEntity.getExchangeCode())
					.setUserId(user.getId()));
			entityWrapper.le("exchange_time", date);
			int count = giftBagExchangeRecordService.selectCount(entityWrapper);
			if(count>=giftBagConfigEntity.getReceiveTimes()) {
				throw new RRException(ErrorCode.ShopErrorCode.GIFT_EXCHANGE_NUM_RECORD.getErrMsg(),
						ErrorCode.ShopErrorCode.GIFT_EXCHANGE_NUM_RECORD.getCode());
			}
		} else {// 6.2单次
			int count = giftBagExchangeRecordService.selectCount(new EntityWrapper<GiftBagExchangeRecordEntity>(
					new GiftBagExchangeRecordEntity()
					.setExchangeCode(giftBagConfigEntity.getExchangeCode())
					.setUserId(user.getId())));
			if (count>=1) {
				throw new RRException(ErrorCode.ShopErrorCode.GIFT_EXCHANGE_RECORD.getErrMsg(),
						ErrorCode.ShopErrorCode.GIFT_EXCHANGE_RECORD.getCode());
			}
		}
		

	}

	@Override
	public void updateGiftBagConfig(Long id, Integer exchangeNum) {
		GiftBagConfigEntity updateEntity = new GiftBagConfigEntity();
		updateEntity.setId(id);
		updateEntity.setExchangeNum(exchangeNum-1);//剩余总数量减去1
		if(exchangeNum-1<=0) {//如果总领取数量为零，关闭该兑换码
			updateEntity.setExchange(false);
		}
		if(this.baseMapper.updateById(updateEntity)<=0) {
			throw new RRException(ErrorCode.ShopErrorCode.UPDATE_GIFT_CONFIG_ERROR.getErrMsg(),
					ErrorCode.ShopErrorCode.UPDATE_GIFT_CONFIG_ERROR.getCode());
		}
	}

}
