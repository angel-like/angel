package com.xmsy.network.pay.jinfapay.pay;

import java.text.SimpleDateFormat;
import java.util.*;

import javax.annotation.Resource;

import com.xmsy.network.pay.jinfapay.config.JinFaPayConfig;
import com.xmsy.network.pay.jinfapay.def.Config;
import com.xmsy.network.pay.jinfapay.utils.util;
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
 * .金发支付
 *
 * @author ahui
 * @date 2018年11月6日
 * @version 1.0
 */
@Slf4j
@Component
public class JinFaPay extends PayServiceBase {

	private final static String title = "零用品";

	@Resource
	private JinFaPayConfig jinFaPayConfig;

	public JinFaPay() {
		super.getPayServiceMap().put(getName(), this);
	}

	@Override
	protected GlobalResult<ResultData> doPay(PayParam param) {
		log.info("[JinFaPay] PayParam {}", param);//PayParam(orderNo=13348764231920kkcudb, amount=10, goods=null, goodsPrice=null, goodsNum=null, orderIp=10.0.0.160, returnUrl=null, orderNotifyUrl=null, payChannel=alipay, platform=H5)
		param.setOrderNotifyUrl(jinFaPayConfig.getCallbackUrl());
		param.setReturnUrl(jinFaPayConfig.getCallbackUrl());
		TreeMap<String, Object> map = getOrderContentMap(param);

		log.info("[JinFaPay] notify {}",jinFaPayConfig.getCallbackUrl());
		log.info("[JinFaPay] uid {}",jinFaPayConfig);

		//加密字符串
		StringBuffer sb = new StringBuffer();//用于使用md5生成sign
		sb.append("version="+1.0);
		sb.append("&mer_id="+ Config.APPID);//商户ID
		sb.append("&order_id="+map.get("orderid"));
		sb.append("&price="+map.get("price"));//金额
		sb.append("&notify_url="+map.get("notify_url"));
		sb.append("&pay_type="+map.get("pay_type"));//银行类型
		sb.append("&randomid="+map.get("randomid"));
		sb.append("&key="+Config.APPSECRET);
		//version=1.0&mer_id="+mer_id+"&order_id="+order_id+"&price="+price+"&notify_url="+notify_url+"&pay_type="+pay_type+"&randomid="+randomid+"&key="+apikey
		String sign = Md5Util.md5(sb.toString()).toLowerCase();// 加密字符串

		JSONObject requestParam = new JSONObject();//封装要请求支付的参数
		requestParam.put("version","1.0");
		requestParam.put("mer_id", Config.APPID);
		if (PayPlatform.PC == param.getPlatform()) {
			requestParam.put("pay_type", Config.PAY_CHANNEL_PC.get(param.getPayChannel()));
		} else {
			requestParam.put("pay_type", Config.PAY_CHANNEL_H5.get(param.getPayChannel()));
		}
		requestParam.put("price", map.get("price"));
		requestParam.put("order_id", param.getOrderNo());
		requestParam.put("notify_url", param.getOrderNotifyUrl());
		requestParam.put("randomid",map.get("randomid"));
		requestParam.put("sign", sign);
		log.info("[JinFaPay] param= {} reqparam={}", requestParam);
		//判断微信支付，支付宝支付，其他支付
		String pay_Url = "";
		if (requestParam.get("pay_type").equals("wx_wap")){
			pay_Url = jinFaPayConfig.getWxUrl();
		}else if (requestParam.get("pay_type").equals("alipay_wap")) {
			pay_Url = jinFaPayConfig.getAliUrl();
		}else{
			pay_Url = Config.ORDER_URL;
		}
		log.info("[JinFaPay] 支付地址 {}   ",pay_Url);

		String result = HttpUtil.post(pay_Url, requestParam);//返回的是html元素

		log.info("[JinFaPay] reqparam {}  result {} ",requestParam, result);
		JSONObject json = JSONObject.parseObject(result);
		System.out.println("转换json:"+json.toJSONString());
		JSONObject jsonData =null;

		if(json.getString("code").equals("00")){
			System.out.println("下单成功！");
			jsonData = JSONObject.parseObject(json.getString("data"));
			//验证签名
			String createReturnSign = util.paySign(jsonData.getString("pay_order"), jsonData.getString("order_id"), jsonData.getString("price"), jsonData.getString("pay_type"), jsonData.getString("qrcode_url"), Config.APPSECRET);
			if(createReturnSign.toLowerCase().equals(jsonData.getString("sign"))){
				System.out.println("----------->验签成功！");
				System.out.print("返回响应码:"+json.getString("code")+",");
				System.out.print("描述:"+(json.getString("message").equals("success")?"下单成功":"下单失败")+",");
				System.out.print("返回签名:"+jsonData.getString("sign")+",");
				System.out.print("平台订单号:"+jsonData.getString("pay_order")+",");
				System.out.print("商户订单号:"+jsonData.getString("order_id")+",");
				System.out.print("支付金额:"+jsonData.getString("price")+",");
				System.out.println("支付类型:"+jsonData.getString("pay_type"));
				System.out.print("支付链接:"+jsonData.getString("qrcode_url"));
			}else{
				System.out.println("----------->验签失败！");
				return ResultUtils.getErrorResult(jsonData.getString("message"));
			}
		}else{
			System.out.println("下单失败！");
			System.out.print("返回响应码:"+json.getString("code")+",");
			System.out.print("描述:"+util.unicodeToCn(json.getString("message")));
			return ResultUtils.getErrorResult(json.getIntValue("code"), util.unicodeToCn(json.getString("message")), null);
		}
		// 获取到key为shoppingCartItemList的值
		String payUrl = jsonData.getString("qrcode_url");

		ResultData resultData = new ResultData(payUrl, param.getOrderNo(), "");
		log.info("[JinFaPay]-> 支付返回结果： {} resultData {}", payUrl, resultData);
		return ResultUtils.getSuccessObject(resultData);
	}

