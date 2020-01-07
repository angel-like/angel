package com.xmsy.network.pay.lingdupay.utils;


import com.xmsy.network.pay.lingdupay.def.Config;

/**
 * .支付结果工具类
 * 
 * @author aleng
 * @date 2018年11月22日
 * @version 1.0
 */
public class ResultUtil {
	/**
	 * 支付是否成功
	 * 
	 * @param result
	 * @return
	 */
	public static boolean success(String code) {
		return Config.ORDER_SUCCESS.equals(code);
	}
}
