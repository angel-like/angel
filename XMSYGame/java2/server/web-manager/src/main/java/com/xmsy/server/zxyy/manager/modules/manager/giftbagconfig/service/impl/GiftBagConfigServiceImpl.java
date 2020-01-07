package com.xmsy.server.zxyy.manager.modules.manager.giftbagconfig.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;


import com.xmsy.server.zxyy.manager.modules.manager.giftbagconfig.dao.GiftBagConfigDao;
import com.xmsy.server.zxyy.manager.modules.manager.giftbagconfig.entity.GiftBagConfigEntity;
import com.xmsy.server.zxyy.manager.modules.manager.giftbagconfig.service.GiftBagConfigService;


@Service("giftBagConfigService")
public class GiftBagConfigServiceImpl extends ServiceImpl<GiftBagConfigDao, GiftBagConfigEntity> implements GiftBagConfigService {

	@Override
	public List<GiftBagConfigEntity> findPageList(GiftBagConfigEntity giftBagConfigEntity, Pagination page) {

		return this.baseMapper.findPageList(giftBagConfigEntity, page);
	}


}
