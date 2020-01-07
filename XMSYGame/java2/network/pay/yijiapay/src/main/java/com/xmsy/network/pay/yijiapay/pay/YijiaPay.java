package com.xmsy.network.pay.yijiapay.pay;

import java.util.TreeMap;

import javax.annotation.Resource;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xmsy.common.bean.message.SysConfigMessage;
import com.xmsy.common.bean.payment.ResultData;
import com.xmsy.common.define.result.GlobalResult;
import com.xmsy.common.define.result.ResultUtils;
import com.xmsy.network.pay.paybase.define.PayPlatform;
import com.xmsy.network.pay.paybase.param.PayParam;
import com.xmsy.network.pay.paybase.pay.impl.PayServiceBase;
import com.xmsy.network.pay.paybase.utils.PayConfigUtils;
import com.xmsy.network.pay.paybase.utils.SignUtil;
import com.xmsy.network.pay.yijiapay.config.YijiaPayConfig;
import com.xmsy.network.pay.yijiapay.def.Config;
import com.xmsy.network.pay.yijiapay.utils.ResultUtil;

import cn.hutool.http.HttpUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * .一加支付
 *
 * @author aleng
 * @date 2018年11月6日
 * @version 1.0
 */
@Slf4j
@Component
public class YijiaPay extends PayServiceBase {

	@Resource
	private YijiaPayConfig yijiapayConfig;

	public YijiaPay() {
		super.getPayServiceMap().put(getName(), this);
	}

	@Override
	protected GlobalResult<ResultData> doPay(PayParam param) {
		param.setOrderNotifyUrl(yijiapayConfig.getCallbackUrl());
		param.setReturnUrl(yijiapayConfig.getCallbackUrl());
		log.info("[YijiaPay] PayParam {}", param);
		TreeMap<String, Object> map = getOrderContentMap(param);
		String data = SignUtil.sortSignTreeMap(map);
		data = data + "&key=" + Config.APPSECRET;
		log.info("[YijiaPay] data= {}", data);
		String sign = DigestUtils.sha1Hex(data.getBytes());
		map.put("sign", sign);
		log.info("[YijiaPay] sign= {}", sign);
		String result = HttpUtil.post(Config.ORDER_URL, map);
		log.info("[YijiaPay] requestParam {} result {}", map, result);
		JSONObject jsonResult = JSON.parseObject(result);
		if (!ResultUtil.success(jsonResult.getString("code"))) {
			return ResultUtils.getErrorResult(jsonResult.getIntValue("code"), jsonResult.getString("msg"), null);
		}
		JSONObject jsonData = jsonResult.getJSONObject("result");
		String tradeNo = jsonResult.getJSONObject("result").getString("orderNo");
		ResultData resultData = new ResultData(jsonData.getString("qrCode"), param.getOrderNo(), tradeNo);
		log.info("[YijiaPay]->HttpUtil.post requestParam {} result {}", map, result);
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
		map.put("merchant", Config.APPID);
		map.put("amount", String.valueOf(param.getAmount() * 100));
		map.put("order_no", param.getOrderNo());
		if (PayPlatform.PC == param.getPlatform()) {
			map.put("pay_type", Config.PAY_CHANNEL_PC.get(param.getPayChannel()));
		} else {
			map.put("pay_type", Config.PAY_CHANNEL_H5.get(param.getPayChannel()));
		}
		String orderTime = String.valueOf(System.currentTimeMillis());
		map.put("notify_url", param.getOrderNotifyUrl());
		map.put("callback_url", param.getOrderNotifyUrl());
		map.put("subject", "subject");
		map.put("order_time", String.valueOf(orderTime.substring(0, orderTime.length() - 3)));
		return map;
	}

	public static void main(String[] args) {
		// Map<String, String> payChannelH5 = Maps.newConcurrentMap();
		// payChannelH5.put("alipay", "wap");
		// payChannelH5.put("weixin", "wxwap");
		// Map<String, String> payChannelPC = Maps.newConcurrentMap();
		// payChannelPC.put("alipay", "qrcode");
		// payChannelPC.put("weixin", "wxqrcode");
		// Config.PAY_CHANNEL_H5 = payChannelH5;
		// Config.PAY_CHANNEL_PC = payChannelPC;
		// Config.ORDER_URL = "http://api.itzoon.com/api/addOrder";
		// Config.APPID = "10009";
		// Config.APPSECRET = "7A146D6043A049F198811458B1CF4BA2";
		// PayParam param = new PayParam();
		// param.setAmount(1000);
		// param.setGoods("aaa");
		// param.setGoodsNum("1");
		// param.setGoodsPrice("11");
		// param.setPlatform(PayPlatform.H5);
		// param.setPayChannel("alipay");
		// param.setOrderNo(String.valueOf(System.currentTimeMillis()));
		// param.setOrderNotifyUrl("http://zxyy-hsk.qicp.io:40192/v1/payment/yijiapay");
		// param.setReturnUrl("http://zxyy-hsk.qicp.io:40192/v1/payment/yijiapay");
		// param.setOrderIp("192.168.0.1");
		// GlobalResult<ResultData> result = new YijiaPay().doPay(param);
		// System.out.println(result);
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
		PayConfigUtils.setPayConfig(commonConfig, yijiapayConfig,callbackUrl);
		Config.APPID = yijiapayConfig.getUid();
		Config.APPSECRET = yijiapayConfig.getSecret();
		Config.ORDER_URL = yijiapayConfig.getOrderUrl();
		Config.PAY_CHANNEL_H5 = yijiapayConfig.getProduct().get(PayPlatform.H5);
		Config.PAY_CHANNEL_PC = yijiapayConfig.getProduct().get(PayPlatform.PC);
		log.info("支付公司 {} 配置初始化 config {}", getName(), yijiapayConfig);
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
