package com.xmsy.network.pay.paybase.query;

import com.xmsy.network.pay.paybase.param.QueryOrderParam;
import com.xmsy.network.pay.paybase.result.QueryOrderResult;

/**
 * .支付订单查询（主动查询支付订单）
 * 
 * @author aleng
 * @date 2018年11月6日
 * @version 1.0
 */
public interface QueryOrder {
	
	//主动查询支付订单
	public QueryOrderResult getOrder(QueryOrderParam param);

}
