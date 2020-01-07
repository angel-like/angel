package com.xmsy.server.zxyy.webhome.modules.manager.giftcoinconfig.service.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xmsy.server.zxyy.webhome.cache.EhCacheName;
import com.xmsy.server.zxyy.webhome.common.utils.Constant;
import com.xmsy.server.zxyy.webhome.modules.manager.giftcoinconfig.dao.GiftCoinConfigDao;
import com.xmsy.server.zxyy.webhome.modules.manager.giftcoinconfig.entity.GiftCoinConfigEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.giftcoinconfig.service.GiftCoinConfigService;

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
	@Cacheable(cacheNames = EhCacheName.GIFT_COIN_CONFIG_CACHE, key = "#id", unless = "#result == null")
	public GiftCoinConfigEntity selectById(Serializable id) {
		return super.selectById(id);
	}

	@Override
	@Cacheable(cacheNames = EhCacheName.GIFT_COIN_CONFIG_CACHE, key = "'EncouragementGiftConfig'", unless = "#result == null || #result.size() == 0")
	public List<GiftCoinConfigEntity> getEncouragementConfig() {
		List<GiftCoinConfigEntity> list = baseMapper
				.selectList(new EntityWrapper<GiftCoinConfigEntity>(new GiftCoinConfigEntity()
						.setType(Constant.UserActivityAwardType.ENCOURAGE.getValue())
						.setEnable(true))
						.orderBy("num", true));
		
		if (CollectionUtils.isEmpty(list)) {
			return null;
		} else {
			return list;
		}
	}

}
