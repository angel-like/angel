package com.xmsy.server.zxyy.manager.modules.manager.shoproomcardproportion.service;

import java.util.List;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.baomidou.mybatisplus.service.IService;
import com.xmsy.server.zxyy.manager.modules.manager.shoproomcardproportion.entity.ShopRoomCardProportionEntity;


/**
 * 金币兑换房卡比例表
 *
 * @author axiong
 * @email xxxxx
 * @date 2019-07-05 17:00:34
 */
public interface ShopRoomCardProportionService extends IService<ShopRoomCardProportionEntity> {
	/**
	 * 通过时间范围   查询所有的金币兑换房卡数据
	 * 降序
	 * @param ShopRoomCardProportion
	 * @return
	 */
	public List<ShopRoomCardProportionEntity> findShopRoomCardProportion(Pagination page,ShopRoomCardProportionEntity shopRoomCardProportion);
}

