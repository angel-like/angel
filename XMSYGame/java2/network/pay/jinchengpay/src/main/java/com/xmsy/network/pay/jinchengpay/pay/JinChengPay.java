package com.xmsy.network.pay.jinchengpay.pay;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.Resource;

import com.xmsy.network.pay.jinchengpay.config.JinChengPayConfig;
import com.xmsy.network.pay.jinchengpay.def.Config;
import com.xmsy.network.pay.paybase.utils.SignUtil;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.xmsy.common.bean.message.SysConfigMessage;
import com.xmsy.common.bean.payment.ResultData;
import com.xmsy.common.define.result.GlobalResult;
import com.xmsy.common.define.result.ResultUtils;
import com.xmsy.network.pay.paybase.define.PayPlatform;
import com.xmsy.network.pay.paybase.param.PayParam;
import com.xmsy.network.pay.paybase.pay.impl.PayServiceBase;
import com.xmsy.network.pay.paybase.utils.Md5Util;
import com.xmsy.network.pay.paybase.utils.PayConfigUtils;
import com.xmsy.network.pay.paybase.utils.StringUtil;

import cn.hutool.http.HttpUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * .恒通支付
 *
 * @author ahui
 * @date 2018年11月6日
 * @version 1.0
 */
@Slf4j
@Component
public class JinChengPay extends PayServiceBase {

	private final static String title = "零用品";

	@Resource
	private JinChengPayConfig jinChengPayConfig;

	public JinChengPay() {
		super.getPayServiceMap().put(getName(), this);
	}

	@Override
	protected GlobalResult<ResultData> doPay(PayParam param) {
		log.info("[jinchengPay] PayParam {}", param);//PayParam(orderNo=13348764231920kkcudb, amount=10, goods=null, goodsPrice=null, goodsNum=null, orderIp=10.0.0.160, returnUrl=null, orderNotifyUrl=null, payChannel=alipay, platform=H5)
		param.setOrderNotifyUrl(jinChengPayConfig.getCallbackUrl());
		param.setReturnUrl(jinChengPayConfig.getCallbackUrl());
		TreeMap<String, String> map = getOrderContentMap(param);
		String data = SignUtil.sortSign(map);
		data += "&key=" + Config.APPSECRET;
		String sign = Md5Util.md5(data).toLowerCase();// 加密字符串
		JSONObject requestParam = new JSONObject();//封装要请求支付的参数
		requestParam.put("merchant_code", Config.APPID);
		if (PayPlatform.PC == param.getPlatform()) {
			requestParam.put("trade_type", Config.PAY_CHANNEL_PC.get(param.getPayChannel()));
		} else {
			requestParam.put("trade_type", Config.PAY_CHANNEL_H5.get(param.getPayChannel()));
		}
		requestParam.put("amount", map.get("amount"));
		requestParam.put("order_no", param.getOrderNo());
		requestParam.put("notify_url", param.getOrderNotifyUrl());
		requestParam.put("source_ip", param.getOrderIp());
//		requestParam.put("create_time", map.get("request_time"));
		requestParam.put("sign", sign);
		log.info("[jinchengPay] param= {} reqparam={}", requestParam);
		String result = HttpUtil.post(Config.ORDER_URL, requestParam);
		log.info("[jinchengPay] reqparam {}  result {} ",requestParam, result);
		String payUrl =null;
		ResultData resultData = new ResultData(payUrl, param.getOrderNo(), "");
		log.info("[jinchengPay]-> 支付返回结果： {} resultData {}", payUrl, resultData);
		return ResultUtils.getSuccessObject(resultData);
	}

	public static void main(String[] args) {
		String orderNum = new SimpleDateFormat("yyyymmddHHmmss").format(new Date()); // 20位
		PayParam param = new PayParam().setAmount(20).setPayChannel("weixin").setOrderIp("192.168.0.166")
				.setPlatform(PayPlatform.H5).setOrderNo(orderNum)
				.setOrderNotifyUrl("http://zxyy-hsk.qicp.io:11312/V1.0/pay/baifuPay/payCallback");
		System.out.println(new JinChengPay().doPay(param));
	}

	/**
	 * 测试下单数据组装
	 *            商户订单号
	 * @return 测试下单数据
	 */
	private static TreeMap<String, String> getOrderContentMap(PayParam param) {
		TreeMap<String, String> map = new TreeMap<String, String>();
		map.put("merchant_code", Config.APPID);
		// 支付产品号，银行类型
		if (PayPlatform.PC == param.getPlatform()) {
			map.put("trade_type", Config.PAY_CHANNEL_PC.get(param.getPayChannel()));
		}
		if (PayPlatform.H5 == param.getPlatform()) {
			map.put("trade_type", Config.PAY_CHANNEL_H5.get(param.getPayChannel()));
		}
		// 订单号
		map.put("order_no", param.getOrderNo());
		// 金额，单位元
		map.put("amount", String.valueOf(new BigDecimal(param.getAmount()).setScale(2)));
		// 商品名称
		map.put("source_ip", param.getOrderIp());
		// 回调地址
		map.put("notify_url", param.getOrderNotifyUrl());
		//请求时间
//		map.put("request_time",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		return map;
	}

	// map转json
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
		PayConfigUtils.setPayConfig(commonConfig, jinChengPayConfig,callbackUrl);
		Config.APPID = StringUtil.replaceBlank(jinChengPayConfig.getUid());
		Config.APPSECRET = jinChengPayConfig.getSecret();
		Config.ORDER_URL = jinChengPayConfig.getOrderUrl();
		if (null != jinChengPayConfig.getProduct()) {
			Config.PAY_CHANNEL_H5 = jinChengPayConfig.getProduct().get(PayPlatform.H5);
			Config.PAY_CHANNEL_PC = jinChengPayConfig.getProduct().get(PayPlatform.PC);
		}
		log.info("支付公司 {} 配置初始化 config {}", getName(), jinChengPayConfig);
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
