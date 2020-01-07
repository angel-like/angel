package com.xmsy.network.pay.yijiapay.callback;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xmsy.common.bean.message.CallbackMessage;
import com.xmsy.network.pay.paybase.callback.BaseCallbackHandler;
import com.xmsy.network.pay.paybase.define.CodeDef;
import com.xmsy.network.pay.paybase.result.CallbackResult;
import com.xmsy.network.pay.paybase.utils.SignUtil;
import com.xmsy.network.pay.yijiapay.def.Config;

import lombok.extern.slf4j.Slf4j;

/**
 * .回调处理
 * 
 * @author aleng
 * @date 2018年11月2日
 * @version 1.0
 */
@Slf4j
@Controller
public class YijiaCallbackHandler extends BaseCallbackHandler {

	public YijiaCallbackHandler() {
		super.getCallbackMap().put(Config.name, this);
	}

	@Override
	protected boolean verfiy(Map<String, Object> parameters) {
		return true;
	}

	@Override
	protected CallbackResult getCallbackResult(HttpServletRequest request) {
		TreeMap<String, Object> parameters = null;
		try {
			String param = readAsString(request);
			log.info("[YijiaCallbackHandler->getCallbackResult] param {}", param);
			JSONObject paramJson = JSON.parseObject(param);
			parameters = new TreeMap<>(paramJson);
			CallbackMessage callbackParam = new CallbackMessage();
			String merchant = String.valueOf(parameters.get("merchant"));
			String tranNo = String.valueOf(parameters.get("tranNo"));
			String amount = String.valueOf(parameters.get("amount"));
			String orderId = String.valueOf(parameters.get("orderId"));
			String sign = String.valueOf(parameters.get("sign"));
			parameters.remove("sign");
			String preSignParam = SignUtil.sortSignTreeMap(parameters);
			preSignParam += "&key=" + Config.APPSECRET;
			String paramSign = DigestUtils.sha1Hex(preSignParam.getBytes()).toUpperCase();
			log.info("[YijiaCallbackHandler->getCallbackResult] preSignParam {} paramSign {}", preSignParam, paramSign);
			if (!sign.equals(paramSign)) {
				callbackParam.setStatus(CodeDef.FAIL);
				return CallbackResult.err("验签失败");
			}
			callbackParam.setAppId(merchant);
			callbackParam.setAmount(Double.valueOf(amount).intValue());
			callbackParam.setMerchantNo(tranNo);
			callbackParam.setOrderNo(orderId);
			callbackParam.setSign(sign);
			return CallbackResult.success(callbackParam);
		} catch (Exception e) {
			log.error("[YijiaCallbackHandler] parameters {}", parameters, e);
			return CallbackResult.err();
		}
	}

	@Override
	protected void getResponse(boolean orderHandleResult, HttpServletResponse response) {
		if (orderHandleResult) {
			try {
				response.getOutputStream().write("success".getBytes("UTF-8"));
			} catch (UnsupportedEncodingException e) {
				log.error("[YijiaCallbackHandler->getResponse] UnsupportedEncodingException ", e);
			} catch (IOException e) {
				log.error("[YijiaCallbackHandler->getResponse] IOException ", e);
			}
			return;
		}
		return;
	}
}
