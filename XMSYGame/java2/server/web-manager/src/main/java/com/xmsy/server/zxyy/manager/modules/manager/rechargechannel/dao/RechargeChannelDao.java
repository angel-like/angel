package com.xmsy.server.zxyy.manager.modules.manager.rechargechannel.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
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
@Mapper
public interface RechargeChannelDao extends BaseMapper<RechargeChannelEntity> {
	
	/**
	 * 获取首推的支付公司对应的微信和支付宝渠道
	 * @param id
	 * @param amount
	 * @return
	 */
	List<PayConfigFirstEntity> selectFirstRecommendForWeb();
	
	/**
	 * 获取首推的支付公司对应的微信和支付宝渠道
	 * @param id
	 * @param amount
	 * @return
	 */
	List<PayConfigFirstEntity> selectFirstRecommendForApp();

	/**
	 * 根据支付公司id已经支付金额筛选渠道列表
	 * @param id
	 * @param amount
	 * @return
	 */
	List<RechargeChannelResultEntity> selectChannelsByPayId(@Param("id") Long id);
	RechargeChannelResultEntity  selectByPayIdAndChannelId(@Param("id") Long id ,@Param("channelId")  Long channelId);
}
