package com.xmsy.network.pay.wantongpay.callback;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.net.URLEncoder;

import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.xmsy.network.pay.paybase.utils.Md5Util;
import com.xmsy.network.pay.paybase.utils.SignUtil;

import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.xmsy.common.bean.message.CallbackMessage;
import com.xmsy.network.pay.paybase.callback.BaseCallbackHandler;
import com.xmsy.network.pay.paybase.define.CodeDef;
import com.xmsy.network.pay.paybase.result.CallbackResult;
import com.xmsy.network.pay.wantongpay.def.Config;

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
public class WanTongPayCallbackHandler extends BaseCallbackHandler {

	public WanTongPayCallbackHandler() {
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
		log.info("contentType {}", request.getContentType());
		int i = request.getContentType().indexOf("application/json");
		Map<String, Object> parameters = null;
		String parmstr = null;
		try {
			if (request.getContentType().indexOf("application/json") > -1) {
				parmstr = readAsString(request);
				log.info("[wantongpay1->parmstr] data {}", parmstr);
				parameters = JSONObject.parseObject(parmstr);
				log.info("[json->parameters] data {}", parameters);

			} else {
				parameters = getRarameters(request);//里面有状态吗result
			/*if(parameters.get("sign")==null){
				parameters.
			}*/
				log.info("[not json->parameters] data {}", parameters);
			}
			log.info("[wantongpay2->parameters] data {}", parameters);
			if (parameters == null) {
				log.error("[wantongpay3] parameters {}", parameters);
				return CallbackResult.err("回调参数为空");
			}
			String transdata = parameters.get("transdata").toString();
			String decode = URLDecoder.decode(transdata, "UTF-8");

			//JSONObject jsonObject = JSONObject.parseObject(decode);
			TreeMap treeMap = JSONObject.parseObject(decode, TreeMap.class);
			String sign = (String) parameters.get("sign");//平台自己产生
			log.info("[wantongpay5-> fxsign] data {}", sign);
			CallbackMessage callbackParam = new CallbackMessage();
			String result = treeMap.get("payment").toString();
			log.info("[wantongpay4->result-fxstatus] data {}", result);
			if (Config.CALLBACK_SUCCESS.equals(result)) {//支付状态码为1就是支付成功
				callbackParam.setStatus(CodeDef.SUCCESS);
			} else {
				log.error("[wantongpay-pay-false] result {}", result);
				String msg = (String) treeMap.get("payment");
				msg = msg == null ? "支付失败" : msg;
				return CallbackResult.err(msg);
			}

			String signDataStr = SignUtil.getSign(treeMap)+ "key=" + Config.APPSECRET;// 拼装签名串
			log.info("[wantongpay3] signDataStr {} ", signDataStr);
			String targetSign = null;
			try {
				targetSign = Md5Util.md5(signDataStr).toUpperCase();
				targetSign=URLEncoder.encode(targetSign, "UTF-8");
			} catch (Exception e) {
				log.error("[wantongpay4] result to json fail  result {}", targetSign);
				return CallbackResult.err("生成签名失败");
			}
			log.info("[wantongpay14-> targetSign] data {}", targetSign);
			if (!sign.equals(targetSign)) {
				callbackParam.setStatus(CodeDef.FAIL);
				return CallbackResult.err("验签失败");
			}
			//设置回调参数
			callbackParam.setOrderNo(treeMap.get("order_no").toString());
			int amount = new BigDecimal(treeMap.get("order_amount").toString()).intValue();//变成.00格式
			callbackParam.setAmount(amount);
			//callbackParam.setMerchantNo(treeMap.get(""));//平台订单号
			callbackParam.setAppId(Config.APPID);//
			callbackParam.setSign(parameters.get("sign").toString());
			log.info("[wantongpay15] callbackParam {}", callbackParam);
			return CallbackResult.success(callbackParam);
		} catch (Exception e) {
			log.error("[wantongpay16] parameters{} e{}", parameters, e);
			return CallbackResult.err("回调异常");
		}

	}

	@Override
	protected void getResponse(boolean orderHandleResult, HttpServletResponse response) {
		if (orderHandleResult) {
			try {
				response.setStatus(200);
				response.getOutputStream().write("200".getBytes("UTF-8"));

			} catch (UnsupportedEncodingException e) {
				log.error("[wantongpay->getResponse] UnsupportedEncodingException ", e);
			} catch (IOException e) {
				log.error("[wantongpay->getResponse] IOException ", e);
			}
			return;
		}else{
			try {
				response.setStatus(300);
				response.getOutputStream().write("fail".getBytes("UTF-8"));
			} catch (UnsupportedEncodingException e) {
				log.error("[wantongpay->getResponse] UnsupportedEncodingException ", e);
			} catch (IOException e) {
				log.error("[wantongpay->getResponse] IOException ", e);
			}
		}
		return;
	}
}
