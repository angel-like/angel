package com.xmsy.network.pay.hr.pay;

import java.util.Arrays;
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
import com.xmsy.network.pay.hr.config.HrPayConfig;
import com.xmsy.network.pay.hr.def.Config;
import com.xmsy.network.pay.hr.utils.ResultUtil;
import com.xmsy.network.pay.paybase.define.PayPlatform;
import com.xmsy.network.pay.paybase.param.PayParam;
import com.xmsy.network.pay.paybase.pay.impl.PayServiceBase;
import com.xmsy.network.pay.paybase.utils.Md5Util;
import com.xmsy.network.pay.paybase.utils.PayConfigUtils;

import cn.hutool.http.HttpUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * .恒润支付
 *
 * @author aleng
 * @date 2018年11月6日
 * @version 1.0
 */
@Slf4j
@Component
public class HrPay extends PayServiceBase {

	private final static String title = "零用品";

	@Resource
	private HrPayConfig hrPayConfig;

	public HrPay() {
		super.getPayServiceMap().put(getName(), this);
	}

	@Override
	protected GlobalResult<ResultData> doPay(PayParam param) {
		param.setOrderNotifyUrl(hrPayConfig.getCallbackUrl());
		param.setReturnUrl(hrPayConfig.getCallbackUrl());
		log.info("[HrPay] PayParam {}", param);
		TreeMap<String, Object> map = getOrderContentMap(param);
		String signDataStr = getSignDataStr(map, Config.APPSECRET);// 拼装签名串
		String sign = Md5Util.md5(signDataStr).toUpperCase(); // 加密字符串
		JSONObject requestParam = new JSONObject(map);
		requestParam.put("sign", sign);
		log.info("[HrPay] sign= {}", sign);
		String requestParamStr = "ApplyParams=" + requestParam.toString();
		String result = HttpUtil.post(Config.ORDER_URL, requestParamStr);
		log.info("[HrPay] requestParam {} result {}", requestParam, result);
		// 解析返回结果，并对返回结果进行key排序
		JSONObject jsonResult = JSON.parseObject(result, Feature.OrderedField);
		if (!ResultUtil.success(jsonResult.getString("stateCode"))) {
			log.error("[HrPay] 请求支付失败  result {}", result);
			return ResultUtils.getErrorResult(jsonResult.getString("stateInfo"));
		}
		// 验签
		String resultSign = (String) jsonResult.remove("sign");
		Map<String, Object> resultMap = jsonResult.getInnerMap();
		resultMap.remove("sign");
		String resultSignDataStr = getSignDataStr(resultMap, Config.APPSECRET);// 拼装签名串
		String targetSign = Md5Util.md5(resultSignDataStr).toUpperCase();
		if (resultSign.equals(targetSign)) {
			log.info("验签成功");
		} else {
			log.error("[HrPay] 请求支付返回验签失败  result {}", result);
			return ResultUtils.getErrorResult(jsonResult.getString("stateInfo"));
		}
		String payUrl = jsonResult.getString("payURL");
		String outTradeNo = jsonResult.getString("outTradeNo");
		ResultData resultData = new ResultData(payUrl, param.getOrderNo(), outTradeNo);
		log.info("[HrPay]->HttpUtil.post requestParam {} result {}", requestParam, result);
		return ResultUtils.getSuccessObject(resultData);
	}

	public static void main(String[] args) {
		PayParam param = new PayParam().setAmount(20).setPayChannel("").setOrderIp("192.168.0.166")
				.setPlatform(PayPlatform.H5).setOrderNo(System.currentTimeMillis() + "")
				.setOrderNotifyUrl("http://zxyy-hsk.qicp.io:11312/V1.0/pay/hrpay/payCallback");
		log.info("[HrPay] PayParam {}", param);
		System.out.println(new HrPay().doPay(param));
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
		map.put("appID", Config.APPID);
		// 随机码
		map.put("randomNo", String.valueOf((new Random().nextInt(10000))));
		// 支付产品号
		if (PayPlatform.PC == param.getPlatform()) {
			map.put("tradeCode", Config.PAY_CHANNEL_PC.get(param.getPayChannel()));
		}
		if (PayPlatform.H5 == param.getPlatform()) {
			map.put("tradeCode", Config.PAY_CHANNEL_H5.get(param.getPayChannel()));
		}
		// 订单号
		map.put("outTradeNo", param.getOrderNo());
		// 金额，单位分
		map.put("totalAmount", String.valueOf(param.getAmount() * 100));
		// 商品名称
		map.put("productTitle", title);
		// 回调地址
		map.put("notifyUrl", param.getOrderNotifyUrl());
		// 支付ip
		map.put("tradeIP", param.getOrderIp());
		return map;
	}

	private static String getSignDataStr(Map<String, Object> reqDataMap, String md5Key) {
		if (reqDataMap != null) {
			// 键值排序
			Object[] keys = reqDataMap.keySet().toArray();
			Arrays.sort(keys);
			// 拼装字符串
			StringBuffer params = new StringBuffer();
			for (Map.Entry<String, Object> map : reqDataMap.entrySet()) {
				if (null == map.getValue()) {
					continue;
				}
				params.append(map.getValue().toString());
				params.append("|");
			}
			params.append(md5Key);
			return params.toString();
		}
		return null;
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
		PayConfigUtils.setPayConfig(commonConfig, hrPayConfig,callbackUrl);
		Config.APPID = hrPayConfig.getUid();
		Config.APPSECRET = hrPayConfig.getSecret();
		Config.ORDER_URL = hrPayConfig.getOrderUrl();
		if (null != hrPayConfig.getProduct()) {
			Config.PAY_CHANNEL_H5 = hrPayConfig.getProduct().get(PayPlatform.H5);
			Config.PAY_CHANNEL_PC = hrPayConfig.getProduct().get(PayPlatform.PC);
		}
		log.info("支付公司 {} 配置初始化 config {}", getName(), hrPayConfig);
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
