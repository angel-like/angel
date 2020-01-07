package com.xmsy.network.pay.qiqipay.callback;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RestController;

import com.xmsy.common.bean.message.CallbackMessage;
import com.xmsy.network.pay.paybase.callback.BaseCallbackHandler;
import com.xmsy.network.pay.paybase.result.CallbackResult;
import com.xmsy.network.pay.paybase.utils.Md5Util;
import com.xmsy.network.pay.paybase.utils.SignUtil;
import com.xmsy.network.pay.qiqipay.def.Config;

import lombok.extern.slf4j.Slf4j;

/**
 * .回调处理
 * 
 * @author aleng
 * @date 2018年11月2日
 * @version 1.0
 */
@Slf4j
@RestController
public class QiqipayCallbackHandler extends BaseCallbackHandler {

	public QiqipayCallbackHandler() {
		super.getCallbackMap().put(Config.name, this);
	}

	@Override
	protected boolean verfiy(Map<String, Object> parameters) {
		String sign = String.valueOf(parameters.get("sign"));
		TreeMap<String, String> map = new TreeMap<String, String>();
		map.put("OrderNo", String.valueOf(parameters.get("OrderNo")));
		map.put("OrderAmount", String.valueOf(parameters.get("OrderAmount")));
		map.put("TimeEnd", String.valueOf(parameters.get("TimeEnd")));
		map.put("Msg", String.valueOf(parameters.get("Msg")));
		String callbackData = SignUtil.sortSign(map);
		callbackData = callbackData + "&key=" + Config.APPSECRET;
		String signParam = Md5Util.md5(callbackData).toUpperCase();
		return signParam.equals(sign);
	}

	@Override
	protected CallbackResult getCallbackResult(HttpServletRequest request) {
		Map<String, Object> parameters = null;
		try {
			parameters = getRarameters(request);
			// 验签
			if (verfiy(parameters)) {
				log.info("[qiqipay->getCallbackResult] parameters {}", parameters);
				CallbackMessage callbackParam = new CallbackMessage();
				callbackParam.setMerchantNo(String.valueOf(parameters.get("OrderNo")));
				callbackParam.setOrderNo(String.valueOf(parameters.get("Msg")));
				callbackParam.setSign(String.valueOf(parameters.get("sign")));
				return CallbackResult.success(callbackParam);
			} else {
				log.error("[qiqipayCallback]->getCallbackResult 回调验签失败 parameters {}", parameters);
				return CallbackResult.err();
			}

		} catch (Exception e) {
			log.error("[qiqipayCallback] parameters {}", parameters, e);
			return CallbackResult.err();
		}
	}

	@Override
	protected void getResponse(boolean orderHandleResult, HttpServletResponse response) {
		if (orderHandleResult) {
			try {
				response.getOutputStream().write(Config.CALLBACK_SUCCESS.getBytes("UTF-8"));
			} catch (UnsupportedEncodingException e) {
				log.error("[QiqipayCallbackHandler->getResponse] UnsupportedEncodingException ", e);
			} catch (IOException e) {
				log.error("[QiqipayCallbackHandler->getResponse] IOException ", e);
			}
			return;
		}
		return;
	}
}
