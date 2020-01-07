package com.xmsy.common.bean.payment;

import lombok.Getter;
import lombok.ToString;

/**
 * .支付返回的数据
 * @author aleng
 * @date 2018年11月22日 
*  @version 1.0
 */
@ToString
@Getter
public class ResultData {
	
	//支付url
	private String payUrl;
	//我方订单流水号
	private String orderNo;
	//第三方支付公司流水号
	private String merchantOrderNo;
	
	public ResultData(String payUrl,String orderNo, String merchantOrderNo) {
		super();
		this.payUrl = payUrl;
		this.orderNo = orderNo;
		this.merchantOrderNo = merchantOrderNo;
	}

}
