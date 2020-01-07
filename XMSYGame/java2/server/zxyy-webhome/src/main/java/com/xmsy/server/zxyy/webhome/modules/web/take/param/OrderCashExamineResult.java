package com.xmsy.server.zxyy.webhome.modules.web.take.param;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Data;

@Data
public class OrderCashExamineResult implements Serializable {

	private static final long serialVersionUID = 1L;
	/**
	 * 存款金额
	 */
	private Long rechargeAmount;
	/**
	 * 等级优惠金额(存款金额*等级优惠比例)
	 */
	private BigDecimal hierarchyDiscountAmount;
	/**
	 * 等级优惠打码(等级优惠金额*等级优惠打码倍数)
	 */
	private BigDecimal hierarchyDiscountBet;
	/**
	 * 等级放宽打码(等级正常打码数+等级优惠打码数)*等级放宽比例)
	 */
	private BigDecimal hierarchyRelaxationBet;
	/**
	 * 等级常态打码(存款金额*等级常态打码倍数)
	 */
	private BigDecimal hierarchyNormalBet;
	/**
	 * 总需要打码数(等级正常打码数+等级优惠打码数+上次缺少打码数-上次剩余打码-等级放宽打码)
	 */
	private BigDecimal userNeedBet;
	/**
	 * 当前用户有效打码
	 */
	private BigDecimal userValidBet;
	/**
	 * 等级行政费(用户总余额*用户等级行政费比例)
	 */
	private BigDecimal hierarchyAdministrativeAmount;

}
