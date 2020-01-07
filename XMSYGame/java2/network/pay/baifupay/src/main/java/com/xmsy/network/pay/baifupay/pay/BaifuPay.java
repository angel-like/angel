package com.xmsy.network.pay.baifupay.pay;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import com.xmsy.common.bean.message.SysConfigMessage;
import com.xmsy.common.bean.payment.ResultData;
import com.xmsy.common.define.result.GlobalResult;
import com.xmsy.common.define.result.ResultUtils;
import com.xmsy.network.pay.baifupay.config.BaifuPayConfig;
import com.xmsy.network.pay.baifupay.def.Config;
import com.xmsy.network.pay.baifupay.utils.ResultUtil;
import com.xmsy.network.pay.paybase.define.PayPlatform;
import com.xmsy.network.pay.paybase.param.PayParam;
import com.xmsy.network.pay.paybase.pay.impl.PayServiceBase;
import com.xmsy.network.pay.paybase.utils.Md5Util;
import com.xmsy.network.pay.paybase.utils.PayConfigUtils;
import com.xmsy.network.pay.paybase.utils.StringUtil;

import cn.hutool.http.HttpUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * .佰富支付
 *
 * @author aleng
 * @date 2018年11月6日
 * @version 1.0
 */
@Slf4j
@Component
public class BaifuPay extends PayServiceBase {

	private final static String title = "零用品";

	@Resource
	private BaifuPayConfig baifuPayConfig;

	public BaifuPay() {
		super.getPayServiceMap().put(getName(), this);
	}

	@Override
	protected GlobalResult<ResultData> doPay(PayParam param) {
		log.info("[BaifuPay] PayParam {}", param);
		param.setOrderNotifyUrl(baifuPayConfig.getCallbackUrl());
		param.setReturnUrl(baifuPayConfig.getCallbackUrl());
		TreeMap<String, Object> map = getOrderContentMap(param);
		String signDataStr = JSON.toJSONString(map) + Config.APPSECRET;// 拼装签名串
		String sign = Md5Util.md5(signDataStr).toUpperCase(); // 加密字符串
		map.put("sign", sign);
		String requestParamStr = JSON.toJSONString(map);
		String reqparam = "paramData=" + requestParamStr;
		log.info("[BaifuPay] param= {} reqparam={}", param, reqparam);
		String result = HttpUtil.post(Config.ORDER_URL, reqparam);
		log.info("[BaifuPay] reqparam {}  result {} ", reqparam, result);
		// 解析返回结果，并对返回结果进行key排序
		JSONObject jsonResult = JSON.parseObject(result, Feature.OrderedField);
		if (!ResultUtil.success(jsonResult.getString("resultCode"))) {
			log.error("[BaifuPay] 请求支付失败  result {}", result);
			return ResultUtils.getErrorResult(jsonResult.getString("resultMsg"));
		}
		String resultSign = (String) jsonResult.remove("sign");
		Map<String, Object> resultMap = jsonResult.getInnerMap();
		resultMap.remove("sign");
		String resultSignDataStr = mapToJSON(resultMap) + Config.APPSECRET;// 拼装签名串
		String targetSign = Md5Util.md5(resultSignDataStr).toUpperCase();
		// 验签
		if (!resultSign.equals(targetSign)) {
			log.error("[BaifuPay] 请求支付返回验签失败  result {}", result);
			return ResultUtils.getErrorResult(jsonResult.getString("resultMsg"));
		}
		String payUrl = jsonResult.getString("CodeUrl");
		String outTradeNo = jsonResult.getString("orderNum");
		ResultData resultData = new ResultData(payUrl, param.getOrderNo(), outTradeNo);
		log.info("[BaifuPay]-> 支付返回结果： {} resultData {}", param, resultData);
		return ResultUtils.getSuccessObject(resultData);
	}

	public static void main(String[] args) {
		String orderNum = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()); // 20位
		PayParam param = new PayParam().setAmount(20).setPayChannel("weixin").setOrderIp("192.168.0.166")
				.setPlatform(PayPlatform.H5).setOrderNo(orderNum)
				.setOrderNotifyUrl("http://zxyy-hsk.qicp.io:11312/V1.0/pay/baifuPay/payCallback");
		System.out.println(new BaifuPay().doPay(param));
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
		// 支付产品号
		if (PayPlatform.PC == param.getPlatform()) {
			map.put("netwayCode", Config.PAY_CHANNEL_PC.get(param.getPayChannel()));
		}
		if (PayPlatform.H5 == param.getPlatform()) {
			map.put("netwayCode", Config.PAY_CHANNEL_H5.get(param.getPayChannel()));
		}
		// 订单号
		map.put("orderNum", param.getOrderNo());
		// 金额，单位分
		map.put("payAmount", String.valueOf(param.getAmount() * 100));
		// 商品名称
		map.put("goodsName", title);
		// 回调地址
		map.put("callBackUrl", param.getOrderNotifyUrl());
		// 回显地址
		map.put("frontBackUrl", param.getOrderNotifyUrl());
		map.put("requestIP", param.getOrderIp());
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
		PayConfigUtils.setPayConfig(commonConfig, baifuPayConfig,callbackUrl);
		Config.APPID = StringUtil.replaceBlank(baifuPayConfig.getUid());
		Config.APPSECRET = baifuPayConfig.getSecret();
		Config.ORDER_URL = baifuPayConfig.getOrderUrl();
		if (null != baifuPayConfig.getProduct()) {
			Config.PAY_CHANNEL_H5 = baifuPayConfig.getProduct().get(PayPlatform.H5);
			Config.PAY_CHANNEL_PC = baifuPayConfig.getProduct().get(PayPlatform.PC);
		}
		log.info("支付公司 {} 配置初始化 config {}", getName(), baifuPayConfig);
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
