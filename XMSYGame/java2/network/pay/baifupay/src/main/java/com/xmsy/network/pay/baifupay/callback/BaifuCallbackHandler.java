package com.xmsy.network.pay.baifupay.callback;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import com.xmsy.common.bean.message.CallbackMessage;
import com.xmsy.network.pay.baifupay.def.Config;
import com.xmsy.network.pay.paybase.callback.BaseCallbackHandler;
import com.xmsy.network.pay.paybase.define.CodeDef;
import com.xmsy.network.pay.paybase.result.CallbackResult;
import com.xmsy.network.pay.paybase.utils.Md5Util;

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
public class BaifuCallbackHandler extends BaseCallbackHandler {

	public BaifuCallbackHandler() {
		super.getCallbackMap().put(Config.name, this);
	}

	@Override
	protected boolean verfiy(Map<String, Object> parameters) {
		return true;
	}

	@Override
	protected CallbackResult getCallbackResult(HttpServletRequest request) {
		String data = request.getParameter("NoticeParams");
		try {
			data = request.getParameter("paramData");
			log.info("[baifuPay->getCallbackResult] data {}", data);
			CallbackMessage callbackParam = new CallbackMessage();
			JSONObject jsonData = JSON.parseObject(data, Feature.OrderedField);
			Map<String, Object> resultMap = jsonData.getInnerMap();
			if (Config.ORDER_SUCCESS.equals(jsonData.getString("resultCode"))) {
				callbackParam.setStatus(CodeDef.SUCCESS);
			} else {
				return CallbackResult.err();
			}
			String sign = (String) resultMap.remove("sign");
			String resultSignDataStr = mapToJSON(resultMap) + Config.APPSECRET;// 拼装签名串
			String targetSign = Md5Util.md5(resultSignDataStr).toUpperCase();
			if (!sign.equals(targetSign)) {
				callbackParam.setStatus(CodeDef.FAIL);
				return CallbackResult.err("验签失败");
			}
			callbackParam.setAppId(jsonData.getString("merchantNo"));
			callbackParam
					.setAmount(new BigDecimal(jsonData.getString("payAmount")).divide(new BigDecimal(100)).intValue());
			callbackParam.setOrderNo(jsonData.getString("orderNum"));
			callbackParam.setSign(sign);
			return CallbackResult.success(callbackParam);
		} catch (Exception e) {
			log.error("[baifupay] parameters {}", data, e);
			return CallbackResult.err();
		}
	}

	public static String mapToJSON(Map<String, Object> map) {
		Iterator<Map.Entry<String, Object>> it = map.entrySet().iterator();
		StringBuffer json = new StringBuffer();
		json.append("{");
		while (it.hasNext()) {
			Map.Entry<String, Object> entry = it.next();
			String key = entry.getKey();
			String value = String.valueOf(entry.getValue());
			json.append("\"").append(key).append("\"");
			json.append(":");
			json.append("\"").append(value).append("\"");
			if (it.hasNext()) {
				json.append(",");
			}
		}
		json.append("}");
		return json.toString();
	}

	@Override
	protected void getResponse(boolean orderHandleResult, HttpServletResponse response) {
		if (orderHandleResult) {
			try {
				response.getOutputStream().write("SUCCESS".getBytes("UTF-8"));
			} catch (UnsupportedEncodingException e) {
				log.error("[baifupay->getResponse] UnsupportedEncodingException ", e);
			} catch (IOException e) {
				log.error("[baifupay->getResponse] IOException ", e);
			}
			return;
		}
		return;
	}
}
