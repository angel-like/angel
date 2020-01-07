package com.xmsy.server.zxyy.game.common.utils;

public enum Dictionary {
	/**
	 * 银行信息
	 */
	BANK("001"),
	/**
	 * 用户类型
	 */
	USERTYPE("002"),
	/**
	 * 交易类型
	 */
	TRANSACTIONTYPE("003");
	
	private String value;
	private Dictionary(String value) {
		this.value=value;
	}; 
	
	public String getValue() {
		return this.value;
	}
}
