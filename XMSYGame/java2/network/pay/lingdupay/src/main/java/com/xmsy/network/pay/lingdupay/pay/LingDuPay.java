package com.xmsy.network.pay.lingdupay.pay;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.xmsy.common.bean.message.SysConfigMessage;
import com.xmsy.common.bean.payment.ResultData;
import com.xmsy.common.define.result.GlobalResult;
import com.xmsy.common.define.result.ResultUtils;
import com.xmsy.network.pay.lingdupay.config.LingDuPayConfig;
import com.xmsy.network.pay.lingdupay.def.Config;
import com.xmsy.network.pay.lingdupay.utils.SHA1Utils;
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
public class LingDuPay extends PayServiceBase {


	@Resource
	private LingDuPayConfig lingDuPayConfig;
	/**
	 * 构造函数 把别名和LingDuPay支付server放入PayServiceMap
	 */
	public LingDuPay() {
		super.getPayServiceMap().put(getName(), this);
	}

	@Override
	/**
	 * 重写支付请求接口
	 */
	protected GlobalResult<ResultData> doPay(PayParam param) {
       
		log.info("[LingDuPay] PayParam {}", param);//PayParam(orderNo=13348764231920kkcudb, amount=10, goods=null, goodsPrice=null, goodsNum=null, orderIp=10.0.0.160, returnUrl=null, orderNotifyUrl=null, payChannel=alipay, platform=H5)
		param.setOrderNotifyUrl(lingDuPayConfig.getCallbackUrl());
		Map<String, Object> data = getOrderContentMap(param);
		log.info("[LingDuPay] data {}",data);
	    StringBuilder sign = new StringBuilder();
	    sign.append("amount=").append(data.get("amount")).append("&");
	    sign.append("charset=").append(data.get("charset")).append("&");
	    sign.append("merchant=").append(data.get("merchant")).append("&");
	    sign.append("notify_url=").append(data.get("notify_url")).append("&");
	    sign.append("order_no=").append(data.get("order_no")).append("&");
	    sign.append("order_time=").append(data.get("order_time")).append("&");
	    sign.append("pay_type=").append(data.get("pay_type")).append("&");
	    sign.append("subject=").append(data.get("subject")).append("&");
	    sign.append("key=").append(Config.APPSECRET);
        //
        log.info("[LingDuPay] sign_str:{}",sign.toString());
        data.put("sign", SHA1Utils.getSha1(sign.toString(), data.get("charset").toString()));
		log.info("[LingDuPay] sign:{}",data.get("sign"));
		String result = HttpUtil.post(Config.ORDER_URL, data);//返回的
		log.info("[LingDuPay] reqparam {}  result {} ",data, result);
		JSONObject jsonResult = JSONObject.parseObject(result);
		String payUrl =null;
		if(jsonResult== null || !"0000".equals(jsonResult.get("code").toString())
				|| !jsonResult.getBoolean("success")){
			log.error("[LingDuPay] 请求支付失败  result {}", result);
			return ResultUtils.getErrorResult("下单失败,请稍后重试！");
        }
		JSONObject payResult = jsonResult.getJSONObject("result");
		if(payResult == null) {
			return ResultUtils.getErrorResult("下单失败,请稍后重试！");
		}
		StringBuilder resultSign = new StringBuilder();
		if(payResult.getString("h5Url")!=null) {
			resultSign.append("h5Url=").append(payResult.getString("h5Url")).append("&");
		}
		resultSign.append("orderNo=").append(payResult.getString("orderNo")).append("&");
		if(payResult.getString("qrUrl")!=null) {
			resultSign.append("qrUrl=").append(payResult.getString("qrUrl")).append("&");
		}
		resultSign.append("key=").append(Config.APPSECRET);
		String tsign=SHA1Utils.getSha1(resultSign.toString(), data.get("charset").toString()).toUpperCase();
		if(!tsign.equals(payResult.getString("sign"))) {
			return ResultUtils.getErrorResult("下单失败,请稍后重试！");
		}
		if(payResult.getString("h5Url")!=null) {
			payUrl=payResult.getString("h5Url");
		}else if(payResult.getString("qrUrl")!=null) {
			payUrl=payResult.getString("qrUrl");
		}
		ResultData resultData = new ResultData(payUrl, param.getOrderNo(), "");
		log.info("[LingDuPay]-> 支付返回结果： {} resultData {}", payUrl, resultData);
		return ResultUtils.getSuccessObject(resultData);
	}

	public static void main(String[] args) {
		String orderNum = new SimpleDateFormat("yyyymmddHHmmss").format(new Date()); // 20位
		PayParam param = new PayParam().setAmount(20).setPayChannel("weixin").setOrderIp("192.168.0.166")
				.setPlatform(PayPlatform.H5).setOrderNo(orderNum)
				.setOrderNotifyUrl("http://zxyy-hsk.qicp.io:11312/V1.0/pay/baifuPay/payCallback");
		System.out.println(new LingDuPay().doPay(param));
	}

	/**
	 * 测试下单数据组装
	 * 
	 * @param order_trano_in
	 *            商户订单号
	 * @return 测试下单数据
	 */
	private static Map<String, Object> getOrderContentMap(PayParam param) {
		Map<String, Object> map = new HashMap<>();
	     // 订单号
		map.put("order_no", param.getOrderNo());
		map.put("merchant", Config.APPID);
		// 金额，单位元
		map.put("amount", (new BigDecimal(param.getAmount()).multiply(new BigDecimal(100))).longValue());
		// 支付产品号，银行类型
		if (PayPlatform.PC == param.getPlatform()) {
			map.put("pay_type", Config.PAY_CHANNEL_PC.get(param.getPayChannel()));
		}
		if (PayPlatform.H5 == param.getPlatform()) {
			map.put("pay_type", Config.PAY_CHANNEL_H5.get(param.getPayChannel()));
		}
		map.put("subject","生活用品");
		
		
		 // 回调地址
		map.put("notify_url", param.getOrderNotifyUrl());
		map.put("charset", "utf-8");
		map.put("order_time", System.currentTimeMillis()/1000);
		
		
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
		PayConfigUtils.setPayConfig(commonConfig, lingDuPayConfig,callbackUrl);
		Config.APPID = StringUtil.replaceBlank(lingDuPayConfig.getUid());
		Config.APPSECRET = lingDuPayConfig.getSecret();
		Config.ORDER_URL = lingDuPayConfig.getOrderUrl();
		if (null != lingDuPayConfig.getProduct()) {
			Config.PAY_CHANNEL_H5 = lingDuPayConfig.getProduct().get(PayPlatform.H5);
			Config.PAY_CHANNEL_PC = lingDuPayConfig.getProduct().get(PayPlatform.PC);
		}
		log.info("支付公司 {} 配置初始化 config {}", getName(), lingDuPayConfig);
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
