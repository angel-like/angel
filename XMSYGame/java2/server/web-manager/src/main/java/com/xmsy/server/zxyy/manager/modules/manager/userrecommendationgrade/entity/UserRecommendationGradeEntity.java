package com.xmsy.server.zxyy.manager.modules.manager.userrecommendationgrade.entity;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotations.TableName;
import com.xmsy.server.zxyy.manager.base.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 用户推荐等级
 * 
 * @author aleng
 * @email xxxxx
 * @date 2019-06-06 15:36:01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("user_recommendation_grade")
public class UserRecommendationGradeEntity extends BaseEntity<UserRecommendationGradeEntity> {
	private static final long serialVersionUID = 1L;
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
