package com.xmsy.server.zxyy.webhome.modules.manager.cashpriceconfig.entity;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotations.TableName;
import com.xmsy.server.zxyy.webhome.base.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * 现金价格预设配置表（提供给用户充值、提现的预设金额）
 * 
 * @author adu
 * @email xxxxx
 * @date 2019-02-18 10:27:10
 */
@Data
@EqualsAndHashCode(callSuper=false)
@Accessors(chain=true)
@TableName("cash_price_config")
public class CashPriceConfigEntity extends BaseEntity<CashPriceConfigEntity> {
	private static final long serialVersionUID = 1L;
							/**
	 * 售价
	 */
    private BigDecimal price;
			/**
	 * 单价
	 */
    private BigDecimal unitPrice;
			/**
	 * 类型   0:充值 1：提现
	 */
    private Integer type;
			/**
	 * 是否展示
	 */
    private Boolean enable;
	}
