package com.xmsy.server.zxyy.schedule.entity;

import java.math.BigDecimal;

import lombok.Data;

/**
 * 用户下级总打码
 * 
 * @author xiaoliu
 * @email xxxxx
 * @date 2019-06-10
 */
@Data
public class RecommendationGradeEntity {
	/**
	 * 用户id
	 */
	private Long id;
	/**
	 * 名称
	 */
	private String name;
	/**
	 * 最小值
	 */
	private Long miniValue;
	/**
	 * 最大值
	 */
	private Long maxValue;
	/**
	 * 返利优惠
	 */
	private BigDecimal rewardRate;
}
