package com.xmsy.network.pay.paybase.pay;

import com.xmsy.common.bean.payment.ResultData;
import com.xmsy.common.define.result.GlobalResult;
import com.xmsy.network.pay.paybase.param.PayParam;

/**
 * 支付接口
 * 
 * @author aleng
 *
 */
public interface Payment {

	// 支付
	GlobalResult<ResultData> pay(PayParam param);

}
