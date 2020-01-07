package com.xmsy.network.pay.paybase.param;

import com.xmsy.network.pay.paybase.define.PayPlatform;

import lombok.Data;

/**
 * .支付回调失败的时候，我方主动掉支付公司提供的接口去查询支付结果
 * 
 * @author aleng
 *
 */
@Data
public class QueryOrderParam {
	
	//订单号（用我方公司自己的订单号去查询支付结果）
	private String orderId;
	//支付平台生成的订单号
	private String billNo;
	//支付平台
	private PayPlatform payPlatform;

}
