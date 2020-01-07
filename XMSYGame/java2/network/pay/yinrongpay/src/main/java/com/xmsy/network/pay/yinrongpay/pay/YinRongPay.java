package com.xmsy.network.pay.yinrongpay.pay;


import javax.annotation.Resource;

import com.xmsy.network.pay.yinrongpay.config.YinRongPayConfig;
import com.xmsy.network.pay.yinrongpay.def.Config;
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
 * .银蓉支付
 *
 * @author ahui
 * @date 2018年11月6日
 * @version 1.0
 */
@Slf4j
@Component
public class YinRongPay extends PayServiceBase {

	private final static String title = "零用品";

	@Resource
	private YinRongPayConfig yinrongpayConfig;

	public YinRongPay() {
		super.getPayServiceMap().put(getName(), this);
	}

	@Override
	protected GlobalResult<ResultData> doPay(PayParam param) {
		log.info("[yinrongpay] PayParam {}", param);//PayParam(orderNo=13348764231920kkcudb, amount=10, goods=null, goodsPrice=null, goodsNum=null, orderIp=10.0.0.160, returnUrl=null, orderNotifyUrl=null, payChannel=alipay, platform=H5)
		param.setOrderNotifyUrl(yinrongpayConfig.getCallbackUrl());
		param.setReturnUrl(yinrongpayConfig.getCallbackUrl());

		log.info("[yinrongpay] notify {}",yinrongpayConfig.getCallbackUrl());
		log.info("[yinrongpay] uid {}",yinrongpayConfig);

		//加密字符串
		StringBuffer sb = new StringBuffer();//用于使用md5生成sign
		sb.append("code="+Config.APPID); //商户号
		sb.append("&goodsClauses="+title); //商品名称
		sb.append("&notifyUrl="+param.getReturnUrl());
		sb.append("&outOrderNo="+ param.getOrderNo());//外部订单号
		sb.append("&payCode="+Config.PAY_CHANNEL_H5.get(param.getPayChannel()));//支付类型
		sb.append("&tradeAmount="+param.getAmount());//金额
		sb.append("&key="+Config.APPSECRET);//商户秘钥
		String sign = Md5Util.md5(sb.toString()).toUpperCase();// 加密字符串
		log.info("[yinrongpay] sign {}",sb.toString());

		JSONObject requestParam = new JSONObject();//封装要请求支付的参数
		requestParam.put("code",Config.APPID);
		requestParam.put("goodsClauses", title);
		requestParam.put("notifyUrl",param.getReturnUrl());
		requestParam.put("outOrderNo", param.getOrderNo());
		requestParam.put("payCode", Config.PAY_CHANNEL_H5.get(param.getPayChannel()));
		requestParam.put("sign", sign);
		requestParam.put("tradeAmount", param.getAmount());

		log.info("[yinrongpay] param= {} reqparam={}", requestParam);
		//判断微信支付，支付宝支付，其他支付
		String pay_Url = Config.ORDER_URL;

		log.info("[yinrongpay] 支付地址 {}   ",pay_Url);

		//POST请求
		String result = HttpUtil.post(pay_Url, requestParam);//返回的是html元素

		log.info("[yinrongpay] reqparam {}  result {} ",requestParam, result);
		JSONObject json = JSONObject.parseObject(result);
		System.out.println("转换json:"+json.toJSONString());


		if(json.getString("payState").equals("1")){
		}else{
			System.out.println("下单失败！");
			System.out.print("返回响应码:"+json.getString("payState")+",");
			System.out.print("描述:"+json.getString("msg"));
			return ResultUtils.getErrorResult(json.getIntValue("payState"), json.getString("msg"), null);
		}

		System.out.println("下单成功！");
		// 获取到key为shoppingCartItemList的值
		String payUrl = json.getString("url");

		ResultData resultData = new ResultData(payUrl, param.getOrderNo(), "");
		log.info("[yinrongpay]-> 支付返回结果： {} resultData {}", payUrl, resultData);
		return ResultUtils.getSuccessObject(resultData);
	}

	public static void main(String[] args) {

	}


	@Override
	protected void init(SysConfigMessage commonConfig,String callbackUrl) {
		if (null == commonConfig) {
			return;
		}
		if (!Config.name.equals(commonConfig.getValue())) {
			log.info("支付公司 {} 配置初始化失败 commonConfig {}", getName(), commonConfig);
		}
		PayConfigUtils.setPayConfig(commonConfig, yinrongpayConfig,callbackUrl);
		Config.APPID = StringUtil.replaceBlank(yinrongpayConfig.getUid());
		Config.APPSECRET = yinrongpayConfig.getSecret();
		Config.ORDER_URL = yinrongpayConfig.getOrderUrl();
		if (null != yinrongpayConfig.getProduct()) {
			Config.PAY_CHANNEL_H5 = yinrongpayConfig.getProduct().get(PayPlatform.H5);
			Config.PAY_CHANNEL_PC = yinrongpayConfig.getProduct().get(PayPlatform.PC);
		}
		log.info("支付公司 {} 配置初始化 config {}", getName(), yinrongpayConfig);
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

	@Override
	protected String getName() {
		// TODO Auto-generated method stub
		return Config.name;
	}

}
