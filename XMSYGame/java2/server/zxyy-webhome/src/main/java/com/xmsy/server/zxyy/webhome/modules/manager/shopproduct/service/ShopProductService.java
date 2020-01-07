package com.xmsy.server.zxyy.webhome.modules.manager.shopproduct.service;

import com.baomidou.mybatisplus.service.IService;
import com.xmsy.server.zxyy.webhome.modules.manager.shopproduct.entity.ShopProductEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.user.entity.UserEntity;


/**
 * 商城产品表
 *
 * @author axiong
 * @email xxxxx
 * @date 2019-06-10 15:38:05
 */
public interface ShopProductService extends IService<ShopProductEntity> {
	/**
	 * 通过购买的道具：
	 * 		更新会员信息
	 * 		增加道具交易记录
	 * @param shopproduct
	 * @param user
	 * @param number
	 */
	void updateUserAndShopPropRecord(ShopProductEntity shopproduct,UserEntity user,Long number);
	/**
	 *	通过会员实体以及要兑换房卡数量：
	 *					修改用户信息
	 *					增加兑换记录（道具交易记录表）
	 * @param user
	 * @param number
	 */
	void exchangeUserAndShopPropRecord(UserEntity user,Long number);
}

