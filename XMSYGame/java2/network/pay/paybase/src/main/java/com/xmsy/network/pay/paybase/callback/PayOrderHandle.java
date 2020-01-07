package com.xmsy.network.pay.paybase.callback;

import com.xmsy.common.bean.message.CallbackMessage;

/**
 * 支付返回的回调
 * 
 * @author aleng
 *
 */
public interface PayOrderHandle {

	//回调接口直接回调
	boolean orderHandle(CallbackMessage callbackParam) throws Exception;
}
