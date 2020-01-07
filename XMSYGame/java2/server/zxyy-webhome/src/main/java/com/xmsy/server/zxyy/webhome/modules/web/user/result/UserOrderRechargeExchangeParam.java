package com.xmsy.server.zxyy.webhome.modules.web.user.result;

import com.baomidou.mybatisplus.annotations.TableField;
import com.xmsy.server.zxyy.webhome.common.utils.Constant;

import lombok.Data;

/**
 * .官网充值记录
 * 
 * @author Administrator
 *
 */
@Data
public class UserOrderRechargeExchangeParam {

	
	// 充值时间
	private String createTime;
	// 到账时间
	private String rechargeTime;
	// 充值金额
	private String amount;
	// 状态
	private Integer status;
	// 订单号
	private String orderNo;
	// 充值类型
	private Integer rechargeType;
	// 充值终端
	private String rechargeTerminal;
	// 优惠金额
	private String discountAmount;
	

	// 状态中文
	@TableField(exist=false)
	private String statusStr;
	// 充值类型中文
	@TableField(exist=false)
	private String rechargeTypeStr;
	
	
	public String getStatusStr() {
		if(status==Constant.OrderStatus.COMPLETE.getValue()) {
			return Constant.OrderStatus.COMPLETE.getName();
		}
		if(status==Constant.OrderStatus.REVOKE.getValue()) {
			return Constant.OrderStatus.REVOKE.getName();			
		}
		if(status==Constant.OrderStatus.CANCEL.getValue()) {
			return Constant.OrderStatus.CANCEL.getName();
		}
		if(status==Constant.OrderStatus.UNCONFIRMED.getValue()) {
			return Constant.OrderStatus.UNCONFIRMED.getName();
		}
		return "";
	}
	public String getRechargeTypeStr() {
		
		if(rechargeType==Constant.RechargeType.ADMIN.getValue()) {
			return Constant.RechargeType.ADMIN.getName();
		}
		if(rechargeType==Constant.RechargeType.BANK.getValue()) {
			return Constant.RechargeType.BANK.getName();			
		}
		if(rechargeType==Constant.RechargeType.THIRD.getValue()) {
			return Constant.RechargeType.THIRD.getName();
		}
		return "";
	}

}
