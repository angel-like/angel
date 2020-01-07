package com.xmsy.network.pay.wantongpay.pay;


import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.annotation.Resource;

import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.Feature;

import com.xmsy.network.pay.paybase.utils.Md5Util;
import com.xmsy.network.pay.paybase.utils.SignUtil;
import com.xmsy.network.pay.wantongpay.utils.ResultUtil;

import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.xmsy.common.bean.message.SysConfigMessage;
import com.xmsy.common.bean.payment.ResultData;
import com.xmsy.common.define.result.GlobalResult;
import com.xmsy.common.define.result.ResultUtils;
import com.xmsy.network.pay.paybase.define.PayPlatform;
import com.xmsy.network.pay.paybase.param.PayParam;
import com.xmsy.network.pay.paybase.pay.impl.PayServiceBase;
import com.xmsy.network.pay.paybase.utils.PayConfigUtils;
import com.xmsy.network.pay.paybase.utils.StringUtil;
import com.xmsy.network.pay.wantongpay.config.WanTongPayConfig;
import com.xmsy.network.pay.wantongpay.def.Config;

import cn.hutool.http.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

/**
 * .BT支付
 *
 * @author aleng
 * @date 2018年11月6日
 * @version 1.0
 */
@Slf4j
@Component
public class WanTongPay extends PayServiceBase {


	@Resource
	private WanTongPayConfig wanTongPayConfig;
	/**
	 * 构造函数 把别名和WanTong支付server放入PayServiceMap
	 */
	public WanTongPay() {
		super.getPayServiceMap().put(getName(), this);
	}

	@Override
	/**
	 * 重写支付请求接口
	 */
	protected GlobalResult<ResultData> doPay(PayParam param) {
		log.info("[wantongpay1] PayParam {}", param);
		param.setOrderNotifyUrl(wanTongPayConfig.getCallbackUrl());
		TreeMap<String, Object> map = getOrderContentMap(param);
		//加密源字符串
		String signDataStr = SignUtil.getSign(map)+ "key=" + Config.APPSECRET;// 拼装签名串
		log.info("[wantongpay3] signDataStr {} ", signDataStr);
		String sign = null;
		try {
			 sign = Md5Util.md5(signDataStr).toUpperCase();
		} catch (Exception e) {
			log.error("[wantongpay4] result to json fail  result {}", sign);
			return ResultUtils.getErrorResult("生成签名失败");
		}
		log.info("[wantongpay5] sign {} ", sign);
		JSONObject requestParam = new JSONObject();//封装要请求支付的参数
		try {
			requestParam.put("transdata", URLEncoder.encode(JSONObject.toJSONString(map), "UTF-8"));
			requestParam.put("sign",URLEncoder.encode(sign, "UTF-8") );
		} catch (Exception e) {
			e.printStackTrace();
		}
		requestParam.put("signtype","MD5" );
		log.info("[wantongpay6] param= {}", requestParam);
		String result = HttpUtil.post(Config.ORDER_URL, requestParam.toJSONString());
		log.info("[wantongpay7] reqparam {}  result {} ", requestParam.toJSONString(), result);
		// 解析返回结果，并对返回结果进行key排序
		JSONObject jsonResult = null;
		try {
			jsonResult = JSON.parseObject(result, Feature.OrderedField);
		} catch (Exception e) {
			log.error("[wantongpay8] result to json fail  result {}", result);
			return ResultUtils.getErrorResult("订单建立失败");
		}
		if (jsonResult == null) {
			log.error("[wantongpay9] result is null  result {}", result);
			return ResultUtils.getErrorResult("订单建立失败");
		}
		if (!ResultUtil.success(jsonResult.getString("payment"))) {
			log.error("[wantongpay10] code is error  result {}", result);
			String msg = jsonResult.getString("message");
			msg = msg == null ? "订单建立失败" : msg;
			return ResultUtils.getErrorResult(msg);
		}

		String payUrl = jsonResult.getString("payUrl");
		ResultData resultData = new ResultData(payUrl, param.getOrderNo(), null);
		log.info("[wantongpay11]->HttpUtil.post requestParam {} result {}", requestParam, result);
		return ResultUtils.getSuccessObject(resultData);
	}

