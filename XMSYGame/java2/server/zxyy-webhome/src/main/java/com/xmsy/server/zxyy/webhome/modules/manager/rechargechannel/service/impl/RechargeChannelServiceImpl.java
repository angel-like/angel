package com.xmsy.server.zxyy.webhome.modules.manager.rechargechannel.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xmsy.server.zxyy.webhome.cache.EhCacheName;
import com.xmsy.server.zxyy.webhome.modules.manager.payconfig.entity.PayConfigFirstEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.rechargechannel.dao.RechargeChannelDao;
import com.xmsy.server.zxyy.webhome.modules.manager.rechargechannel.entity.RechargeChannelEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.rechargechannel.entity.RechargeChannelResultEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.rechargechannel.service.RechargeChannelService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;

/**
 * .支付渠道配置
 * 
 * @author Administrator
 *
 */
@Service("rechargeChannelService")
public class RechargeChannelServiceImpl extends ServiceImpl<RechargeChannelDao, RechargeChannelEntity>
		implements RechargeChannelService {

	@Override
	@CacheEvict(value = EhCacheName.PAY_CHANNEL_CACHE, allEntries = true)
	public boolean insert(RechargeChannelEntity entity) {
		// TODO Auto-generated method stub
		return super.insert(entity);
	}

	@Override
	@CacheEvict(value = EhCacheName.PAY_CHANNEL_CACHE, allEntries = true)
	public boolean deleteById(Serializable id) {
		// TODO Auto-generated method stub
		return super.deleteById(id);
	}

	@Override
	@CacheEvict(value = EhCacheName.PAY_CHANNEL_CACHE, allEntries = true)
	public boolean updateById(RechargeChannelEntity entity) {
		// TODO Auto-generated method stub
		return super.updateById(entity);
	}

	@Override
	@Cacheable(cacheNames = EhCacheName.PAY_CHANNEL_CACHE, key = "#id", unless = "#result == null")
	public RechargeChannelEntity selectById(Serializable id) {
		// TODO Auto-generated method stub
		return super.selectById(id);
	}

	@Override
	@Cacheable(cacheNames = EhCacheName.PAY_CHANNEL_CACHE, key = "'channel'+#type", unless = "#result == null")
	public RechargeChannelEntity getRechargeChannelByType(Long type) {
		return selectOne(new EntityWrapper<>(new RechargeChannelEntity().setType(type)));
	}

	@Override
	@Cacheable(cacheNames = EhCacheName.PAY_CHANNEL_CACHE, key = "'channel_aliasName'+#aliasName", unless = "#result == null")
	public RechargeChannelEntity getRechargeChannelByAliasName(String aliasName) {
		return selectOne(new EntityWrapper<>(new RechargeChannelEntity().setAlias(aliasName)));
	}

	@Override
	public List<RechargeChannelResultEntity> selectChannelsByPayId(Long payId) {
		return baseMapper.selectChannelsByPayId(payId);
	}

	@Override
	public List<PayConfigFirstEntity> selectFirstRecommendForWeb() {
		// TODO Auto-generated method stub
		return baseMapper.selectFirstRecommendForWeb();
	}

	@Override
	public List<PayConfigFirstEntity> selectFirstRecommendForApp() {
		// TODO Auto-generated method stub
		return baseMapper.selectFirstRecommendForApp();
	}

	@Override
	public List<RechargeChannelResultEntity> selectChannelsByPayIdhierarchyId(Long payId, Long hierarchyId) {
		return baseMapper.selectChannelsByPayIdhierarchyId(payId,hierarchyId);
	}

}
