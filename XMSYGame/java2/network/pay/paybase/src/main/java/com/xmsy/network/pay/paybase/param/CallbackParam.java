package com.xmsy.network.pay.paybase.param;

import com.xmsy.common.bean.message.BaseMessage;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * .支付回调参数封装
 * 
 * @author aleng
 * @date 2018年11月2日
 * @version 1.0
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class CallbackParam extends BaseMessage {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// 支付平台订单号
	private String merchantNo;
	// 金额
	private Integer amount;
	// 接入方在接入时上传的参数
	private String attach;
	// 接入方的商户编号
	private String appId;
	// 我方平台订单号
	private String orderNo;
	// 商品名称
	private String goods;
	// 商品价格
	private String goodsPrice;
	// 商品数量
	private String goodsNum;
	// 签名
	private String sign;
	// 状态：0：未支付 1：成功
	private Integer status;

}
