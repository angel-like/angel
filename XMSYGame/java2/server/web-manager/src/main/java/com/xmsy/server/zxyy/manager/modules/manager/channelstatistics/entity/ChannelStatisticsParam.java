package com.xmsy.server.zxyy.manager.modules.manager.channelstatistics.entity;


import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 渠道统计参数
 * 
 * @author axiong
 * @email xxxxx
 * @date 2019-07-03 17:05:09
 */
@Data
@EqualsAndHashCode(callSuper=false)//注解会生成equals(Object other) 和 hashCode()方法。 
@Accessors(chain=true)// 注解用来配置lombok如何产生和显示getters和setters的方法
public class ChannelStatisticsParam {
	
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 渠道名称
	 */
	private String channelName;
	/**
	 * 渠道代码
	 */
	private String channelCode;
	/**
	 * 会员数
	 */
	private Long userNum;
	/**
	 * 打码量(有效下注)
	 */
	private Long validBet;
	/**
	 * 充值总额
	 */
	private Long amount;
	
	/**
	 * 取款总额
	 */
	private Long takeAmount;
	// =======================查询调用==================
	/**
	 * 开始时间
	 */
	private String startTime;
	/**
	 * 结束时间
	 */
	private String endTime;
}
