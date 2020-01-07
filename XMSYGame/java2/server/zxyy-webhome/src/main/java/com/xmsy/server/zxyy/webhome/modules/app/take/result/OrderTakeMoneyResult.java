package com.xmsy.server.zxyy.webhome.modules.app.take.result;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;
import com.xmsy.server.zxyy.webhome.common.utils.Constant.OrderStatus;
import com.xmsy.server.zxyy.webhome.common.utils.Constant.TakeMoneyType;

/**
 * 取款记录
 * 
 * @author aleng
 * @email xxxxx
 * @date 2019-01-28 15:26:30
 */
public class OrderTakeMoneyResult implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 订单号
	 */
	private String orderNo;
	/**
	 * 取款金额
	 */
	private Long amount;
	/**
	 * 取款佣金
	 */
	private Long commission;
	/**
	 * 手续费
	 */
	private BigDecimal poundage;
	/**
	 * 状态(0:未审核，1：失败，2.完成)
	 */
	@JSONField(serialize = false)
	private Integer status;
	/**
	 * 状态(0:账号取现。1：佣金取现,)
	 */
	@JSONField(serialize = false)
	private Integer type;
	/**
	 * 时间
	 */
	private Date time;
	/**
	 * 状态(0:未审核，1：失败，2.完成)
	 */
	@SuppressWarnings("unused")
	private String statusStr;

	public String getStatusStr() {
		if(this.status==4) {
			return "订单审核中";
		}
		return OrderStatus.getOrderstatusmap().get(this.status);
	}

	public Long getAmount() {
		if (TakeMoneyType.COMMISSION.getValue() == this.type) {
			return 0L;
		}
		return amount;
	}

	public Long getCommission() {
		if (TakeMoneyType.TAKE_MONEY.getValue() == this.type) {
			return 0L;
		}
		return commission;
	}

	public void setCommission(Long commission) {
		this.commission = commission;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public void setAmount(Long amount) {
		this.amount = amount;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public void setStatusStr(String statusStr) {
		this.statusStr = statusStr;
	}

	public BigDecimal getPoundage() {
		return poundage;
	}

	public void setPoundage(BigDecimal poundage) {
		this.poundage = poundage;
	}

}
