package com.xmsy.network.pay.paybase.define;

/**
 * .支付配置定义
 * 
 * @author aleng
 *
 */
public class PayConfigConstant {

	/**
	 * .第三方支付凭证 uid（代表我们在第三方公司系统里面的商户id）
	 */
	public static final String UID = "uid";

	/**
	 * .第三方支付凭证 secret（代表我们在第三方公司系统里面的商户密钥）
	 */
	public static final String SECRET = "secret";

	/**
	 * .第三方支付加密公钥
	 */
	public static final String PUBLIC_KEY = "publicKey";

	/**
	 * .第三方支付加密私钥
	 */
	public static final String PRIVATE_KEY = "privateKey";

	/**
	 * .第三方支付凭证
	 */
	public static final String KEY = "密钥";

	/**
	 * .支付url
	 */
	public static final String ORDER_URL = "支付地址";

	/**
	 * .支付渠道编码
	 */
	public static final String PRODUCT = "产品编码";

	/**
	 * .支付回调url
	 */
	public static final String CALLBACK_URL = "回调地址";

	/**
	 * h5产品
	 */
	public static final String H5 = "h5";

	/**
	 * pc产品
	 */
	public static final String PC = "pc";

	/**
	 * .微信支付url
	 */
	public static final String WX_URL = "wx_url";

	/**
	 * .阿里支付url
	 */
	public static final String ALI_URL = "ali_url";
}
