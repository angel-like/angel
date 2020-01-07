package com.xmsy.network.pay.hongniupay.query;

import java.util.TreeMap;

import com.xmsy.network.pay.hongniupay.def.Config;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xmsy.network.pay.paybase.param.QueryOrderParam;
import com.xmsy.network.pay.paybase.query.QueryOrder;
import com.xmsy.network.pay.paybase.result.QueryOrderResult;
import com.xmsy.network.pay.paybase.utils.Md5Util;
import com.xmsy.network.pay.paybase.utils.SignUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * .回调处理
 *
 * @author aleng
 * @date 2018年11月2日
 * @version 1.0
 */
@Slf4j
@Component
public class HongNiuPayQueryHandler implements QueryOrder {

	@Override
	public QueryOrderResult getOrder(QueryOrderParam param) {
		JSONObject requestParam = new JSONObject();
		requestParam.put("merchant", Config.APPID);
		requestParam.put("tradeId", param.getOrderId());
		TreeMap<String, String> map = new TreeMap<String, String>();
		map.put("merchant", Config.APPID);
		map.put("tradeId", param.getOrderId());
		String data = SignUtil.sortSign(map);
		String sign = Md5Util.md5(data);
		requestParam.put("sign", sign);
//		String result = HttpUtil.post(Config.QUERY_URL, requestParam.toString());
		JSONObject jsonResult = JSON.parseObject("");
		log.info("[HrPayQueryHandler] jsonResult {}", jsonResult);
		return null;
	}
}
