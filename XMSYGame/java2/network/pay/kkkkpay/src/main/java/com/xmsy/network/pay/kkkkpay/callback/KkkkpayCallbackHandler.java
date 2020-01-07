package com.xmsy.network.pay.kkkkpay.callback;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RestController;

import com.xmsy.common.bean.message.CallbackMessage;
import com.xmsy.network.pay.kkkkpay.def.Config;
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
@RestController
public class KkkkpayCallbackHandler extends BaseCallbackHandler {

	public KkkkpayCallbackHandler() {
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
			parameters = getRarameters(request);
			log.info("[kkkkpay->getCallbackResult] parameters {}", parameters);
			CallbackMessage callbackParam = new CallbackMessage();
			String returncode = String.valueOf(parameters.get("returncode"));
			if (Config.CALLBACK_SUCCESS.equals(returncode)) {
				callbackParam.setStatus(CodeDef.SUCCESS);
			}
			String user_id = String.valueOf(parameters.get("user_id"));
			String trade_no = String.valueOf(parameters.get("trade_no"));
			String transaction_money = String.valueOf(parameters.get("transaction_money"));
			String out_trade_no = String.valueOf(parameters.get("out_trade_no"));
			// // String datetime = parameters.get("datetime");
			String transaction_id = String.valueOf(parameters.get("transaction_id"));
			String sign = String.valueOf(parameters.get("sign"));
			parameters.remove("attach");
			parameters.remove("sign");
			String signTemp = SignUtil.sortSignTreeMap(parameters);
			signTemp += "&apikey=" + Config.APPSECRET;
			log.info("[getCallbackResult] signTemp {} ", signTemp);
			String md5sign = Md5Util.md5(signTemp);
			if (!sign.equals(md5sign)) {
				callbackParam.setStatus(CodeDef.FAIL);
				return CallbackResult.err("验签失败");
			}
			callbackParam.setAppId(user_id);
			callbackParam.setAmount(Double.valueOf(transaction_money).intValue());
			callbackParam.setMerchantNo(trade_no == null ? transaction_id : trade_no);
			callbackParam.setOrderNo(out_trade_no);
			callbackParam.setSign(sign);
			return CallbackResult.success(callbackParam);
		} catch (Exception e) {
			log.error("[kkkkpay] parameters {}", parameters, e);
			return CallbackResult.err();
		}
	}

	@Override
	protected void getResponse(boolean orderHandleResult, HttpServletResponse response) {
		if (orderHandleResult) {
			try {
				response.getOutputStream().write("OK".getBytes("UTF-8"));
			} catch (UnsupportedEncodingException e) {
				log.error("[KkkkpayCallbackHandler->getResponse] UnsupportedEncodingException ", e);
			} catch (IOException e) {
				log.error("[KkkkpayCallbackHandler->getResponse] IOException ", e);
			}
			return;
		}
		return;
	}
}
