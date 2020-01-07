package com.xmsy.network.pay.jinchengpay.callback;

import com.xmsy.common.bean.message.CallbackMessage;
import com.xmsy.network.pay.jinchengpay.def.Config;
import com.xmsy.network.pay.paybase.callback.BaseCallbackHandler;
import com.xmsy.network.pay.paybase.define.CodeDef;
import com.xmsy.network.pay.paybase.result.CallbackResult;
import com.xmsy.network.pay.paybase.utils.Md5Util;
import com.xmsy.network.pay.paybase.utils.SignUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
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
public class JinChengPayCallbackHandler extends BaseCallbackHandler {

	public JinChengPayCallbackHandler() {
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
			parameters = getRarameters(request);//里面有状态吗trade_status
			log.info("[htPay->getCallbackResult] data {}", parameters);
			CallbackMessage callbackParam = new CallbackMessage();
			String result = (String) parameters.get("trade_status");
			if (Config.CALLBACK_SUCCESS.equals(result)) {//支付状态码为1就是支付成功
				callbackParam.setStatus(CodeDef.SUCCESS);
			} else {
				return CallbackResult.err();
			}
			String sign = (String) parameters.get("sign");//平台自己产生
			//封装再次验签  加密前源字符串为：
			TreeMap<String, String> map = new TreeMap<String, String>();
			map.put("merchant_code", (String) parameters.get("merchant_code"));
			map.put("order_no", (String) parameters.get("order_no"));
			map.put("order_amount", (String) parameters.get("amount"));
			map.put("order_time", (String) parameters.get("order_time"));
			map.put("trade_no", (String) parameters.get("trade_no"));
			map.put("order_time", (String) parameters.get("create_time"));
			map.put("trade_status", result);
			String data = SignUtil.sortSign(map);//按首字母升序
			data += "&key=" + Config.APPSECRET;
			String targetSign = Md5Util.md5(data).toLowerCase();// 加密字符串
			if (!sign.equals(targetSign)) {
				callbackParam.setStatus(CodeDef.FAIL);
				return CallbackResult.err("验签失败");
			}
			//设置回调参数
			callbackParam.setOrderNo((String) parameters.get("order_no"));
			callbackParam.setStatus(Integer.valueOf(result));
//			int amount = new BigDecimal(parameters.get("amount").toString()).intValue();//变成.00格式
			callbackParam.setAmount((Integer) parameters.get("amount"));
			callbackParam.setMerchantNo((String) parameters.get("trade_no"));//平台订单号
			callbackParam.setSign(sign);
			return CallbackResult.success(callbackParam);
		} catch (Exception e) {
			log.error("[jinchengpay] parameters {}", parameters, e);
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
				log.error("[jinchengpay->getResponse] UnsupportedEncodingException ", e);
			} catch (IOException e) {
				log.error("[jinchengpay->getResponse] IOException ", e);
			}
			return;
		}
		return;
	}
}
