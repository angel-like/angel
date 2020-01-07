package com.xmsy.network.pay.htpay.pay;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONObject;
import com.xmsy.common.bean.message.SysConfigMessage;
import com.xmsy.common.bean.payment.ResultData;
import com.xmsy.common.define.result.GlobalResult;
import com.xmsy.common.define.result.ResultUtils;
import com.xmsy.network.pay.htpay.config.HtPayConfig;
import com.xmsy.network.pay.htpay.def.Config;
import com.xmsy.network.pay.paybase.define.PayPlatform;
import com.xmsy.network.pay.paybase.param.PayParam;
import com.xmsy.network.pay.paybase.pay.impl.PayServiceBase;
import com.xmsy.network.pay.paybase.utils.Md5Util;
import com.xmsy.network.pay.paybase.utils.PayConfigUtils;
import com.xmsy.network.pay.paybase.utils.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * .佰富支付
 *
 * @author aleng
 * @date 2018年11月6日
 * @version 1.0
 */
@Slf4j
@Component
public class HtPay extends PayServiceBase {

	private final static String title = "零用品";

	@Resource
	private HtPayConfig htPayConfig;

	public HtPay() {
		super.getPayServiceMap().put(getName(), this);
	}

	@Override
	protected GlobalResult<ResultData> doPay(PayParam param) {
		log.info("[BaifuPay] PayParam {}", param);//PayParam(orderNo=13348764231920kkcudb, amount=10, goods=null, goodsPrice=null, goodsNum=null, orderIp=10.0.0.160, returnUrl=null, orderNotifyUrl=null, payChannel=alipay, platform=H5)
		param.setOrderNotifyUrl(htPayConfig.getCallbackUrl());
		param.setReturnUrl(htPayConfig.getCallbackUrl());
		TreeMap<String, Object> map = getOrderContentMap(param);
		StringBuffer sb = new StringBuffer();//用于使用md5生成sign
		sb.append("customer="+Config.APPID);//商户ID
		sb.append("&banktype="+map.get("banktype"));//银行类型
		sb.append("&amount="+map.get("amount"));//金额
		sb.append("&orderid="+map.get("orderid"));
		sb.append("&asynbackurl="+map.get("asynbackurl"));
		sb.append("&request_time="+map.get("request_time"));
		sb.append("&key="+Config.APPSECRET);
		//加密源字符串  customer={0}&banktype={1}&amount={2}&orderid={3}&asynbackurl={4}&request_time={5}&key={6}  字母无序
		String sign = Md5Util.md5(sb.toString()).toLowerCase();// 加密字符串
		JSONObject requestParam = new JSONObject();//封装要请求支付的参数
		requestParam.put("customer", Config.APPID);
		if (PayPlatform.PC == param.getPlatform()) {
			requestParam.put("banktype", Config.PAY_CHANNEL_PC.get(param.getPayChannel()));
		} else {
			requestParam.put("banktype", Config.PAY_CHANNEL_H5.get(param.getPayChannel()));
		}
		requestParam.put("amount", map.get("amount"));
		requestParam.put("orderid", param.getOrderNo());
		requestParam.put("asynbackurl", param.getOrderNotifyUrl());
		requestParam.put("request_time", map.get("request_time"));
		requestParam.put("sign", sign);
		log.info("[HtPay] param= {} reqparam={}", requestParam);
		String result = HttpUtil.post(Config.ORDER_URL, requestParam);//返回的是html元素
		log.info("[HtPay] reqparam {}  result {} ",requestParam, result);
		String payUrl =null;
		if(result.contains("href")){
			int start = result.indexOf("href");
			int end = result.indexOf("here");
			payUrl = result.substring(start + 6, end-2);//<a href="http://www.zhaolong4.cc/c/?poid=D131919081714225899">here
		}
		ResultData resultData = new ResultData(payUrl, param.getOrderNo(), "");
		log.info("[htPay]-> 支付返回结果： {} resultData {}", payUrl, resultData);
		return ResultUtils.getSuccessObject(resultData);
	}

	public static void main(String[] args) {
		String orderNum = new SimpleDateFormat("yyyymmddHHmmss").format(new Date()); // 20位
		PayParam param = new PayParam().setAmount(20).setPayChannel("weixin").setOrderIp("192.168.0.166")
				.setPlatform(PayPlatform.H5).setOrderNo(orderNum)
				.setOrderNotifyUrl("http://zxyy-hsk.qicp.io:11312/V1.0/pay/baifuPay/payCallback");
		System.out.println(new HtPay().doPay(param));
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
		map.put("merchantNo", Config.APPID);
		// 随机码
		map.put("randomNum", String.valueOf((new Random().nextInt(10000))));
		// 支付产品号，银行类型
		if (PayPlatform.PC == param.getPlatform()) {
			map.put("banktype", Config.PAY_CHANNEL_PC.get(param.getPayChannel()));
		}
		if (PayPlatform.H5 == param.getPlatform()) {
			map.put("banktype", Config.PAY_CHANNEL_H5.get(param.getPayChannel()));
		}
		// 订单号
		map.put("orderid", param.getOrderNo());
		// 金额，单位元
		map.put("amount", new BigDecimal(param.getAmount()).setScale(2));
		// 商品名称
		map.put("goodsName", title);
		// 回调地址
		map.put("asynbackurl", param.getOrderNotifyUrl());
		// 回显地址
		map.put("frontBackUrl", param.getOrderNotifyUrl());
		map.put("requestIP", param.getOrderIp());
		//请求时间
		map.put("request_time",new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
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
		PayConfigUtils.setPayConfig(commonConfig, htPayConfig,callbackUrl);
		Config.APPID = StringUtil.replaceBlank(htPayConfig.getUid());
		Config.APPSECRET = htPayConfig.getSecret();
		Config.ORDER_URL = htPayConfig.getOrderUrl();
		if (null != htPayConfig.getProduct()) {
			Config.PAY_CHANNEL_H5 = htPayConfig.getProduct().get(PayPlatform.H5);
			Config.PAY_CHANNEL_PC = htPayConfig.getProduct().get(PayPlatform.PC);
		}
		log.info("支付公司 {} 配置初始化 config {}", getName(), htPayConfig);
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
