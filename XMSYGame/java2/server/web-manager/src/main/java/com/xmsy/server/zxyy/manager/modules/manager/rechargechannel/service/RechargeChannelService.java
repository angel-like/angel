package com.xmsy.server.zxyy.manager.modules.manager.rechargechannel.service;

import java.util.List;

import com.baomidou.mybatisplus.service.IService;
import com.xmsy.server.zxyy.manager.modules.manager.payconfig.entity.PayConfigFirstEntity;
import com.xmsy.server.zxyy.manager.modules.manager.rechargechannel.entity.RechargeChannelEntity;
import com.xmsy.server.zxyy.manager.modules.manager.rechargechannel.entity.RechargeChannelResultEntity;

/**
 * 支付渠道
 *
 * @author aleng
 * @email xxxxx
 * @date 2019-03-27 10:16:53
 */
public interface RechargeChannelService extends IService<RechargeChannelEntity> {

	/**
	 * 获取支付渠道，根据支付渠道类别
	 * 
	 * @param type
	 * @return
	 */
	RechargeChannelEntity getRechargeChannelByType(Long type);
	
	/**
	 * 获取首推支付公司
	 * 
	 * @param payId
	 * @param amount
	 * @return
	 */
	List<PayConfigFirstEntity> selectFirstRecommendForWeb();
	
	/**
	 * 获取首推支付公司
	 * 
	 * @param payId
	 * @param amount
	 * @return
	 */
	List<PayConfigFirstEntity> selectFirstRecommendForApp();

	/**
	 * 根据支付公司id和金额筛选对应的支付渠道列表
	 * 
	 * @param payId
	 * @param amount
	 * @return
	 */
	List<RechargeChannelResultEntity> selectChannelsByPayId(Long payId);

	RechargeChannelResultEntity selectByPayIdAndChannelId(Long id, Long channelId);
}
