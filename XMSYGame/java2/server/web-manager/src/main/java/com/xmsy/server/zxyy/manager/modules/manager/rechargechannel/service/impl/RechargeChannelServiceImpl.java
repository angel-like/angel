package com.xmsy.server.zxyy.manager.modules.manager.rechargechannel.service.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xmsy.server.zxyy.manager.cache.EhCacheName;
import com.xmsy.server.zxyy.manager.modules.manager.payconfig.entity.PayConfigFirstEntity;
import com.xmsy.server.zxyy.manager.modules.manager.rechargechannel.dao.RechargeChannelDao;
import com.xmsy.server.zxyy.manager.modules.manager.rechargechannel.entity.RechargeChannelEntity;
import com.xmsy.server.zxyy.manager.modules.manager.rechargechannel.entity.RechargeChannelResultEntity;
import com.xmsy.server.zxyy.manager.modules.manager.rechargechannel.service.RechargeChannelService;

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
	public RechargeChannelEntity selectById(Serializable id) {
		// TODO Auto-generated method stub
		return super.selectById(id);
	}

	@Override
	public RechargeChannelEntity getRechargeChannelByType(Long type) {
		return selectOne(new EntityWrapper<RechargeChannelEntity>(new RechargeChannelEntity().setType(type)));
	}

	@Override
	public List<RechargeChannelResultEntity> selectChannelsByPayId(Long payId) {
		return baseMapper.selectChannelsByPayId(payId);
	}

	@Override
	public RechargeChannelResultEntity selectByPayIdAndChannelId(Long id, Long channelId) {
		return  baseMapper.selectByPayIdAndChannelId(id,channelId);
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

}
