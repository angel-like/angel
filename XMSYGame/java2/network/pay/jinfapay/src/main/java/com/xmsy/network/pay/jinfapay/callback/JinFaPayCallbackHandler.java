package com.xmsy.network.pay.jinfapay.callback;

import com.alibaba.fastjson.JSONObject;
import com.xmsy.common.bean.message.CallbackMessage;
import com.xmsy.network.pay.jinfapay.def.Config;
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
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;

/**
 * .回调处理
 *
 * @author aleng
 * @date 2018年11月2日
 * @version 1.0
 */
@Slf4j
@Component
public class JinFaPayCallbackHandler extends BaseCallbackHandler {

	public JinFaPayCallbackHandler() {
		super.getCallbackMap().put(Config.name, this);
	}

	@Override
	protected boolean verfiy(Map<String, Object> parameters) {
		return true;
	}

	@Override
	protected CallbackResult getCallbackResult(HttpServletRequest request) {
		log.info("contentType {}", request.getContentType());
		int i = request.getContentType().indexOf("application/json");
		Map<String, Object> parameters = null;
		String parmstr = null;
		try {
			if (request.getContentType().indexOf("application/json") > -1) {
				parmstr = readAsString(request);
				log.info("[jinfapay1->parmstr] data {}", parmstr);
				parameters = JSONObject.parseObject(parmstr);
				log.info("[json->parameters] data {}", parameters);

			} else {
				StringBuilder sg=new StringBuilder();
				Enumeration<String> parameterNames = request.getParameterNames();
				while (parameterNames.hasMoreElements()) {
					sg.append(parameterNames.nextElement());
				}
				/*Object json = JSON.toJSON(sg);*/
				log.info("[hypay2->sg001] data {}", sg);
				if(sg.toString().endsWith("=")){
					log.info("[hypay2->sg end with =] sg {}", sg);
					sg= sg.deleteCharAt(sg.lastIndexOf("="));
				}
				parameters= JSONObject.parseObject(sg.toString());
				log.info("[not json->parameters] data {}", parameters);
			}

			log.info("[jinfapay2->parameters] data {}", parameters);
			if (parameters == null) {
				log.error("[jinfapay3] parameters {}", parameters);
				return CallbackResult.err("回调参数为空");
			}
			log.info("[jinfapay->getCallbackResult] data {}", parameters);
			CallbackMessage callbackParam = new CallbackMessage();
			String result = (String) parameters.get("code");
			if (Config.CALLBACK_SUCCESS.equals(result)) {//支付状态码为1就是支付成功
				callbackParam.setStatus(CodeDef.SUCCESS);
			} else {
				return CallbackResult.err();
			}
			String sign = (String) parameters.get("sign");//平台自己产生
			//封装再次验签  加密前源字符串为： orderid={0}&result={1}&amount={2}&systemorderid={3}&completetime={4}&key={5}
			StringBuffer sb = new StringBuffer();
			sb.append("pay_order="+parameters.get("pay_order"));//商户ID
			sb.append("&order_id="+parameters.get("order_id"));//银行类型
			sb.append("&price="+parameters.get("price"));//银行类型
			sb.append("&pay_type="+parameters.get("pay_type"));//此次订单过程中恒通接口系统内的订单Id
			sb.append("&code="+parameters.get("code"));//此次订单过程中恒通接口系统内的订单结束时间。长度14位，格式为：yyyymmddHHmmss
			sb.append("&timestamp="+parameters.get("timestamp"));
			sb.append("&key="+ Config.APPSECRET);
			String targetSign = Md5Util.md5(sb.toString()).toLowerCase();// 加密字符串
			if (!sign.equals(targetSign)) {
				callbackParam.setStatus(CodeDef.FAIL);
				return CallbackResult.err("验签失败");
			}
			//设置回调参数
			callbackParam.setOrderNo((String) parameters.get("order_id"));
			callbackParam.setStatus(Integer.valueOf(result));
			int amount = new BigDecimal(Integer.parseInt(parameters.get("price").toString())/100).intValue();//变成.00格式
			callbackParam.setAmount(amount);
			callbackParam.setMerchantNo((String) parameters.get("pay_order"));//平台订单号
			callbackParam.setSign(sign);
			return CallbackResult.success(callbackParam);
		} catch (Exception e) {
			log.error("[jinfapay] parameters {}", parameters, e);
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
