package com.xmsy.server.zxyy.manager.modules.manager.giftcoinconfig.service.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xmsy.server.zxyy.manager.cache.EhCacheName;
import com.xmsy.server.zxyy.manager.modules.manager.giftcoinconfig.dao.GiftCoinConfigDao;
import com.xmsy.server.zxyy.manager.modules.manager.giftcoinconfig.entity.GiftCoinConfigEntity;
import com.xmsy.server.zxyy.manager.modules.manager.giftcoinconfig.service.GiftCoinConfigService;

@Service("giftCoinConfigService")
public class GiftCoinConfigServiceImpl extends ServiceImpl<GiftCoinConfigDao, GiftCoinConfigEntity>
		implements GiftCoinConfigService {

	@Override
	@CacheEvict(value = EhCacheName.GIFT_COIN_CONFIG_CACHE, allEntries = true)
	public boolean insert(GiftCoinConfigEntity entity) {
		return super.insert(entity);
	}

	@Override
	@CacheEvict(value = EhCacheName.GIFT_COIN_CONFIG_CACHE, allEntries = true)
	public boolean updateById(GiftCoinConfigEntity entity) {
		return super.updateById(entity);
	}

	@Override
	@CacheEvict(value = EhCacheName.GIFT_COIN_CONFIG_CACHE, allEntries = true)
	public boolean deleteById(Serializable id) {
		return super.deleteById(id);
	}

	@Override
	public GiftCoinConfigEntity selectById(Serializable id) {
		return super.selectById(id);
	}

	@Override
	public List<GiftCoinConfigEntity> getEncouragementConfig() {
		List<GiftCoinConfigEntity> list = baseMapper
				.selectList(new EntityWrapper<GiftCoinConfigEntity>(new GiftCoinConfigEntity().setEnable(true))
						.orderBy("num", true));
		if (CollectionUtils.isEmpty(list)) {
			return null;
		} else {
			return list;
		}
	}

}
