package com.xmsy.network.pay.paybase.define;

/**
 * 交易具体类型
 */
public enum PayChannel {
	/**
	 * 银行充值
	 */
	BANKRECHARGE(11, "银行充值"),
	/**
	 * 支付宝充值
	 */
	ALIPAYRECHARGE(12, "支付宝充值"),
	/**
	 * 微信充值
	 */
	WECHATRECHARGE(13, "微信充值"),
	/**
	 * qq充值
	 */
	QQRECHARGE(29, "QQ支付"),
	/**
	 * 京东充值
	 */
	JINGDONGRECHARGE(30, "京东充值"),
	/**
	 * 快捷充值
	 */
	QUICKRECHARGE(31, "快捷充值"),
	/**
	 * 银联充值
	 */
	UNIONRECHARGE(32, "银联充值");

	private long value;
	private String name;

	public long getValue() {
		return value;
	}

	public String getName() {
		return name;
	}

	PayChannel(long value, String name) {
		this.value = value;
		this.name = name;
	}

	public static PayChannel getPayChannel(long value) throws Exception {
		for (PayChannel channel : PayChannel.values()) {
			if (channel.getValue() == value) {
				return channel;
			}
		}
		throw new Exception("支付通道类型不正确");
	}
}
