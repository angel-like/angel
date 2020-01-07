package com.xmsy.network.pay.jdbpay.callback;

import com.xmsy.common.bean.message.CallbackMessage;
import com.xmsy.network.pay.jdbpay.def.Config;
import com.xmsy.network.pay.paybase.callback.BaseCallbackHandler;
import com.xmsy.network.pay.paybase.define.CodeDef;
import com.xmsy.network.pay.paybase.result.CallbackResult;
import com.xmsy.network.pay.paybase.utils.Md5Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

/**
 * .回调处理
 * 
 * @author aleng
 * @date 2018年11月2日
 * @version 1.0
 */
@Slf4j
@Component
public class JdbPayCallbackHandler extends BaseCallbackHandler {

	public JdbPayCallbackHandler() {
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
			parameters = getRarameters(request);//里面有状态码status
			log.info("[JdbPay->getCallbackResult] data {}", parameters);
			CallbackMessage callbackParam = new CallbackMessage();
			String status = (String) parameters.get("status");
			if (Config.CALLBACK_SUCCESS.equals(status)) {//支付状态码为1就是支付成功
				callbackParam.setStatus(CodeDef.SUCCESS);
			} else {
				return CallbackResult.err();
			}
			String sign = (String) parameters.get("sign");//平台自己产生
			//封装再次验签  加密前源字符串为： mcode={0}&orderid={1}&amt={2}&key={3}
			StringBuffer sb = new StringBuffer();
			sb.append("mcode="+parameters.get("mcode"));//商户ID
			sb.append("&orderid="+parameters.get("orderid"));
			sb.append("&amt="+parameters.get("amt"));
			sb.append("&key="+Config.APPSECRET);
			String targetSign = Md5Util.md5(sb.toString()).toLowerCase();// 加密字符串
			if (!sign.equals(targetSign)) {
				callbackParam.setStatus(CodeDef.FAIL);
				return CallbackResult.err("验签失败");
			}
			//设置回调参数
			callbackParam.setAppId(Config.APPID);
			callbackParam.setOrderNo((String) parameters.get("orderid"));
			callbackParam.setStatus(Integer.valueOf(status));
			int amt = new BigDecimal(parameters.get("amt").toString()).intValue();
			callbackParam.setAmount(amt);
			callbackParam.setMerchantNo((String) parameters.get("systemorderid"));//平台订单号
			callbackParam.setSign(sign);
			return CallbackResult.success(callbackParam);
		} catch (Exception e) {
			log.error("[Jdbpay] parameters {}", parameters, e);
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
				log.error("[Jdbpay->getResponse] UnsupportedEncodingException ", e);
			} catch (IOException e) {
				log.error("[Jdbpay->getResponse] IOException ", e);
			}
			return;
		}
		return;
	}
}
