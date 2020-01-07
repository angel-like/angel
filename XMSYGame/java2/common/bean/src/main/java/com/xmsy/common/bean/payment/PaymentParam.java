package com.xmsy.common.bean.payment;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

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
public class PaymentParam {

	// 支付订单号：（系统自己的订单号）
	@NotBlank(message = "订单号不能为空")
	private String orderNo;
	// 支付金额（订单总额）
	@NotNull(message = "支付金额不能为空")
	private Long amount;
	// 商品名称
	private String goods;
	// 商品价格
	private String goodsPrice;
	// 商品数量
	private String goodsNum;
	// 订单支付ip
	private String orderIp;
	// 支付通道
	@NotEmpty(message = "支付类型不能为空")
	private String payChannel;
	// 服务路径
	@NotEmpty(message = "支付公司不能为空")
	private String payServiceName;
	// 用户名
	private String userName;
}
