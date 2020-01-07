package com.xmsy.network.pay.qiqipay.pay;

import cn.hutool.core.date.DateUtil;
import cn.hutool.http.HttpUtil;
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
import com.xmsy.network.pay.paybase.utils.SignUtil;
import com.xmsy.network.pay.qiqipay.config.QiqiPayConfig;
import com.xmsy.network.pay.qiqipay.def.Config;
import com.xmsy.network.pay.qiqipay.utils.QiqiPayResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.TreeMap;

/**
 * .xinghot支付逻辑
 *
 * @author aleng
 * @date 2018年11月6日
 * @version 1.0
 */
@Slf4j
@Component
public class QiqiPay extends PayServiceBase {

	@Resource
	private QiqiPayResultUtil qiqiPayResultUtil;

	@Resource
	private QiqiPayConfig qiqiPayConfig;

	public QiqiPay() {
		super.getPayServiceMap().put(getName(), this);
	}

	@Override
	protected GlobalResult<ResultData> doPay(PayParam param) {
		log.info("[QiqiPay] PayParam {}", param);
		TreeMap<String, String> map = getOrderContentMap(param);
		map.put("Ip", "192.168.0.1");
		String data = SignUtil.sortSign(map);
		String signParam = data + "&key=" + Config.APPSECRET;
		String sign = Md5Util.md5(signParam).toUpperCase();
		String requestParam = data + "&sign=" + sign ;
		log.info("[QiqiPay] requestParam {}", requestParam);

		String result =Config.ORDER_URL+"?"+requestParam;
//		String result = HttpUtil.post(Config.ORDER_URL, requestParam);
		log.info("[QiqiPay] result {}",result);

		ResultData resultData = new ResultData(result, param.getOrderNo(),param.getOrderNo());
		log.info("[QiqiPay]->HttpUtil.post requestParam {} result {} resultData {} ", requestParam, result, resultData);
		return ResultUtils.getSuccessObject(resultData);
	}

	/**
	 * 测试下单数据组装
	 *
	 * @param order_trano_in
	 *            商户订单号
	 * @return 测试下单数据
	 */
	private static TreeMap<String, String> getOrderContentMap(PayParam param) {
		TreeMap<String, String> map = new TreeMap<String, String>();
		map.put("UId", Config.APPID);
		map.put("Amount", String.valueOf(param.getAmount()));
		map.put("Sh_OrderNo", param.getOrderNo());
		map.put("Msg", param.getOrderNo());
		map.put("Type", Config.PAY_CHANNEL.get(param.getPayChannel()));
		return map;
	}

	/**
	 * 测试下单返回数据验签
	 *
	 * @param order_trano_in
	 *            商户订单号
	 * @return 测试下单数据
	 */
	private static TreeMap<String, String> getOrderContentMap(JSONObject param) {
		TreeMap<String, String> map = new TreeMap<String, String>();
		map.put("UId", Config.APPID);
		map.put("Amount", param.getString("Amount"));
		map.put("OrderNo", param.getString("OrderNo"));
		map.put("Sh_OrderNo", param.getString("Sh_OrderNo"));
		map.put("Qrcode", param.getString("Qrcode"));
		return map;
	}

	public static void main(String[] args) throws NoSuchAlgorithmException {
		// PayParam param = new PayParam();
		// param.setAmount(100);
		// param.setGoods("aaa");
		// param.setGoodsNum("1");
		// param.setGoodsPrice("11");
		// param.setOrderNo("asdfasdfasdfgg111111");
		// param.setOrderNotifyUrl("http://zxyy-hsk.qicp.io:11312/pay/QiqiPay/V1.0/payCallback");
		// param.setReturnUrl("http://zxyy-hsk.qicp.io:11312/pay/QiqiPay/V1.0/payCallback");
		String UId = "0cb18710-3a60-4517-9f7e-95e753178155";
		String Amount = "200";
		String merKey = "A7074647B072592F16E35B372277BD89";
		String Sh_OrderNo = DateUtil.format(new Date(), "yyyyMMddHHmmssSSS");
		String Type = "13";
//		5 代表微信H5
//		13 代表支付宝H5,
//		14 代表QQH5
//		15 代表银联快捷

		String Msg = "test";
		String body = "Amount=" + Amount;
		body += "&Ip=192.168.0.1";
		body += "&Msg=" + Msg;
		body += "&Sh_OrderNo=" + Sh_OrderNo;
		body += "&Type=" + Type;
		body += "&UId=" + UId;
		String signKey = body + "&key=" + merKey;
		System.out.println( "signKey:"+signKey);
		String sign = Md5Util.md5(signKey).toUpperCase();
		System.out.println( "sign:"+sign);
		String postData = body + "&sign=" + sign;
		System.out.println(postData);
		String result = HttpUtil.post("http://www.77-seven.com/pay/h5_pay.aspx", postData);
//		String result = HttpUtil.get("http://www.77-seven.com/pay/h5_pay.aspx?"+ postData);
		System.out.println("===============================" );
		System.out.println( "result:"+result);

		// TreeMap<String, String> map =new TreeMap<>();
		// map.put("Msg", Msg);
		// map.put("Sh_OrderNo", Sh_OrderNo);
		// map.put("Type", Type);
		// map.put("UId", UId);
		// map.put("Amount", Amount);
		// map.put("key", merKey);
		// String signKey=SignUtil.sortSign(map);
		// PayResult result = new QiqiPay().doPay(param);
		// System.out.println(result);
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
		// 配置初始化
		PayConfigUtils.setPayConfig(commonConfig, qiqiPayConfig,callbackUrl);
		Config.APPID = qiqiPayConfig.getUid();
		Config.APPSECRET = qiqiPayConfig.getSecret();
		Config.ORDER_URL = qiqiPayConfig.getOrderUrl();
		if (null != qiqiPayConfig.getProduct()) {
			Config.PAY_CHANNEL = qiqiPayConfig.getProduct().get(PayPlatform.H5);
		}
		log.info("支付公司 {} 配置初始化 config {}", getName(), qiqiPayConfig);
	}

	/**
	 * 校验支付渠道是否存在
	 */
	@Override
	public boolean payChannelVerify(String payChannel, PayPlatform payPlatform) {
		return Config.PAY_CHANNEL.get(payChannel) != null;
	}

}
