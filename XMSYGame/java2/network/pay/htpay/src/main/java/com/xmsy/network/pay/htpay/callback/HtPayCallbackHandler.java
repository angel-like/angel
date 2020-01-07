package com.xmsy.network.pay.htpay.callback;

import com.xmsy.common.bean.message.CallbackMessage;
import com.xmsy.network.pay.htpay.def.Config;
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
public class HtPayCallbackHandler extends BaseCallbackHandler {

	public HtPayCallbackHandler() {
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
			parameters = getRarameters(request);//里面有状态吗result
			log.info("[htPay->getCallbackResult] data {}", parameters);
			CallbackMessage callbackParam = new CallbackMessage();
			String result = (String) parameters.get("result");
			if (Config.CALLBACK_SUCCESS.equals(result)) {//支付状态码为1就是支付成功
				callbackParam.setStatus(CodeDef.SUCCESS);
			} else {
				return CallbackResult.err();
			}
			String sign = (String) parameters.get("sign");//平台自己产生
			//封装再次验签  加密前源字符串为： orderid={0}&result={1}&amount={2}&systemorderid={3}&completetime={4}&key={5}
			StringBuffer sb = new StringBuffer();
			sb.append("orderid="+parameters.get("orderid"));//商户ID
			sb.append("&result="+parameters.get("result"));//银行类型
			sb.append("&amount="+parameters.get("amount"));//银行类型
			sb.append("&systemorderid="+parameters.get("systemorderid"));//此次订单过程中恒通接口系统内的订单Id
			sb.append("&completetime="+parameters.get("completetime"));//此次订单过程中恒通接口系统内的订单结束时间。长度14位，格式为：yyyymmddHHmmss
			sb.append("&key="+Config.APPSECRET);
			String targetSign = Md5Util.md5(sb.toString()).toLowerCase();// 加密字符串
			if (!sign.equals(targetSign)) {
				callbackParam.setStatus(CodeDef.FAIL);
				return CallbackResult.err("验签失败");
			}
			//设置回调参数
			callbackParam.setOrderNo((String) parameters.get("orderid"));
			callbackParam.setStatus(Integer.valueOf(result));
			int amount = new BigDecimal(parameters.get("amount").toString()).intValue();//变成.00格式
			callbackParam.setAmount(amount);
			callbackParam.setMerchantNo((String) parameters.get("systemorderid"));//平台订单号
			callbackParam.setSign(sign);
			return CallbackResult.success(callbackParam);
		} catch (Exception e) {
			log.error("[htpay] parameters {}", parameters, e);
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
