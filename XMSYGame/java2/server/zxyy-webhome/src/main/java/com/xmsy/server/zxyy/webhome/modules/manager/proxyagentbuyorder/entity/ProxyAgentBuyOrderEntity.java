package com.xmsy.server.zxyy.webhome.modules.manager.proxyagentbuyorder.entity;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotations.TableName;
import com.xmsy.server.zxyy.webhome.base.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * 代理购买订单列表
 * 
 * @author ahui
 * @email xxxxx
 * @date 2019-08-02 10:09:16
 */
@Data
@EqualsAndHashCode(callSuper=false)
@Accessors(chain=true)
@TableName("proxy_agent_buy_order")
public class ProxyAgentBuyOrderEntity extends BaseEntity<ProxyAgentBuyOrderEntity> {
	private static final long serialVersionUID = 1L;
						/**
	 * 代理账户
	 */
    private String proxyAccount;
			/**
	 * 代理名称
	 */
    private String proxyName;
			/**
	 * 购买金币
	 */
    private Long buyCoins;
			/**
	 * 折扣
	 */
    private BigDecimal discount;
			/**
	 * 优惠金币
	 */
    private Long disGoldCoins;
			/**
	 * 获得的总金币
	 */
    private Long getTatolCoins;
	}
