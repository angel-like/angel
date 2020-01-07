package com.xmsy.network.pay.nowtopay.pay;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONObject;
import com.xmsy.common.bean.message.SysConfigMessage;
import com.xmsy.common.bean.payment.ResultData;
import com.xmsy.common.define.result.GlobalResult;
import com.xmsy.common.define.result.ResultUtils;
import com.xmsy.network.pay.nowtopay.config.NowToPayConfig;
import com.xmsy.network.pay.nowtopay.def.Config;
import com.xmsy.network.pay.paybase.define.PayPlatform;
import com.xmsy.network.pay.paybase.param.PayParam;
import com.xmsy.network.pay.paybase.pay.impl.PayServiceBase;
import com.xmsy.network.pay.paybase.utils.Md5Util;
import com.xmsy.network.pay.paybase.utils.PayConfigUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.TreeMap;

/**
 * .立刻付支付逻辑
 *
 * @author aleng
 * @date 2018年11月6日
 * @version 1.0
 */
@Slf4j
@Component
public class NowToPay extends PayServiceBase {

	@Resource
	private NowToPayConfig nowToPayConfig;

	public NowToPay() {
		super.getPayServiceMap().put(getName(), this);
	}

	@Override
	protected GlobalResult<ResultData> doPay(PayParam param) {
		log.info("[NowToPay] PayParam {}", param);
		param.setOrderNotifyUrl(nowToPayConfig.getCallbackUrl());
		param.setReturnUrl(nowToPayConfig.getCallbackUrl());
		JSONObject requestParam = new JSONObject();
		requestParam.put("partner", Config.APPID);
		if (PayPlatform.PC == param.getPlatform()) {
			requestParam.put("banktype", Config.PAY_CHANNEL_PC.get(param.getPayChannel()));
		} else {
			requestParam.put("banktype", Config.PAY_CHANNEL_H5.get(param.getPayChannel()));
		}
		requestParam.put("isshow", 0);
		requestParam.put("paymoney", param.getAmount());
		requestParam.put("ordernumber", param.getOrderNo());
		requestParam.put("callbackurl", param.getReturnUrl());
		requestParam.put("hrefbackurl", param.getOrderNotifyUrl());
		requestParam.put("attach", "attach");
		TreeMap<String, String> map = getOrderContentMap(param);
		StringBuffer sb = new StringBuffer();//用于使用md5生成sign
		sb.append("partner="+map.get("partner"));
		sb.append("&banktype="+map.get("banktype"));
		sb.append("&paymoney="+map.get("paymoney"));
		sb.append("&ordernumber="+map.get("ordernumber"));
		sb.append("&callbackurl="+map.get("callbackurl"));
//		partner={}&banktype={}&paymoney={}&ordernumber={}&callbackurl={}key 待加密源串
		String sign = Md5Util.md5(sb.toString()+Config.APPSECRET);
		requestParam.put("sign", sign);
		sb.append("&sign="+sign);
		String url = Config.ORDER_URL+"?"+sb.toString();
		log.info("url:{}",url);
		String result = HttpUtil.get(url);
		ResultData resultData = new ResultData(url, param.getOrderNo(), "");
		return ResultUtils.getSuccessObject(resultData);
	}

	/**
	 * 这些字段封装进map，用md5加密做成sign
	 * @return
	 */
	private static TreeMap<String, String> getOrderContentMap(PayParam param) {
		TreeMap<String, String> map = new TreeMap<String, String>();
		map.put("partner", Config.APPID);//商户id

		if (PayPlatform.PC == param.getPlatform()) {
			map.put("banktype", Config.PAY_CHANNEL_PC.get(param.getPayChannel()));
		} else {
			map.put("banktype", Config.PAY_CHANNEL_H5.get(param.getPayChannel()));//设置支付银行
		}
		map.put("paymoney", String.valueOf(param.getAmount()));//设置金额
		map.put("ordernumber", param.getOrderNo());//商品订单号
		map.put("callbackurl", param.getReturnUrl());
		map.put("attach", "attach");

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
		param.setOrderNotifyUrl("http://zxyy-hsk.qicp.io:11312/V1.0/pay/nowtopay/payCallback");
		param.setReturnUrl("http://zxyy-hsk.qicp.io:11312/V1.0/pay/nowtopay/payCallback");
		GlobalResult<ResultData> result = new NowToPay().doPay(param);
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
		PayConfigUtils.setPayConfig(commonConfig, nowToPayConfig,callbackUrl);
		Config.APPID = nowToPayConfig.getUid();
		Config.APPSECRET = nowToPayConfig.getSecret();
		Config.ORDER_URL = nowToPayConfig.getOrderUrl();
		if (null != nowToPayConfig.getProduct()) {
			Config.PAY_CHANNEL_H5 = nowToPayConfig.getProduct().get(PayPlatform.H5);
			Config.PAY_CHANNEL_PC = nowToPayConfig.getProduct().get(PayPlatform.PC);
		}
		Config.ORDER_NOTIFY_URL = nowToPayConfig.getCallbackUrl();
		Config.ORDER_RETURN_URL = nowToPayConfig.getCallbackUrl();
		log.info("支付公司 {} 配置初始化 config {}", getName(), nowToPayConfig);
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
