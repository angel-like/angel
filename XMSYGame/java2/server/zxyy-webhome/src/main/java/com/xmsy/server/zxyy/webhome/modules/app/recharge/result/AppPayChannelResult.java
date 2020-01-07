package com.xmsy.server.zxyy.webhome.modules.app.recharge.result;

import java.util.List;

import com.xmsy.server.zxyy.webhome.modules.manager.rechargeamount.entity.RechargeAmountEntity;

import lombok.Data;

/**
 * .扫码支付方式参数定义
 * 
 * @author Administrator
 *
 */
@Data
public class AppPayChannelResult {
	
	/**
	 * 支付id
	 */
	private Long payId;

	/**
	 * 渠道id
	 */
	private Long channelId;
	
	/**
	 * 层级id
	 */
	private Long hierarchyId;
	
	/**
	 * 限制最高支付金额
	 */
	private Long highLimit;
	/**
	 * 限制最低支付金额
	 */
	private Long lowLimit;
	/**
	 * 是否首推
	 */
	private Boolean firstPush;
	/**
	 * 支付别名
	 */
	private String aliasName;
	/**
	 * 支付名称
	 */
	private String payName;
	/**
	 * 排序
	 */
	private Integer orderNum;
	
	/**
	 * 充值金额预设值
	 */
	private List<RechargeAmountEntity> rechargeAmount;
	/**
	 * 充值金额预设值
	 */
	private  String  amount;

}
