package com.xmsy.server.zxyy.webhome.modules.payment;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.xmsy.common.bean.payment.PaymentParam;
import com.xmsy.common.bean.payment.ResultData;
import com.xmsy.common.define.result.GlobalResult;
import com.xmsy.server.zxyy.webhome.constant.HallUrlConstant;

import cn.hutool.http.HttpRequest;
import lombok.extern.slf4j.Slf4j;

/**
 * 请求支付系统的接口
 * 
 * @author adu
 * @date 2019-02-26
 */
@Slf4j
public class PaymentApi {
	/**
	 * 游戏信息
	 */
	public static GlobalResult<ResultData> payH5(PaymentParam param) {
		String paymentToken = HallUrlConstant.getPAYMENT_TOKEN();
		String payh5Url = HallUrlConstant.getPAYMENT_H5URL();
		log.info("payh5Url {} paymentToken {}", payh5Url, paymentToken);
		String result = HttpRequest.post(payh5Url).header("token", paymentToken).body(JSON.toJSONString(param))
				.timeout(10000).execute().body();
		return JSON.parseObject(result, new TypeReference<GlobalResult<ResultData>>() {
		});
	}

	/**
	 * 游戏信息
	 */
	public static GlobalResult<ResultData> payPC(PaymentParam param) {
		String paymentToken = HallUrlConstant.getPAYMENT_TOKEN();
		String payPcUrl = HallUrlConstant.getPAYMENT_PCURL();
		log.info("payPcUrl {} paymentToken {}", payPcUrl, paymentToken);
		String result = HttpRequest.post(payPcUrl).header("token", paymentToken).body(JSON.toJSONString(param))
				.timeout(10000).execute().body();
		return JSON.parseObject(result, new TypeReference<GlobalResult<ResultData>>() {
		});
	}
}
