package com.xmsy.server.zxyy.webhome.modules.manager.userhierarchy.entity;

import java.math.BigDecimal;
import java.util.List;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.xmsy.server.zxyy.webhome.base.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 用户层级表
 * 
 * @author aleng
 * @email xxxxx
 * @date 2019-01-02 11:57:53
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("user_hierarchy")
public class UserHierarchyEntity extends BaseEntity<UserHierarchyEntity> {
	
	private static final long serialVersionUID = 1L;
	/**
	 * 层级名称
	 */
	private String name;
	/**
	 * 层级属性（1为默认层级）
	 */
	private Boolean type;
	/**
	 * 下注超过该比例则用户下回充值的时候按照用户余额计算为打码数，用户打码未超过该比例的按照用户打码规则计算为打码的数量
	 */
	private BigDecimal betRate;
	/**
	 * 充值打码倍数
	 */
	private BigDecimal rechargeMultiple;
	/**
	 * 优惠打码倍数
	 */
	private BigDecimal discountMultiple;
	/**
	 * 打码放宽比例
	 */
	private BigDecimal relaxationRate;
	/**
	 * 行政费率
	 */
	private BigDecimal administrativeRate;
	/**
	 * 是否开放vip权限
	 */
	private Boolean vipEnable;
	
	/**
	 * 层级类型0：正常  1：风控层级
	 */
	private Integer hierarchyType;
	
	/**
	 * 游戏胜率
	 */
	private BigDecimal gameRate;
	
	/**
	 * 是否开放vip权限
	 */
	@TableField(exist=false)
	private List<Long> gameIds;
	
	
	
}