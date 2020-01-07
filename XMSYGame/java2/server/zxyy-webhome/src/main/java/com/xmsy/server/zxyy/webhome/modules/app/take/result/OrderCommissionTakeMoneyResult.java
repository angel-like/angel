package com.xmsy.server.zxyy.webhome.modules.app.take.result;

import java.math.BigDecimal;

import lombok.Data;

/**
 * 取款
 * 
 * @author xiaoliu
 * @email xxxxx
 * @date 2019-06-18
 */
@Data
public class OrderCommissionTakeMoneyResult {
	/**
	 * 时间
	 */
	private String takeTime;

	/**
	 * 金额
	 */
	private BigDecimal money;
	/**
	 * 状态(0:未审核，1：失败，2.完成)
	 */
	private Integer status;

}
