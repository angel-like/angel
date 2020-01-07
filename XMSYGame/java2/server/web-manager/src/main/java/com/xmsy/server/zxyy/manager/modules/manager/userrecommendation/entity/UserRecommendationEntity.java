package com.xmsy.server.zxyy.manager.modules.manager.userrecommendation.entity;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.xmsy.server.zxyy.manager.base.BaseEntity;
import com.xmsy.server.zxyy.manager.modules.manager.user.param.UserOperater;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 用户推荐码表
 * 
 * @author aleng
 * @email xxxxx
 * @date 2018-12-26 19:15:50
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("user_recommendation")
public class UserRecommendationEntity extends BaseEntity<UserRecommendationEntity> {
	private static final long serialVersionUID = 1L;
	/**
	 * 用户ID
	 */
	private Long userId;
	/**
	 * 用户账号
	 */
	private String userAccount;
	/**
	 * 推荐码
	 */
	private String recommendationCode;
	/**
	 * 邀请人数
	 */
	private Integer num;
	/**
	 * 总佣金
	 */
	private BigDecimal amount;
	/**
	 * 总金币
	 */
	private Long coin;
	/**
	 * 代理等级
	 */
	private Long agentHierarchyId;
	/**
	 * 代理等级名称
	 */
	@TableField(exist = false)
	private String agentHierarchyName;
	/**
	 * 推荐等级
	 */
	private Long recommendationHierarchyId;
	/**
	 * 推荐等级名称
	 */
	@TableField(exist = false)
	private Long recommendationHierarchyName;
	/**
	 * 推荐等级(true,开启团队权限)
	 */
	private Boolean teamEnable;
	/**
	 * 代理状态（1：是代理。0：禁用代理）
	 */
	@TableField(exist = false)
	private Boolean agentEnable;
	/**
	 * 操作内容
	 */
	@TableField(exist = false)
	private UserOperater userOperater;
}
