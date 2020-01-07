package com.xmsy.server.zxyy.manager.modules.manager.sysconfig.entity;


import lombok.Data;

/**
 * 基本设置SysConfig配置信息类
 * @author Administrator
 *
 */
@Data
public class SysConfigBasicParam {
	private String name;//支付公司名称
	private String aliasName;//支付渠道别名
	
	private String alipay;//6个产品编码字段
	private String weixin;
	private String unionpay;
	private String quickpay;
	private String qqpay;
	private String jindongpay;
	
	private String uid;//4个秘钥
	private String secret;
	private String publicKey;
	private String privateKey;
	
	private String callbackUrl;//回调地址
	private String payUrl;//支付地址
	
	private Long alipayId;//6个产品编码字段Id
	private Long weixinId;
	private Long unionpayId;
	private Long quickpayId;
	private Long qqpayId;
	private Long jindongpayId;
	
	private Long uidId;//4个秘钥Id
	private Long secretId;
	private Long publicKeyId;
	private Long privateKeyId;
	
	private Long callbackUrlId;//回调地址Id
	private Long payUrlId;//支付地址Id
}
