package com.xmsy.network.pay.wantongpay.query;

import org.springframework.stereotype.Component;

import com.xmsy.network.pay.paybase.param.QueryOrderParam;
import com.xmsy.network.pay.paybase.query.QueryOrder;
import com.xmsy.network.pay.paybase.result.QueryOrderResult;

/**
 * .回调处理
 * 
 * @author aleng
 * @date 2018年11月2日
 * @version 1.0
 */
@Component
public class WanTongPayQueryHandler implements QueryOrder {

	@Override
	public QueryOrderResult getOrder(QueryOrderParam param) {
		
		return null;
	}
}
