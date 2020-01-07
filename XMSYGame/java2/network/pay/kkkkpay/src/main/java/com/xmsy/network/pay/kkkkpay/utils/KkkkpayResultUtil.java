package com.xmsy.network.pay.kkkkpay.utils;

import com.xmsy.network.pay.kkkkpay.def.Config;

/**
 * .支付结果工具类
 * 
 * @author aleng
 * @date 2018年11月22日
 * @version 1.0
 */
public class KkkkpayResultUtil {

	/**
	 * 支付是否成功
	 * 
	 * @param result
	 * @return
	 */
	public static boolean success(int code) {
		return Config.ORDER_SUCCESS == code;
	}

}
