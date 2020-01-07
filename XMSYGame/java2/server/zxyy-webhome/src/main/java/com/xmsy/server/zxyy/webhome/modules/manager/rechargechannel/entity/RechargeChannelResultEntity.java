package com.xmsy.server.zxyy.webhome.modules.manager.rechargechannel.entity;

import java.util.List;

import lombok.Data;

/**
 * 支付渠道
 * 
 * @author xiaoliu
 * @email xxxxx
 * @date 2019-03-27 10:16:53
 */
@Data
public class RechargeChannelResultEntity {

	/**
	 * id
	 */
	private Long id;
	/**
	 * 支付名称
	 */
	private String name;

	private String iconUrl;
	/**
	 * MD5
	 */
	private String iconMd5;
	/**
	 * 最高限制
	 */
	private Long highLimit;
	/**
	 * 支付方式id
	 */
	private Long channelId;
	
	/**
	 * 最低限制
	 */
	private Long lowLimit;
	
	private List<Long> amount;

}
