package com.xmsy.network.pay.hypay.callback;

import com.alibaba.fastjson.JSONObject;
import com.xmsy.common.bean.message.CallbackMessage;
import com.xmsy.network.pay.hypay.config.HyPayConfig;
import com.xmsy.network.pay.hypay.def.Config;
import com.xmsy.network.pay.hypay.pay.HyPay;
import com.xmsy.network.pay.paybase.callback.BaseCallbackHandler;
import com.xmsy.network.pay.paybase.define.CodeDef;
import com.xmsy.network.pay.paybase.result.CallbackResult;
import com.xmsy.network.pay.paybase.utils.Md5Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.Enumeration;
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
public class HyPayCallbackHandler extends BaseCallbackHandler {
	@Resource
	private HyPayConfig hyPayConfig;
	public HyPayCallbackHandler() {
		super.getCallbackMap().put(Config.name, this);
	}

	@Override
	protected boolean verfiy(Map<String, Object> parameters) {
		return true;
	}

	@Override
	protected CallbackResult getCallbackResult(HttpServletRequest request) {
		log.info("contentType {}",request.getContentType());
		int i = request.getContentType().indexOf("application/json");
		if(request.getContentType().indexOf("application/json")>-1){
			/*TreeMap<String, Object> parameters = null;*/
			String parmstr = null;
			JSONObject parameters =null;
			try {
				parmstr = readAsString(request);
				log.info("[hypay1->parmstr] data {}", parmstr);
				parameters= JSONObject.parseObject(parmstr);
				/*parameters = getRarameters(request);//里面有状态吗result*/
				log.info("[hypay2->parameters] data {}", parameters);
				if(parameters==null){
					log.error("[hypay3] parameters {}", parameters);
					return CallbackResult.err("回调参数为空");
				}
				CallbackMessage callbackParam = new CallbackMessage();
				String result = (String) parameters.get("respSts");
				log.info("[hypay4->result-statuscode] data {}", result);
				/*Integer status =0;*/
				if (Config.CALLBACK_SUCCESS.equals(result)) {//支付状态码为1就是支付成功
					/*status=1;*/
					callbackParam.setStatus(CodeDef.SUCCESS);
				} else {
					log.error("[hypay-pay-false] result {}", result);
					return CallbackResult.err("支付失败");
				}
				String sign = (String) parameters.get("sign");//平台自己产生
				log.info("[hypay5-> sign] data {}",  sign);
				//封装再次验签  加密前源字符串为： orderid={0}&result={1}&amount={2}&systemorderid={3}&completetime={4}&key={5}
				TreeMap<String, Object> map = new TreeMap<String, Object>();
				log.info("[hypay6-> Config.APPID] data {}",  Config.APPID);
				map.put("merchant", Config.APPID);
				// 金额，单位元
				map.put("money", parameters.get("money"));
				// 订单号
				map.put("tradeId", parameters.get("tradeId"));
				// 支付产品号，银行类型
				map.put("payWay",  parameters.get("payWay"));
				// 回调地址
				log.info("[hypay7-> hyPayConfig.getCallbackUrl()] data {}",  hyPayConfig.getCallbackUrl());
				map.put("callback", hyPayConfig.getCallbackUrl());
				/*map.put("callback", "https://api.3456.im/v1/payment/hypay");*/
				/*	map.put("sign", Config.APPSECRET);*/
				log.info("[hypay8->  Config.APPSECRET] data {}",  Config.APPSECRET);
				log.info("[hypay9->  map] data {}",  map);
				String signDataStr = HyPay.getSignDataStr(map, Config.APPSECRET);// 拼装签名串
				log.info("[hypay10->  signDataStr] data {}",  signDataStr);
				String targetSign = Md5Util.md5(signDataStr).toUpperCase(); // 加密字符串
				log.info("[hypay11-> targetSign] data {}",  targetSign);
				if (!sign.equals(targetSign)) {
					log.info("[hypay111-> CodeDef] data {验签失败}");
					callbackParam.setStatus(CodeDef.FAIL);
					return CallbackResult.err("验签失败");
				}
				//设置回调参数
				callbackParam.setOrderNo((String) parameters.get("tradeId"));
				/*callbackParam.setStatus(status);*/
				int amount = new BigDecimal(parameters.get("money").toString()).intValue();//变成.00格式
				callbackParam.setAmount(amount);
				callbackParam.setMerchantNo((String) parameters.get("tradeId"));//平台订单号
				callbackParam.setAppId((String) parameters.get("merchant"));//平台订单号
				callbackParam.setSign(sign);
				log.error("[hypay12] callbackParam {}", callbackParam);
				return CallbackResult.success(callbackParam);
			} catch (Exception e) {
				log.error("[hypay13] parameters e{}", parameters, e);
				return CallbackResult.err("回调异常");
			}
		}else{

		/*TreeMap<String, Object> parameters = null;*/
		/*String parmstr = null;*/
		JSONObject parameters =null;
		try {

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
		/*	parameters = getRarameters(request);//里面有状态吗result*/
			log.info("[hypay3->parameters] data {}", parameters);
			log.info("[hypay4->parameters.size()] data {}", parameters.size());
			log.info("[hypay5->parameters.values()] data {}", parameters.entrySet());
			log.info("[hypay6->parameters.keySet()] data {}", parameters.keySet());
			log.info("[hypay7->parameters.values()] data {}", parameters.values());
			if(parameters==null){
				log.error("[hypay3] parameters {}", parameters);
				return CallbackResult.err("回调参数为空");
			}
			CallbackMessage callbackParam = new CallbackMessage();
			String result = (String) parameters.get("respSts");
			log.info("[hypay8->result-statuscode] data {}", result);
			/*Integer status =0;*/
			if (Config.CALLBACK_SUCCESS.equals(result)) {//支付状态码为1就是支付成功
				/*status=1;*/
				callbackParam.setStatus(CodeDef.SUCCESS);
			} else {
				log.error("[hypay-pay-false] result {}", result);
				return CallbackResult.err("支付失败");
			}
			String sign = (String) parameters.get("sign");//平台自己产生
			log.info("[hypay9-> sign] data {}",  sign);
			//封装再次验签  加密前源字符串为： orderid={0}&result={1}&amount={2}&systemorderid={3}&completetime={4}&key={5}
			TreeMap<String, Object> map = new TreeMap<String, Object>();
			log.info("[hypay10-> Config.APPID] data {}",  Config.APPID);
			map.put("merchant", parameters.get("merchant"));
			// 金额，单位元
			map.put("money", parameters.get("money"));
			// 订单号
			map.put("tradeId", parameters.get("tradeId"));
			// 支付产品号，银行类型
			map.put("payWay",  parameters.get("payWay"));
			// 回调地址
			log.info("[hypay11-> hyPayConfig.getCallbackUrl()] data {}",  hyPayConfig.getCallbackUrl());
			map.put("respSts", parameters.get("respSts"));
			/*map.put("return_url", hyPayConfig.getCallbackUrl());*/
			/*	map.put("sign", Config.APPSECRET);*/
			log.info("[hypay12->  Config.APPSECRET] data {}",  Config.APPSECRET);
			log.info("[hypay13->  map] data {}",  map);
			String signDataStr = HyPay.getSignDataStr(map, Config.APPSECRET);// 拼装签名串
			log.info("[hypay14->  signDataStr] data {}",  signDataStr);
			String targetSign = Md5Util.md5(signDataStr).toUpperCase(); // 加密字符串
			log.info("[hypay15-> targetSign] data {}",  targetSign);
			if (!sign.equals(targetSign)) {
				log.info("[hypay111-> CodeDef] data {验签失败}");
				callbackParam.setStatus(CodeDef.FAIL);
				return CallbackResult.err("验签失败");
			}
			//设置回调参数
			callbackParam.setOrderNo((String) parameters.get("tradeId"));
			/*callbackParam.setStatus(status);*/
			int amount = new BigDecimal(parameters.get("money").toString()).intValue();//变成.00格式
			callbackParam.setAmount(amount);
			callbackParam.setMerchantNo((String) parameters.get("tradeId"));//平台订单号
			callbackParam.setAppId((String) parameters.get("merchant"));//平台订单号
			callbackParam.setSign(sign);
			log.error("[hypay16] callbackParam {}", callbackParam);
			return CallbackResult.success(callbackParam);
		} catch (Exception e) {
			log.error("[hypay17] parameters e{}", parameters, e);
			return CallbackResult.err("回调异常");
		}
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
				response.getOutputStream().write("OK".getBytes("UTF-8"));
			} catch (UnsupportedEncodingException e) {
				log.error("[hypay->getResponse] UnsupportedEncodingException ", e);
			} catch (IOException e) {
				log.error("[hypay->getResponse] IOException ", e);
			}
			return;
		}else {
			try {
				response.getOutputStream().write("FAIL".getBytes("UTF-8"));
			} catch (UnsupportedEncodingException e) {
				log.error("[hypay->getResponse] UnsupportedEncodingException ", e);
			} catch (IOException e) {
				log.error("[hypay->getResponse] IOException ", e);
			}
			return;
		}

	}
}
