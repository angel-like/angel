package com.xmsy.network.pay.mingjiepay.callback;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import com.xmsy.common.bean.message.CallbackMessage;
import com.xmsy.network.pay.paybase.callback.BaseCallbackHandler;
import com.xmsy.network.pay.paybase.define.CodeDef;
import com.xmsy.network.pay.paybase.result.CallbackResult;
import com.xmsy.network.pay.mingjiepay.def.Config;
import com.xmsy.network.pay.mingjiepay.utils.ToolKit;

import lombok.extern.slf4j.Slf4j;
import sun.misc.BASE64Decoder;

/**
 * .回调处理
 * 
 * @author aleng
 * @date 2018年11月2日
 * @version 1.0
 */
@SuppressWarnings({ "unused", "restriction" })
@Slf4j
@Controller
public class MingjieCallbackHandler extends BaseCallbackHandler {

	public MingjieCallbackHandler() {
		super.getCallbackMap().put(Config.name, this);
	}

	@Override
	protected boolean verfiy(Map<String, Object> parameters) {
		return true;
	}

	@Override
	protected CallbackResult getCallbackResult(HttpServletRequest request) {
		String data = request.getParameter("data");
		try {
			data = request.getParameter("data");
			log.info("[mingjiepay->getCallbackResult] data {}", data);
			byte[] resultByte = ToolKit.decryptByPrivateKey(new BASE64Decoder().decodeBuffer(data), Config.PRIVATE_KEY);
			String resultStr = new String(resultByte, ToolKit.CHARSET);// 解密数据
			CallbackMessage callbackParam = new CallbackMessage();
			JSONObject jsonData = JSON.parseObject(resultStr, Feature.OrderedField);
			Map<String, Object> resultMap = jsonData.getInnerMap();
			if (Config.CALLBACK_SUCCESS.equals(jsonData.getString("payStateCode"))) {
				callbackParam.setStatus(CodeDef.SUCCESS);
			} else {
				return CallbackResult.err();
			}
			String sign = String.valueOf(resultMap.remove("sign"));
			String resultSignDataStr = ToolKit.mapToJsonObject(resultMap);
			String targetSign = ToolKit.MD5(resultSignDataStr + Config.APPSECRET, ToolKit.CHARSET);
			if (!sign.equals(targetSign)) {
				callbackParam.setStatus(CodeDef.FAIL);
				return CallbackResult.err("验签失败");
			}
			callbackParam.setAppId(jsonData.getString("merchNo"));
			callbackParam
					.setAmount(new BigDecimal(jsonData.getString("amount")).divide(new BigDecimal(100)).intValue());
			callbackParam.setOrderNo(jsonData.getString("orderNo"));
			callbackParam.setSign(sign);
			return CallbackResult.success(callbackParam);
		} catch (Exception e) {
			log.error("[mingjiepay] parameters {}", data, e);
			return CallbackResult.err();
		}
	}

	private static String getSignDataStr(Map<String, Object> reqDataMap, String md5Key) {
		if (reqDataMap != null) {
			// 键值排序
			Object[] keys = reqDataMap.keySet().toArray();
			Arrays.sort(keys);
			// 拼装字符串
			StringBuffer params = new StringBuffer();
			for (Map.Entry<String, Object> map : reqDataMap.entrySet()) {
				params.append(map.getValue().toString());
				params.append("|");
			}
			params.append(md5Key);
			return params.toString();
		}
		return null;
	}

	@Override
	protected void getResponse(boolean orderHandleResult, HttpServletResponse response) {
		if (orderHandleResult) {
			try {
				response.getOutputStream().write("SUCCESS".getBytes("UTF-8"));
			} catch (UnsupportedEncodingException e) {
				log.error("[mingjiepay->getResponse] UnsupportedEncodingException ", e);
			} catch (IOException e) {
				log.error("[mingjiepay->getResponse] IOException ", e);
			}
			return;
		}
		return;
	}
}
