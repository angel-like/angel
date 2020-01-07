package com.xmsy.network.pay.qiqipay.utils;

import org.springframework.stereotype.Component;

import com.xmsy.network.pay.qiqipay.def.Config;

/**
 * .支付结果工具类
 * 
 * @author aleng
 * @date 2018年11月22日
 * @version 1.0
 */
@Component
public class QiqiPayResultUtil {

	/**
	 * 支付是否成功
	 * 
	 * @param result
	 * @return
	 */
	public boolean success(int code) {
		return Config.ORDER_SUCCESS == code;
	}
}
