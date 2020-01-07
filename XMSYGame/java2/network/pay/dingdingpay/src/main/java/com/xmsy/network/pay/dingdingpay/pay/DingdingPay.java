package com.xmsy.network.pay.dingdingpay.pay;

import java.util.Map;
import java.util.TreeMap;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.xmsy.common.bean.message.SysConfigMessage;
import com.xmsy.common.bean.payment.ResultData;
import com.xmsy.common.define.result.GlobalResult;
import com.xmsy.common.define.result.ResultUtils;
import com.xmsy.network.pay.dingdingpay.config.DingdingPayConfig;
import com.xmsy.network.pay.dingdingpay.def.Config;
import com.xmsy.network.pay.dingdingpay.utils.ResultUtil;
import com.xmsy.network.pay.paybase.define.PayPlatform;
import com.xmsy.network.pay.paybase.param.PayParam;
import com.xmsy.network.pay.paybase.pay.impl.PayServiceBase;
import com.xmsy.network.pay.paybase.utils.Md5Util;
import com.xmsy.network.pay.paybase.utils.PayConfigUtils;
import com.xmsy.network.pay.paybase.utils.SignUtil;

import cn.hutool.http.HttpUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * .钉钉支付
 *
 * @author aleng
 * @date 2018年11月6日
 * @version 1.0
 */
@Slf4j
@Component
public class DingdingPay extends PayServiceBase {

	@Resource
	private DingdingPayConfig dingdingpayConfig;

	public DingdingPay() {
		super.getPayServiceMap().put(getName(), this);
	}

	@Override
	protected GlobalResult<ResultData> doPay(PayParam param) {
		param.setOrderNotifyUrl(dingdingpayConfig.getCallbackUrl());
		param.setReturnUrl(dingdingpayConfig.getCallbackUrl());
		log.info("[dingdingpay] PayParam {}", param);
		TreeMap<String, Object> map = getOrderContentMap(param);
		String data = SignUtil.sortSignTreeMap(map);
		data = data + "&key=" + Config.APPSECRET;
		log.info("[dingdingpay] data= {}", data);
		String sign = Md5Util.md5(data).toLowerCase();
		map.put("sign", sign);
		String postJson = JSON.toJSONString(map);
		log.info("[dingdingpay] sign= {}", sign);
		String result = HttpUtil.post(Config.ORDER_URL, postJson);
		log.info("[dingdingpay] requestParam {} result {}", postJson, result);
		JSONObject jsonResult = null;
		try {
			jsonResult = JSON.parseObject(result);
		} catch (Exception e) {
			return ResultUtils.getErrorResult(result);
		}
		if (!ResultUtil.prePaySuccess(jsonResult.getString("status"))) {
			return ResultUtils.getErrorResult(jsonResult.getIntValue("status"), jsonResult.getString("message"), null);
		}
		String tradeNo = jsonResult.getString("orderNo");
		ResultData resultData = new ResultData(jsonResult.getString("payUrl"), param.getOrderNo(), tradeNo);
		log.info("[dingdingpay]->HttpUtil.post requestParam {} result {}", map, result);
		return ResultUtils.getSuccessObject(resultData, param.getOrderIp());
	}

	/**
	 * 测试下单数据组装
	 *
	 * @param order_trano_in
	 *            商户订单号
	 * @return 测试下单数据
	 */
	private static TreeMap<String, Object> getOrderContentMap(PayParam param) {
		TreeMap<String, Object> map = new TreeMap<String, Object>();
		map.put("userId", Config.APPID);
		map.put("amount", String.valueOf(param.getAmount() * 100));
		map.put("requestNo", param.getOrderNo());
		if (PayPlatform.PC == param.getPlatform()) {
			map.put("type", Config.PAY_CHANNEL_PC.get(param.getPayChannel()));
		} else {
			map.put("type", Config.PAY_CHANNEL_H5.get(param.getPayChannel()));
		}
		map.put("callBackURL", param.getOrderNotifyUrl());
		map.put("redirectUrl", param.getOrderNotifyUrl());
		map.put("version", "v1.0");
		return map;
	}

	public static void main(String[] args) {
		Map<String, String> payChannelH5 = Maps.newConcurrentMap();
		payChannelH5.put("alipay", "aliPayH5");
		payChannelH5.put("weixin", "wxPayH5");
		Map<String, String> payChannelPC = Maps.newConcurrentMap();
		payChannelPC.put("alipay", "aliPaySM");
		payChannelPC.put("weixin", "wxPaySM");
		Config.PAY_CHANNEL_H5 = payChannelH5;
		Config.PAY_CHANNEL_PC = payChannelPC;
		Config.ORDER_URL = "http://47.110.245.153:8080/api/pay";
		Config.APPID = "881422";
		Config.APPSECRET = "88606EC4C8A543D5ADEFED6BCA184662";
		PayParam param = new PayParam();
		param.setAmount(1000);
		param.setGoods("aaa");
		param.setGoodsNum("1");
		param.setGoodsPrice("11");
		param.setPlatform(PayPlatform.H5);
		param.setPayChannel("alipay");
		param.setOrderNo(String.valueOf(System.currentTimeMillis()));
		param.setOrderNotifyUrl("http://zxyy-hsk.qicp.io:40192/v1/payment/dingdingpay");
		param.setReturnUrl("http://zxyy-hsk.qicp.io:40192/v1/payment/dingdingpay");
		param.setOrderIp("192.168.0.1");
		GlobalResult<ResultData> result = new DingdingPay().doPay(param);
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
		PayConfigUtils.setPayConfig(commonConfig, dingdingpayConfig,callbackUrl);
		Config.APPID = dingdingpayConfig.getUid();
		Config.APPSECRET = dingdingpayConfig.getSecret();
		Config.ORDER_URL = dingdingpayConfig.getOrderUrl();
		Config.PAY_CHANNEL_H5 = dingdingpayConfig.getProduct().get(PayPlatform.H5);
		Config.PAY_CHANNEL_PC = dingdingpayConfig.getProduct().get(PayPlatform.PC);
		log.info("支付公司 {} 配置初始化 config {}", getName(), dingdingpayConfig);
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
