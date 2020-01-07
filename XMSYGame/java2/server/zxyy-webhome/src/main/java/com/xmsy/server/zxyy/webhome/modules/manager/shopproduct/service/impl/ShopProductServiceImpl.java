package com.xmsy.server.zxyy.webhome.modules.manager.shopproduct.service.impl;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xmsy.server.zxyy.webhome.common.exception.RRException;
import com.xmsy.server.zxyy.webhome.common.utils.Constant;
import com.xmsy.server.zxyy.webhome.common.utils.ErrorCode;
import com.xmsy.server.zxyy.webhome.modules.app.event.PushService;
import com.xmsy.server.zxyy.webhome.modules.app.event.userinfo.UserInfoMessage;
import com.xmsy.server.zxyy.webhome.modules.manager.shopproduct.dao.ShopProductDao;
import com.xmsy.server.zxyy.webhome.modules.manager.shopproduct.entity.ShopProductEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.shopproduct.service.ShopProductService;
import com.xmsy.server.zxyy.webhome.modules.manager.shopproprecord.entity.ShopPropRecordEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.shoproomcardproportion.entity.ShopRoomCardProportionEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.shoproomcardproportion.service.ShopRoomCardProportionService;
import com.xmsy.server.zxyy.webhome.modules.manager.user.dao.UserDao;
import com.xmsy.server.zxyy.webhome.modules.manager.user.entity.UserEntity;
import com.xmsy.server.zxyy.webhome.utils.DateUtils;

import lombok.extern.slf4j.Slf4j;

@Transactional
@Slf4j
@Service("shopProductService")
public class ShopProductServiceImpl extends ServiceImpl<ShopProductDao, ShopProductEntity>
		implements ShopProductService {
	@Autowired
	private UserDao userDao;
	@Autowired
	private PushService pushService;
	@Autowired
	private ShopRoomCardProportionService shopRoomCardProportionService;

	@Override
	public void updateUserAndShopPropRecord(ShopProductEntity shopproduct, UserEntity user,Long number) {
		// TODO Auto-generated method stub
		// 判断购买的是哪个道具
		if (Constant.COIN_PROP_ID == shopproduct.getSysPropId()) {// 购买金币请联系客服购买
			throw new RRException(ErrorCode.UserErrorCode.USER_BUY_COIN.getErrMsg(),
					ErrorCode.UserErrorCode.USER_BUY_COIN.getCode());
		} else if (Constant.ROOMCART_PROP_ID == shopproduct.getSysPropId()) {// 购买房卡要判断金币是否充足
			// 判断用户金币是否足够购买
			if (user.getCoin() < (shopproduct.getProductPrice()*number)) {
				throw new RRException(ErrorCode.UserErrorCode.USER_COIN_NOT_ENOUGH.getErrMsg(),
						ErrorCode.UserErrorCode.USER_COIN_NOT_ENOUGH.getCode());
			}
			UserEntity userParam = new UserEntity();
			userParam.setId(user.getId());
			userParam.setCoin(number*shopproduct.getProductPrice() * -1);
			userParam.setRoomCard(number*Long.parseLong(shopproduct.getProductNumber().toString()));
			Integer i = userDao.updateUserWalletByUserId(userParam);
			if (i > 0) {
				// 添加道具交易记录表
				ShopPropRecordEntity shopPropRecord = new ShopPropRecordEntity();
				shopPropRecord.setSysPropId(shopproduct.getSysPropId());// 道具名称id
				int num=Integer.parseInt(String.valueOf(number));
				shopPropRecord.setPropNumber(num*shopproduct.getProductNumber());// 道具数量
				shopPropRecord.setType(Constant.PROP_USE_TYPE_0);// 类型0：购买 1：使用
				shopPropRecord.setProduceId(shopproduct.getId()); // 产品id
				shopPropRecord.setUserId(user.getId());// 会员id
				shopPropRecord.setUserAccount(user.getAccount());// 会员账号
				shopPropRecord.insert();
			}
			UserInfoMessage message = new UserInfoMessage(userParam, null);
			log.info("[购买房卡-推送消息] message {}", message);
			pushService.pushUserInfo(message);
		} else {// 购买未知道具异常
			throw new RRException(ErrorCode.UserErrorCode.USER_BUY_ERROR.getErrMsg(),
					ErrorCode.UserErrorCode.USER_BUY_ERROR.getCode());
		}
	}

	@Override
	public void exchangeUserAndShopPropRecord(UserEntity user, Long number) {
		BigDecimal proportion = shopRoomCardProportionService.findOneProportion(new ShopRoomCardProportionEntity().setEffectDate(DateUtils.getDateAddDay(0)));//距离当前时间最近的一条比例
		if(proportion==null || proportion.equals(BigDecimal.ZERO)) {
			throw new RRException(ErrorCode.ShopErrorCode.SHOP_EXCHANGE_PROPORTION.getErrMsg(),
					ErrorCode.ShopErrorCode.SHOP_EXCHANGE_PROPORTION.getCode());
		}
		if(user.getCoin()<proportion.multiply(new BigDecimal(number*Constant.DB_COIN_RATE)).longValue()) {
			throw new RRException(ErrorCode.UserErrorCode.USER_COIN_NOT_ENOUGH.getErrMsg(),
					ErrorCode.UserErrorCode.USER_COIN_NOT_ENOUGH.getCode());
		}
		UserEntity userParam = new UserEntity();
		userParam.setId(user.getId());
		userParam.setCoin(proportion.multiply(new BigDecimal(Constant.DB_COIN_RATE*number*-1)).longValue());
		userParam.setRoomCard(number);
		Integer i = userDao.updateUserWalletByUserId(userParam);
		if(i>0) {
			// 添加金币兑换房卡表（道具交易记录表）
			ShopPropRecordEntity shopPropRecord = new ShopPropRecordEntity();
			shopPropRecord.setSysPropId(Constant.ROOMCART_PROP_ID);// 道具名称id(0表示在道具列表里不存在)
			shopPropRecord.setPropNumber(Integer.parseInt(String.valueOf(number)));// 兑换的道具数量
			shopPropRecord.setType(Constant.PROP_USE_TYPE_0);// 类型0：购买 1：使用
			shopPropRecord.setProduceId(0L); // 产品id(0表示在商城产品表里不存在)
			shopPropRecord.setUserId(user.getId());// 会员id
			shopPropRecord.setUserAccount(user.getAccount());// 会员账号
			shopPropRecord.insert();
		}
		UserInfoMessage message = new UserInfoMessage(userParam, null);
		log.info("[兑换房卡-推送消息] message {}", message);
		pushService.pushUserInfo(message);
	}

}
