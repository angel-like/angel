package com.xmsy.server.zxyy.webhome.modules.manager.ordertakemoney.entity;

import lombok.Data;

/**
 * 充值订单批量锁定
 * 
 * @author xiaoliu
 * @email xxxxx
 * @date 2019-06-01 15:55:35
 */
@Data
public class OrderTakeMoneyLockingEntity {
	/**
	 * 操作人用户名
	 */
	private String sysUserAccount;
	/**
	 * 操作人id
	 */
	private Long sysUserId;
	/**
	 * 数量
	 */
	private int num;

}
