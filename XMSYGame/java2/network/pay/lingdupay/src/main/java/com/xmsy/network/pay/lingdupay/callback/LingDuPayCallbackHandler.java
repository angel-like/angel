package com.xmsy.network.pay.lingdupay.callback;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.xmsy.common.bean.message.CallbackMessage;
import com.xmsy.network.pay.lingdupay.def.Config;
import com.xmsy.network.pay.lingdupay.utils.SHA1Utils;
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
public class LingDuPayCallbackHandler extends BaseCallbackHandler {

	public LingDuPayCallbackHandler() {
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
//			{"amount":1.0,"channelType":"7","merchant":"10009","orderId":"ord1551026297","sign":"D38A55A438CAAFB191FD551BE1510A231C1C8C70",
//			"tranNo":"YJ201902250038170818353"}
//			商家打印success表示收到，一定需要是小写
			parmstr = readAsString(request);
			log.info("[LingDuPay->getCallbackResult] data {}", parmstr);
			JSONObject resultJson = JSONObject.parseObject(parmstr);
			CallbackMessage callbackParam = new CallbackMessage();
			log.info("[LingDuPay->getCallbackResult] result {}", resultJson);
			if (resultJson == null ) {//支付状态码为1就是支付成功
				return CallbackResult.err();
			}
			callbackParam.setStatus(CodeDef.SUCCESS);
			String sign = resultJson.getString("sign");//平台自己产生
			//封装再次验签  加密前源字符串为： orderid={0}&result={1}&amount={2}&systemorderid={3}&completetime={4}&key={5}
			StringBuilder resultSign = new StringBuilder();
			resultSign.append("amount=").append(resultJson.getString("amount")).append("&");
//			resultSign.append("amount=").append("297").append("&");
			resultSign.append("channelType=").append(resultJson.getString("channelType")).append("&");
			resultSign.append("merchant=").append(resultJson.getString("merchant")).append("&");
			resultSign.append("orderId=").append(resultJson.getString("orderId")).append("&");
			resultSign.append("tranNo=").append(resultJson.getString("tranNo")).append("&");
			resultSign.append("key=").append(Config.APPSECRET);
//			sb.append(Config.APPSECRET);
			log.info("[LingDuPay->getCallbackResult] signstr {}", resultSign.toString());
			String targetSign = SHA1Utils.getSha1(resultSign.toString(), "utf-8").toUpperCase();// 加密字符串
			log.info("[LingDuPay->getCallbackResult] targetSign {}", targetSign);
			if (!sign.equals(targetSign)) {
				callbackParam.setStatus(CodeDef.FAIL);
				return CallbackResult.err("验签失败");
			}
			//设置回调参数
			callbackParam.setOrderNo(resultJson.getString("orderId"));
			callbackParam.setAmount(new BigDecimal(resultJson.get("amount").toString()).intValue());
			callbackParam.setMerchantNo(resultJson.getString("tranNo"));//平台订单号
			callbackParam.setSign(sign);
			return CallbackResult.success(callbackParam);
		} catch (Exception e) {
			log.error("[LingDuPay] parameters {}", parmstr, e);
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
		if (!orderHandleResult) {
			return;
		}
		try {
			response.getOutputStream().write(Config.CALLBACK_SUCCESS.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			log.error("[LingDuPay->getResponse] UnsupportedEncodingException ", e);
		} catch (IOException e) {
			log.error("[LingDuPay->getResponse] IOException ", e);
		}
		return;
	}
}
