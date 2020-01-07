package com.xmsy.network.pay.paybase.param;

import com.xmsy.network.pay.paybase.define.PayPlatform;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 支付请求参数
 *
 * @author aleng
 *
 */
@Data
@Accessors(chain = true)
public class PayParam {

	// 支付订单号：（系统自己的订单号）
	private String orderNo;
	// 支付金额（订单总额）
	private long amount;
	// 商品名称
	private String goods;
	// 商品价格
	private String goodsPrice;
	// 商品数量
	private String goodsNum;
	// 订单支付ip
	private String orderIp;
	// 支付商家同步返回的url
	private String returnUrl;
	// 支付商家异步通知的url
	private String orderNotifyUrl;
	// 支付通道
	private String payChannel;
	// pc端和H5端
	private PayPlatform platform;
	// 用户名
	private String userName;
}