	public static void main(String[] args) {
		String orderNum = new SimpleDateFormat("yyyymmddHHmmss").format(new Date()); // 20位
		PayParam param = new PayParam().setAmount(20).setPayChannel("weixin").setOrderIp("192.168.0.166")
				.setPlatform(PayPlatform.H5).setOrderNo(orderNum)
				.setOrderNotifyUrl("http://zxyy-hsk.qicp.io:11312/V1.0/pay/baifuPay/payCallback");
		System.out.println(new JinFaPay().doPay(param));
	}

	/**
	 * 测试下单数据组装
	 *
	 * @param
	 * @return 测试下单数据
	 */
	private static TreeMap<String, Object> getOrderContentMap(PayParam param) {
		TreeMap<String, Object> map = new TreeMap<String, Object>();
		map.put("merchantNo", Config.APPID);
		// 随机码
		map.put("randomNum", String.valueOf((new Random().nextInt(10000))));
		// 支付产品号，银行类型
		if (PayPlatform.PC == param.getPlatform()) {
			map.put("pay_type", Config.PAY_CHANNEL_PC.get(param.getPayChannel()));
		}
		if (PayPlatform.H5 == param.getPlatform()) {
			map.put("pay_type", Config.PAY_CHANNEL_H5.get(param.getPayChannel()));
		}
		// 订单号
		map.put("orderid", param.getOrderNo());
		// 金额，单位元
		map.put("price", param.getAmount()*100);//new BigDecimal(param.getAmount()).setScale(2)
		// 商品名称
		map.put("goodsName", title);
		// 回调地址
		map.put("notify_url", param.getOrderNotifyUrl());
		// 回显地址
		map.put("frontBackUrl", param.getOrderNotifyUrl());
		map.put("requestIP", param.getOrderIp());
		//请求时间
		map.put("request_time",new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
		String randomid = UUID.randomUUID().toString().substring(0, 8);
		map.put("randomid",randomid);
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
		log.info("支付公司1 {} 配置初始化1 config1 {}", getName(), jinFaPayConfig);
		PayConfigUtils.setPayConfig(commonConfig, jinFaPayConfig,callbackUrl);
		Config.APPID = StringUtil.replaceBlank(jinFaPayConfig.getUid());
		Config.APPSECRET = jinFaPayConfig.getSecret();
		Config.ORDER_URL = jinFaPayConfig.getOrderUrl();
		if (null != jinFaPayConfig.getProduct()) {
			Config.PAY_CHANNEL_H5 = jinFaPayConfig.getProduct().get(PayPlatform.H5);
			Config.PAY_CHANNEL_PC = jinFaPayConfig.getProduct().get(PayPlatform.PC);
		}
		log.info("支付公司 {} 配置初始化 config {}", getName(), jinFaPayConfig);
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