	public static void main(String[] args) {
		String orderNum = new SimpleDateFormat("yyyymmddHHmmss").format(new Date()); // 20位
		PayParam param = new PayParam().setAmount(20).setPayChannel("weixin").setOrderIp("192.168.0.166")
				.setPlatform(PayPlatform.H5).setOrderNo(orderNum)
				.setOrderNotifyUrl("http://zxyy-hsk.qicp.io:11312/V1.0/pay/baifuPay/payCallback");
		System.out.println(new WanTongPay().doPay(param));
	}

	/**
	 * 测试下单数据组装
	 *
	 * @param order_trano_in
	 *            商户订单号
	 * @return 测试下单数据
	 */
	public static String getRandom2(int len) {
		Random r = new Random();
		StringBuilder rs = new StringBuilder();
		for (int i = 0; i < len; i++) {
			rs.append(r.nextInt(10));
		}
		return rs.toString();
	}
	public static char getRandomChar() {
		return (char) (0x4e00 + (int) (Math.random() * (0x9fa5 - 0x4e00 + 1)));
	}
	private static TreeMap<String, Object>  getOrderContentMap(PayParam param) {

		TreeMap<String, Object>  map = new TreeMap<String, Object> ();
		map.put("merchant_code",Config.APPID);
		map.put("appno_no",Config.APPNO);
		map.put("order_amount",param.getAmount());
		map.put("order_time", DateUtil.format(new Date(), "YYYYMMDDHHmmss"));
		map.put("order_no",param.getOrderNo());
		map.put("product_name",getRandomChar());
		map.put("product_code", getRandom2(24));
		if (PayPlatform.PC == param.getPlatform()) {
			map.put("pay_type", Config.PAY_CHANNEL_PC.get(param.getPayChannel()));
		} else {
			map.put("pay_type", Config.PAY_CHANNEL_H5.get(param.getPayChannel()));
		}
		map.put("user_no",param.getUserName());
		map.put("merchant_ip",param.getOrderIp());
		map.put("notify_url",param.getOrderNotifyUrl());
		map.put("return_url",param.getOrderNotifyUrl());
		return map;
	}

	/**
	 *  map转json
	 * @param map
	 * @return
	 */
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
	/**
	 * 取得支付通道的别名
	 */
	@Override
	protected String getName() {
		// TODO Auto-generated method stub
		return Config.name;
	}

	@Override
	/**
	 * 重写 支付的初始化
	 */
	protected void init(SysConfigMessage commonConfig,String callbackUrl) {
		if (null == commonConfig) {
			return;
		}
		if (!Config.name.equals(commonConfig.getValue())) {
			log.info("支付公司 {} 配置初始化失败 commonConfig {}", getName(), commonConfig);
		}
		PayConfigUtils.setPayConfig(commonConfig, wanTongPayConfig,callbackUrl);
		setAppNo(commonConfig,wanTongPayConfig);
		Config.APPID = StringUtil.replaceBlank(wanTongPayConfig.getUid());
		Config.APPNO = StringUtil.replaceBlank(wanTongPayConfig.getAppno());
		Config.APPSECRET = wanTongPayConfig.getSecret();
		Config.APPPRIVATEKEY= wanTongPayConfig.getPrivateKey();
		Config.ORDER_URL = wanTongPayConfig.getOrderUrl();
		if (null != wanTongPayConfig.getProduct()) {
			Config.PAY_CHANNEL_H5 = wanTongPayConfig.getProduct().get(PayPlatform.H5);
			Config.PAY_CHANNEL_PC = wanTongPayConfig.getProduct().get(PayPlatform.PC);
		}
		log.info("支付公司 {} 配置初始化 config {}", getName(), wanTongPayConfig);
	}
	private static final void setAppNo(SysConfigMessage commonConfig, WanTongPayConfig payConfig) {
		if (StringUtils.isEmpty(commonConfig.getChildren())) {
			return;
		}
		for (SysConfigMessage config : commonConfig.getChildren()) {
			if ("appno".equals(config.getName())) {
				payConfig.setAppno(StringUtil.replaceBlank(config.getValue()));
			}

		}

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
