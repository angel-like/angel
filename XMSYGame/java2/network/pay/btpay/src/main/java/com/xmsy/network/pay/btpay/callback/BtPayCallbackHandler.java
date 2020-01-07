package com.xmsy.network.pay.btpay.callback;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.xmsy.common.bean.message.CallbackMessage;
import com.xmsy.network.pay.btpay.def.Config;
import com.xmsy.network.pay.btpay.utils.AES128Util;
import com.xmsy.network.pay.paybase.callback.BaseCallbackHandler;
import com.xmsy.network.pay.paybase.define.CodeDef;
import com.xmsy.network.pay.paybase.result.CallbackResult;

import lombok.extern.slf4j.Slf4j;

/**
 * .回调处理
 * 
 * @author adu
 * @date 2019年10月21日
 * @version 1.0
 */
@Slf4j
@Component
public class BtPayCallbackHandler extends BaseCallbackHandler {

	public BtPayCallbackHandler() {
		super.getCallbackMap().put(Config.name, this);
	}

	@Override
	protected boolean verfiy(Map<String, Object> parameters) {
		return true;
	}
	/**
	 * 取得回调参数并验证签名
	 */
	@Override
	protected CallbackResult getCallbackResult(HttpServletRequest request) {
		String parmstr = null;
		try {
			parmstr = readAsString(request);
			log.info("[btPay->getCallbackResult] data {}", parmstr);
			JSONObject parmJson = JSONObject.parseObject(parmstr);
			CallbackMessage callbackParam = new CallbackMessage();
			String result = AES128Util.decryptBase64(parmJson.getString("data"), Config.APPSECRET);
			log.info("[btPay->getCallbackResult] result {}", result);
			JSONObject resultJson = JSONObject.parseObject(result);
			if (Config.CALLBACK_SUCCESS.equals(resultJson.getString("status"))) {//支付状态码为1就是支付成功
				callbackParam.setStatus(CodeDef.SUCCESS);
			} else {
				return CallbackResult.err();
			}
			String sign = parmJson.getString("sign");//平台自己产生
			//封装再次验签  加密前源字符串为： orderid={0}&result={1}&amount={2}&systemorderid={3}&completetime={4}&key={5}
			StringBuffer sb = new StringBuffer();
			sb.append(resultJson.getString("orderId"));//此次订单过程中接口系统内的订单Id
			sb.append(resultJson.getString("outerOrderId"));//
			int amount = new BigDecimal(resultJson.get("submitAmount").toString()).intValue();//变成.00格式
			sb.append(amount+".000");//
			sb.append(parmJson.getString("timestamp"));//
//			sb.append(Config.APPSECRET);
			log.info("[btPay->getCallbackResult] signstr {}", sb.toString());
			String targetSign = DigestUtils.md5Hex(sb.toString());// 加密字符串
			log.info("[btPay->getCallbackResult] targetSign {}", targetSign);
			if (!sign.equals(targetSign)) {
				callbackParam.setStatus(CodeDef.FAIL);
				return CallbackResult.err("验签失败");
			}
			//设置回调参数
			callbackParam.setOrderNo(resultJson.getString("outerOrderId"));
			callbackParam.setAmount(amount);
			callbackParam.setMerchantNo(resultJson.getString("orderId"));//平台订单号
			callbackParam.setSign(sign);
			return CallbackResult.success(callbackParam);
		} catch (Exception e) {
			log.error("[btpay] parameters {}", parmstr, e);
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
	/**
	 * 给第三方平台回写内容 内容根据开发文档决定
	 */
	protected void getResponse(boolean orderHandleResult, HttpServletResponse response) {
		JSONObject data = new JSONObject();
		if (orderHandleResult) {
			data.put("code", 200);
		}else {
			data.put("code", 500);
		}
		data.put("msg", "");
		try {
			response.getOutputStream().write(data.toJSONString().getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			log.error("[baifupay->getResponse] UnsupportedEncodingException ", e);
		} catch (IOException e) {
			log.error("[baifupay->getResponse] IOException ", e);
		}
		return;
	}
}
