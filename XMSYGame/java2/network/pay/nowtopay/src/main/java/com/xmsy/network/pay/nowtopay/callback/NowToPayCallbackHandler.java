package com.xmsy.network.pay.nowtopay.callback;

import com.xmsy.common.bean.message.CallbackMessage;
import com.xmsy.network.pay.nowtopay.def.Config;
import com.xmsy.network.pay.paybase.callback.BaseCallbackHandler;
import com.xmsy.network.pay.paybase.define.CodeDef;
import com.xmsy.network.pay.paybase.result.CallbackResult;
import com.xmsy.network.pay.paybase.utils.Md5Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
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
@RestController
public class NowToPayCallbackHandler extends BaseCallbackHandler {

	public NowToPayCallbackHandler() {
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
			log.info("[nowtopay->getCallbackResult] parameters {}", parameters);
			CallbackMessage callbackParam = new CallbackMessage();
			String orderstatus = (String) parameters.get("orderstatus");//status
			if (Config.CALLBACK_SUCCESS.equals(orderstatus)) {
				callbackParam.setStatus(CodeDef.SUCCESS);
			}
			String partner = String.valueOf(parameters.get("partner"));
			String ordernumber = String.valueOf(parameters.get("ordernumber"));
			String paymoney = String.valueOf(parameters.get("paymoney"));
			String sign = (String) parameters.get("sign");
			StringBuffer sb = new StringBuffer();
			//	partner={}&ordernumber={}&orderstatus={}&paymoney={}key 支付平台的sign
			sb.append("partner="+parameters.get("partner"));
			sb.append("&ordernumber="+parameters.get("ordernumber"));
			sb.append("&orderstatus="+parameters.get("orderstatus"));
			sb.append("&paymoney="+parameters.get("paymoney"));
			sb.append(Config.APPSECRET);
			String sign2 = Md5Util.md5(sb.toString());//再次加密的
			if (!sign.equals(sign2)) {
				callbackParam.setStatus(CodeDef.FAIL);
				return CallbackResult.err("验签失败");
			}
			//设置回调参数
			callbackParam.setAppId(partner);
			callbackParam.setStatus(Integer.valueOf(orderstatus));
			callbackParam.setAmount(Integer.valueOf(paymoney));
			callbackParam.setOrderNo(ordernumber);//商品订单号
			callbackParam.setSign(sign);
			return CallbackResult.success(callbackParam);
		} catch (Exception e) {
			log.error("[nowtopay] parameters {}", parameters, e);
			return CallbackResult.err();
		}
	}

	@Override
	protected void getResponse(boolean orderHandleResult, HttpServletResponse response) {
		if (orderHandleResult) {
			try {
				response.getOutputStream().write("OK".getBytes("UTF-8"));
			} catch (UnsupportedEncodingException e) {
				log.error("[NowToPayCallbackHandler->getResponse] UnsupportedEncodingException ", e);
			} catch (IOException e) {
				log.error("[NowToPayCallbackHandler->getResponse] IOException ", e);
			}
			return;
		}
		return;
	}
}
