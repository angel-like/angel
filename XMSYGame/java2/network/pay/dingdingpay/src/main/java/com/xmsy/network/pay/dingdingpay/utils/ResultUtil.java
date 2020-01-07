package com.xmsy.network.pay.dingdingpay.utils;

import com.xmsy.network.pay.dingdingpay.def.Config;

/**
 * .支付结果工具类
 * 
 * @author aleng
 * @date 2018年11月22日
 * @version 1.0
 */
public class ResultUtil {
	/**
	 * 预支付是否成功
	 * 
	 * @param result
	 * @return
	 */
	public static boolean prePaySuccess(String code) {
		return Config.PRE_ORDER_SUCCESS.equals(code);
	}
	
	/**
	 * 支付是否成功
	 * 
	 * @param result
	 * @return
	 */
	public static boolean paySuccess(String code) {
		return Config.ORDER_SUCCESS.equals(code);
	}
}
