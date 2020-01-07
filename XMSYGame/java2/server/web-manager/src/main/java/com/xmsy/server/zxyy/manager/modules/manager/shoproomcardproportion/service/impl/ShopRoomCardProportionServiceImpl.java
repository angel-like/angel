package com.xmsy.server.zxyy.manager.modules.manager.shoproomcardproportion.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xmsy.server.zxyy.manager.modules.manager.shoproomcardproportion.dao.ShopRoomCardProportionDao;
import com.xmsy.server.zxyy.manager.modules.manager.shoproomcardproportion.entity.ShopRoomCardProportionEntity;
import com.xmsy.server.zxyy.manager.modules.manager.shoproomcardproportion.service.ShopRoomCardProportionService;


@Service("shopRoomCardProportionService")
public class ShopRoomCardProportionServiceImpl extends ServiceImpl<ShopRoomCardProportionDao, ShopRoomCardProportionEntity> implements ShopRoomCardProportionService {

	@Override
	public List<ShopRoomCardProportionEntity> findShopRoomCardProportion(Pagination page,
			ShopRoomCardProportionEntity ShopRoomCardProportion) {
		return this.baseMapper.findShopRoomCardProportion(page,ShopRoomCardProportion);
	}

}
