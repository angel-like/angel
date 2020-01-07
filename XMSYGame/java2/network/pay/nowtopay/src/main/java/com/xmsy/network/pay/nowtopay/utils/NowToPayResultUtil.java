package com.xmsy.network.pay.nowtopay.utils;

import com.xmsy.network.pay.nowtopay.def.Config;

/**
 * .支付结果工具类
 *
 * @author aleng
 * @date 2018年11月22日
 * @version 1.0
 */
public class NowToPayResultUtil {

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
