package com.xmsy.network.pay.dingdingpay.callback;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xmsy.common.bean.message.CallbackMessage;
import com.xmsy.network.pay.dingdingpay.def.Config;
import com.xmsy.network.pay.dingdingpay.utils.ResultUtil;
import com.xmsy.network.pay.paybase.callback.BaseCallbackHandler;
import com.xmsy.network.pay.paybase.define.CodeDef;
import com.xmsy.network.pay.paybase.result.CallbackResult;
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
@Controller
public class DingdingCallbackHandler extends BaseCallbackHandler {

	public DingdingCallbackHandler() {
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
			log.info("[DingdingCallbackHandler->getCallbackResult] param {}", param);
			JSONObject paramJson = JSON.parseObject(param);
			parameters = new TreeMap<>(paramJson);
			CallbackMessage callbackParam = new CallbackMessage();
			String userId = String.valueOf(parameters.get("userId"));
			String tranNo = String.valueOf(parameters.get("orderNo"));
			String amount = String.valueOf(parameters.get("payAmount"));
			String orderNo = String.valueOf(parameters.get("requestNo"));
			String sign = String.valueOf(parameters.get("sign"));
			parameters.remove("sign");
			parameters.remove("attachData");
			String preSignParam = SignUtil.sortSignTreeMap(parameters);
			preSignParam += "&key=" + Config.APPSECRET;
			String paramSign =Md5Util.md5(preSignParam).toLowerCase();
			log.info("[DingdingCallbackHandler->getCallbackResult] preSignParam {} paramSign {}", preSignParam, paramSign);
			if (!sign.equals(paramSign)) {
				callbackParam.setStatus(CodeDef.FAIL);
				return CallbackResult.err("验签失败");
			}
			if (!ResultUtil.paySuccess(paramJson.getString("status"))) {
				return CallbackResult.err(paramJson.getString("message"));
			}
			callbackParam.setAppId(userId);
			callbackParam.setAmount(Double.valueOf(amount).intValue());
			callbackParam.setMerchantNo(tranNo);
			callbackParam.setOrderNo(orderNo);
			callbackParam.setSign(sign);
			return CallbackResult.success(callbackParam);
		} catch (Exception e) {
			log.error("[DingdingCallbackHandler] parameters {}", parameters, e);
			return CallbackResult.err();
		}
	}

	@Override
	protected void getResponse(boolean orderHandleResult, HttpServletResponse response) {
		if (orderHandleResult) {
			try {
				response.getOutputStream().write("000000".getBytes("UTF-8"));
			} catch (UnsupportedEncodingException e) {
				log.error("[DingdingCallbackHandler->getResponse] UnsupportedEncodingException ", e);
			} catch (IOException e) {
				log.error("[DingdingCallbackHandler->getResponse] IOException ", e);
			}
			return;
		}
		return;
	}
}
