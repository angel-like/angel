package com.xmsy.server.zxyy.webhome.modules.manager.shoproomcardproportion.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xmsy.server.zxyy.webhome.modules.manager.shoproomcardproportion.dao.ShopRoomCardProportionDao;
import com.xmsy.server.zxyy.webhome.modules.manager.shoproomcardproportion.entity.ShopRoomCardProportionEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.shoproomcardproportion.service.ShopRoomCardProportionService;


@Service("shopRoomCardProportionService")
public class ShopRoomCardProportionServiceImpl extends ServiceImpl<ShopRoomCardProportionDao, ShopRoomCardProportionEntity> implements ShopRoomCardProportionService {

	@Override
	public List<Map<String, Object>> findProportion() {	
		return this.baseMapper.findProportion();
	}

	@Override
	public BigDecimal findOneProportion(ShopRoomCardProportionEntity shopRoomCardProportion) {
		return this.baseMapper.findOneProportion(shopRoomCardProportion);
	}


}
