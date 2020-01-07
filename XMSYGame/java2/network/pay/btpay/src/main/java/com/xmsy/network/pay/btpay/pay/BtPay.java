package com.xmsy.network.pay.btpay.pay;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.xmsy.common.bean.message.SysConfigMessage;
import com.xmsy.common.bean.payment.ResultData;
import com.xmsy.common.define.result.GlobalResult;
import com.xmsy.common.define.result.ResultUtils;
import com.xmsy.network.pay.btpay.config.BtPayConfig;
import com.xmsy.network.pay.btpay.def.Config;
import com.xmsy.network.pay.btpay.utils.AES128Util;
import com.xmsy.network.pay.paybase.define.PayPlatform;
import com.xmsy.network.pay.paybase.param.PayParam;
import com.xmsy.network.pay.paybase.pay.impl.PayServiceBase;
import com.xmsy.network.pay.paybase.utils.PayConfigUtils;
import com.xmsy.network.pay.paybase.utils.StringUtil;

import cn.hutool.http.HttpUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * .BT支付
 * 
 * @author aleng
 * @date 2018年11月6日
 * @version 1.0
 */
@Slf4j
@Component
public class BtPay extends PayServiceBase {


	@Resource
	private BtPayConfig btPayConfig;
	/**
	 * 构造函数 把别名和BtPay支付server放入PayServiceMap
	 */
	public BtPay() {
		super.getPayServiceMap().put(getName(), this);
	}

	@Override
	/**
	 * 重写支付请求接口
	 */
	protected GlobalResult<ResultData> doPay(PayParam param) {
       
		log.info("[BtPay] PayParam {}", param);//PayParam(orderNo=13348764231920kkcudb, amount=10, goods=null, goodsPrice=null, goodsNum=null, orderIp=10.0.0.160, returnUrl=null, orderNotifyUrl=null, payChannel=alipay, platform=H5)
		param.setOrderNotifyUrl(btPayConfig.getCallbackUrl());
		JSONObject data = getOrderContentMap(param);
		log.info("[BtPay] data {}",data);
		String dataEn = AES128Util.encryptBase64(data.toJSONString(), Config.APPSECRET);
		log.info("[BtPay] APPSECRET {}",Config.APPSECRET);
		log.info("[BtPay] dataEn {}",dataEn);
	    StringBuilder sign = new StringBuilder();
        //userId+timestamp+data
        Long time = new Date().getTime();
        sign.append(Config.APPID).append(time).append(dataEn).append(Config.APPSECRET);
        log.info("[BtPay] sign_str:{}",sign.toString());
        JSONObject requestParam = new JSONObject();
		requestParam.put("sign", DigestUtils.md5Hex(sign.toString()));
		log.info("[BtPay] sign:{}",requestParam.getString("sign"));
		requestParam.put("timestamp",time);
        requestParam.put("data",dataEn);
        requestParam.put("username",Config.APPID);
		String result = HttpUtil.post(Config.ORDER_URL, requestParam.toJSONString());//返回的是html元素
		log.info("[BtPay] reqparam {}  result {} ",requestParam, result);
		JSONObject jsonResult = JSONObject.parseObject(result);
		String payUrl =null;
		if(jsonResult== null || !"0".equals(jsonResult.get("code").toString())){
			log.error("[BtPay] 请求支付失败  result {}", result);
			return ResultUtils.getErrorResult(jsonResult.getString("msg"));
        }
        payUrl = jsonResult.get("url").toString();
		ResultData resultData = new ResultData(payUrl, param.getOrderNo(), "");
		log.info("[BtPay]-> 支付返回结果： {} resultData {}", payUrl, resultData);
		return ResultUtils.getSuccessObject(resultData);
	}

	public static void main(String[] args) {
		String orderNum = new SimpleDateFormat("yyyymmddHHmmss").format(new Date()); // 20位
		PayParam param = new PayParam().setAmount(20).setPayChannel("weixin").setOrderIp("192.168.0.166")
				.setPlatform(PayPlatform.H5).setOrderNo(orderNum)
				.setOrderNotifyUrl("http://zxyy-hsk.qicp.io:11312/V1.0/pay/baifuPay/payCallback");
		System.out.println(new BtPay().doPay(param));
	}

	/**
	 * 测试下单数据组装
	 * 
	 * @param order_trano_in
	 *            商户订单号
	 * @return 测试下单数据
	 */
	private static JSONObject getOrderContentMap(PayParam param) {
		JSONObject map = new JSONObject();
	     // 订单号
		map.put("outerOrderId", param.getOrderNo());
		map.put("username", Config.APPID);
		// 金额，单位元
		map.put("submitAmount", new BigDecimal(param.getAmount()));
		// 支付产品号，银行类型
		if (PayPlatform.PC == param.getPlatform()) {
			map.put("payType", Config.PAY_CHANNEL_PC.get(param.getPayChannel()));
		}
		if (PayPlatform.H5 == param.getPlatform()) {
			map.put("payType", Config.PAY_CHANNEL_H5.get(param.getPayChannel()));
		}
//		map.put("payType","ysf");
		
		
		 // 回调地址
		map.put("callbackUrl", param.getOrderNotifyUrl());
		
		
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
		PayConfigUtils.setPayConfig(commonConfig, btPayConfig,callbackUrl);
		Config.APPID = StringUtil.replaceBlank(btPayConfig.getUid());
		Config.APPSECRET = btPayConfig.getSecret();
		Config.ORDER_URL = btPayConfig.getOrderUrl();
		if (null != btPayConfig.getProduct()) {
			Config.PAY_CHANNEL_H5 = btPayConfig.getProduct().get(PayPlatform.H5);
			Config.PAY_CHANNEL_PC = btPayConfig.getProduct().get(PayPlatform.PC);
		}
		log.info("支付公司 {} 配置初始化 config {}", getName(), btPayConfig);
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
