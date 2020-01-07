package com.xmsy.server.zxyy.webhome.modules.manager.proxyordertransactionrecord.entity;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotations.TableName;
import com.xmsy.server.zxyy.webhome.base.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * 代理交易订单表
 * 
 * @author ahui
 * @email xxxxx
 * @date 2019-08-05 17:11:01
 */
@Data
@EqualsAndHashCode(callSuper=false)
@Accessors(chain=true)
@TableName("proxy_order_transaction_record")
public class ProxyOrderTransactionRecordEntity extends BaseEntity<ProxyOrderTransactionRecordEntity> {
	private static final long serialVersionUID = 1L;
							/**
	 * 用户ID
	 */
    private Long userId;
			/**
	 * 代理账户
	 */
    private String proxyAccount;
			/**
	 * 交易类型
	 */
    private Integer type;
			/**
	 * 订单编号
	 */
    private String orderNo;
			/**
	 * 交易额
	 */
    private BigDecimal amount;
			/**
	 * 代理商余额
	 */
    private BigDecimal proxyBalance;
			/**
	 * 备注
	 */
    private String remake;
	}
