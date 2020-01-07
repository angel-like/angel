package com.xmsy.network.pay.paybase.config;

import java.util.Map;

import com.xmsy.network.pay.paybase.define.PayPlatform;

import lombok.Data;
import lombok.ToString;

/**
 * .支付配置基类
 * 
 * @author aleng
 *
 */
@Data
@ToString
public class PayConfigBase {

	// 商户id
	private String uid;
	// 商户密钥
	private String secret;
	// 加密公钥
	private String publicKey;
	// 加密私钥
	private String privateKey;
	// 产品
	private Map<PayPlatform, Map<String, String>> product;
	// 回调地址
	private String callbackUrl;
	// 下单地址
	private String orderUrl;
	// 微信支付地址
	private String wxUrl;
	// 支付宝支付地址
	private String aliUrl;
}
