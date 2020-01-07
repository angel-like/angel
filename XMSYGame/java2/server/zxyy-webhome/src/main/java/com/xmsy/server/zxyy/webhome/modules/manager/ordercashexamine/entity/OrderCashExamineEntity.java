package com.xmsy.server.zxyy.webhome.modules.manager.ordercashexamine.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.xmsy.server.zxyy.webhome.base.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 取款稽查表
 * 
 * @author aleng
 * @email xxxxx
 * @date 2019-01-31 16:25:31
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("order_cash_examine")
public class OrderCashExamineEntity extends BaseEntity<OrderCashExamineEntity> {
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
	 * 等级优惠打码倍数
	 */
	@JSONField(serialize = false)
	private BigDecimal hierarchyDiscountMultiple;
	/**
	 * 等级优惠打码(等级优惠金额*等级优惠打码倍数)
	 */
	private BigDecimal hierarchyDiscountBet;
	/**
	 * 等级放宽比例
	 */
	@JSONField(serialize = false)
	private BigDecimal hierarchyRelaxationRate;
	/**
	 * 等级放宽打码(等级正常打码数+等级优惠打码数)*等级放宽比例)
	 */
	private BigDecimal hierarchyRelaxationBet;
	/**
	 * 等级常态打码倍数
	 */
	private BigDecimal hierarchyNormalMultiple;
	/**
	 * 等级常态打码(存款金额*等级常态打码倍数)
	 */
	private BigDecimal hierarchyNormalBet;
	/**
	 * 总需要打码数(等级正常打码数+等级优惠打码数-等级放宽打码)
	 */
	private BigDecimal userNeedBet;
	/**
	 * 当前用户有效打码
	 */
	private BigDecimal userValidBet;
	/**
	 * 用户取款操作减少的需要打码
	 */
	private BigDecimal deductionNeedBet;
	/**
	 * 用户取款操作减少的有效打码
	 */
	private BigDecimal deductionValidBet;
	/**
	 * 是否结算（0：未审核，1：已审核过）
	 */
	@JSONField(serialize = false)
	private Boolean status;
	/**
	 * 等级行政费比例
	 */
	@JSONField(serialize = false)
	private BigDecimal hierarchyAdministrativeRate;
	/**
	 * 等级行政费(用户总余额*用户等级行政费比例)
	 */
	private BigDecimal hierarchyAdministrativeAmount;
	/**
	 * 已经被扣的行政费用(行政费被扣后总需要打码就要清0了，意味着扣过钱了剩余的余额就不用扣钱了)
	 */
	private BigDecimal deductionAdministrative;
	/**
	 * 取款用户id
	 */
	@JSONField(serialize = false)
	private Long userId;
	/**
	 * 取款用户账号
	 */
	@JSONField(serialize = false)
	private String userAccount;
	/**
	 * 充值订单号
	 */
	@JSONField(serialize = false)
	private String orderNo;
	/**
	 * 充值订单时间(充值订单生成稽查订单)
	 */
	@JSONField(serialize = false)
	private Date orderTime;
	/**
	 * 用户余额
	 */
	private BigDecimal userMoney;
}
