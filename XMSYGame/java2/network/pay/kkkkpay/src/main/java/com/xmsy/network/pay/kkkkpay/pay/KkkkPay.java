package com.xmsy.network.pay.kkkkpay.pay;

import java.util.TreeMap;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xmsy.common.bean.message.SysConfigMessage;
import com.xmsy.common.bean.payment.ResultData;
import com.xmsy.common.define.result.GlobalResult;
import com.xmsy.common.define.result.ResultUtils;
import com.xmsy.network.pay.kkkkpay.config.KkkPayConfig;
import com.xmsy.network.pay.kkkkpay.def.Config;
import com.xmsy.network.pay.kkkkpay.utils.KkkkpayResultUtil;
import com.xmsy.network.pay.paybase.define.PayPlatform;
import com.xmsy.network.pay.paybase.param.PayParam;
import com.xmsy.network.pay.paybase.pay.impl.PayServiceBase;
import com.xmsy.network.pay.paybase.utils.Md5Util;
import com.xmsy.network.pay.paybase.utils.PayConfigUtils;
import com.xmsy.network.pay.paybase.utils.SignUtil;

import cn.hutool.http.HttpUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * .鼎盛支付逻辑
 *
 * @author aleng
 * @date 2018年11月6日
 * @version 1.0
 */
@Slf4j
@Component
public class KkkkPay extends PayServiceBase {

	@Resource
	private KkkPayConfig kkkPayConfig;

	public KkkkPay() {
		super.getPayServiceMap().put(getName(), this);
	}

	@Override
	protected GlobalResult<ResultData> doPay(PayParam param) {
		param.setOrderNotifyUrl(kkkPayConfig.getCallbackUrl());
		param.setReturnUrl(kkkPayConfig.getCallbackUrl());
		log.info("[KkkkPay] PayParam {}", param);
		JSONObject requestParam = new JSONObject();
		String applydate = SignUtil.generateTime();
		requestParam.put("user_id", Config.APPID);
		requestParam.put("out_trade_no", param.getOrderNo());
		if (PayPlatform.PC == param.getPlatform()) {
			requestParam.put("product_id", Config.PAY_CHANNEL_PC.get(param.getPayChannel()));
		} else {
			requestParam.put("product_id", Config.PAY_CHANNEL_H5.get(param.getPayChannel()));
		}
		requestParam.put("return_url", param.getReturnUrl());
		requestParam.put("notify_url", param.getOrderNotifyUrl());
		requestParam.put("subject", "subject");
		requestParam.put("body", "body");
		requestParam.put("remark", "remark");
		requestParam.put("pay_amount", param.getAmount());
		requestParam.put("applydate", applydate);
		requestParam.put("attach", "attach");
		TreeMap<String, String> map = getOrderContentMap(param, applydate);
		String data = SignUtil.sortSign(map);
		data += "&apikey=" + Config.APPSECRET;
		String sign = Md5Util.md5(data).toUpperCase();
		requestParam.put("sign", sign);
		String result = HttpUtil.post(Config.ORDER_URL, requestParam.toString());
		log.info("[KkkkPay] requestParam {} result {}", requestParam, result);
		JSONObject jsonResult = null;
		try {
			jsonResult = JSON.parseObject(result);
		} catch (Exception e) {
			log.error("JSON.parseObject error:{}",e.getMessage());
		}
		if (jsonResult == null) {
			return ResultUtils.getErrorResult(500, "下单失败！", null);
		}
		if (!KkkkpayResultUtil.success(jsonResult.getIntValue("code"))) {
			return ResultUtils.getErrorResult(jsonResult.getIntValue("code"), jsonResult.getString("msg"), null);
		}
		JSONObject jsonData = jsonResult.getJSONObject("data").getJSONObject("pay_extends");
		String tradeNo = jsonResult.getJSONObject("data").getString("trade_no");
		ResultData resultData = new ResultData(jsonData.getString("pay_url"), param.getOrderNo(), tradeNo);
		log.info("[KkkkPay]->HttpUtil.post requestParam {} result {}", requestParam, result);
		return ResultUtils.getSuccessObject(resultData);
	}

	/**
	 * 测试下单数据组装
	 *
	 * @param order_trano_in
	 *            商户订单号
	 * @return 测试下单数据
	 */
	private static TreeMap<String, String> getOrderContentMap(PayParam param, String applydate) {
		TreeMap<String, String> map = new TreeMap<String, String>();
		map.put("user_id", Config.APPID);
		map.put("out_trade_no", param.getOrderNo());
		if (PayPlatform.PC == param.getPlatform()) {
			map.put("product_id", Config.PAY_CHANNEL_PC.get(param.getPayChannel()));
		} else {
			map.put("product_id", Config.PAY_CHANNEL_H5.get(param.getPayChannel()));
		}
		map.put("return_url", param.getReturnUrl());
		map.put("notify_url", param.getOrderNotifyUrl());
		map.put("subject", "subject");
		map.put("body", "body");
		map.put("remark", "remark");
		map.put("pay_amount", String.valueOf(param.getAmount()));
		map.put("applydate", applydate);
		return map;
	}

	public static void main(String[] args) {
		PayParam param = new PayParam();
		param.setAmount(10);
		param.setGoods("aaa");
		param.setGoodsNum("1");
		param.setGoodsPrice("11");
		param.setPlatform(PayPlatform.H5);
		param.setPayChannel("weixin");
		param.setOrderNo(String.valueOf(System.currentTimeMillis()));
		param.setOrderNotifyUrl("http://zxyy-hsk.qicp.io:11312/V1.0/pay/kkkkpay/payCallback");
		param.setReturnUrl("http://zxyy-hsk.qicp.io:11312/V1.0/pay/kkkkpay/payCallback");
		GlobalResult<ResultData> result = new KkkkPay().doPay(param);
		System.out.println(result);
	}

	@Override
	protected String getName() {
		// TODO Auto-generated method stub
		return Config.name;
	}

	@Override
	protected void init(SysConfigMessage commonConfig,String callbackUrl) {
		if (null == commonConfig) {
			return;
		}
		if (!Config.name.equals(commonConfig.getValue())) {
			log.info("支付公司 {} 配置初始化失败 commonConfig {}", getName(), commonConfig);
		}
		PayConfigUtils.setPayConfig(commonConfig, kkkPayConfig,callbackUrl);
		Config.APPID = kkkPayConfig.getUid();
		Config.APPSECRET = kkkPayConfig.getSecret();
		Config.ORDER_URL = kkkPayConfig.getOrderUrl();
		if (null != kkkPayConfig.getProduct()) {
			Config.PAY_CHANNEL_H5 = kkkPayConfig.getProduct().get(PayPlatform.H5);
			Config.PAY_CHANNEL_PC = kkkPayConfig.getProduct().get(PayPlatform.PC);
		}
		Config.ORDER_NOTIFY_URL = kkkPayConfig.getCallbackUrl();
		Config.ORDER_RETURN_URL = kkkPayConfig.getCallbackUrl();
		log.info("支付公司 {} 配置初始化 config {}", getName(), kkkPayConfig);
	}

	/**
	 * 校验支付渠道是否存在
	 */
	@Override
	public boolean payChannelVerify(String payChannel, PayPlatform payPlatform) {
		if (PayPlatform.PC == payPlatform) {
			return Config.PAY_CHANNEL_PC.get(payChannel) != null;
		}
		return Config.PAY_CHANNEL_H5.get(payChannel) != null;
	}

}
