package com.xmsy.server.zxyy.manager.modules.manager.shopproduct.entity;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.xmsy.server.zxyy.manager.base.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 商城产品表
 * 
 * @author axiong
 * @email xxxxx
 * @date 2019-06-10 15:38:05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("shop_product")
public class ShopProductEntity extends BaseEntity<ShopProductEntity> {
	private static final long serialVersionUID = 1L;
	/**
	 * 道具名称id
	 */
	private Long sysPropId;
	/**
	 * 产品数量
	 */
	private Integer productNumber;
	/**
	 * 产品优惠价
	 */
	private Long productPrice;
	/**
	 * 产品总价
	 */
	private Long  productTotalPrice;
	/**
	 * 折扣
	 */
	private BigDecimal discount;
	/**
	 * 产品单价
	 */
	private Long productOnePrice;
	/**
	 * 是否上架（0表示下架，1表示上架）
	 */
	private Boolean sell;
	/**
	 * 道具名称
	 */
	@TableField(exist = false)
	private String sysPropName;
}
