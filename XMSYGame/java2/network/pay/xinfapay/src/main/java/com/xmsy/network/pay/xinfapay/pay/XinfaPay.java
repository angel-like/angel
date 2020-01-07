package com.xmsy.network.pay.xinfapay.pay;

import java.net.URLEncoder;
import java.util.Map;
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
import com.xmsy.network.pay.paybase.define.PayPlatform;
import com.xmsy.network.pay.paybase.param.PayParam;
import com.xmsy.network.pay.paybase.pay.impl.PayServiceBase;
import com.xmsy.network.pay.paybase.utils.PayConfigUtils;
import com.xmsy.network.pay.xinfapay.config.XinfaPayConfig;
import com.xmsy.network.pay.xinfapay.def.Config;
import com.xmsy.network.pay.xinfapay.utils.ResultUtil;
import com.xmsy.network.pay.xinfapay.utils.ToolKit;

import lombok.extern.slf4j.Slf4j;
import sun.misc.BASE64Encoder;

/**
 * .鑫发支付
 *
 * @author aleng
 * @date 2018年11月6日
 * @version 1.0
 */
@Slf4j
@Component
@SuppressWarnings("restriction")
public class XinfaPay extends PayServiceBase {

	private final static String title = "零用品";
	private final static String encodeError = "支付异常请重试";

	@Resource
	private XinfaPayConfig xinfaPayConfig;

	public XinfaPay() {
		super.getPayServiceMap().put(getName(), this);
	}

	@Override
	protected GlobalResult<ResultData> doPay(PayParam param) {
		Map<String, String> metaSignMap = new TreeMap<String, String>();
		metaSignMap.put("orderNo", param.getOrderNo());
		metaSignMap.put("version", "V3.3.0.0");
		metaSignMap.put("charsetCode", ToolKit.CHARSET);//
		metaSignMap.put("randomNum", ToolKit.randomStr(4));// 4位随机数
		metaSignMap.put("merchNo", Config.APPID);
		if (PayPlatform.PC == param.getPlatform()) {
			metaSignMap.put("payType", Config.PAY_CHANNEL_PC.get(param.getPayChannel()));
		}
		if (PayPlatform.H5 == param.getPlatform()) {
			metaSignMap.put("payType", Config.PAY_CHANNEL_H5.get(param.getPayChannel()));
		}
		metaSignMap.put("amount", String.valueOf(param.getAmount() * 100));//
		metaSignMap.put("goodsName", title);//
		metaSignMap.put("notifyUrl", xinfaPayConfig.getCallbackUrl());// 回调地址
		metaSignMap.put("notifyViewUrl", xinfaPayConfig.getCallbackUrl());// 回显地址

		String metaSignJsonStr = JSON.toJSONString(metaSignMap);
		String sign = ToolKit.MD5(metaSignJsonStr + Config.APPSECRET, ToolKit.CHARSET);// 32位
		log.info("sign:{} metaSignMap :{}", sign, metaSignMap); // 英文字母大写
		metaSignMap.put("sign", sign);
		try {
			byte[] dataStr = ToolKit.encryptByPublicKey(JSON.toJSONString(metaSignMap).getBytes(ToolKit.CHARSET),
					Config.PUBLIC_KEY);
			String paramStr = new BASE64Encoder().encode(dataStr);
			String reqParam = "data=" + URLEncoder.encode(paramStr, ToolKit.CHARSET) + "&merchNo="
					+ metaSignMap.get("merchNo");
			String resultJsonStr = ToolKit.request(Config.ORDER_URL, reqParam);
			JSONObject jsonResult = JSONObject.parseObject(resultJsonStr, Feature.OrderedField);
			if (!ResultUtil.success(jsonResult.getString("stateCode"))) {
				log.error("[XinfaPay] 请求支付失败  result {}", resultJsonStr);
				return ResultUtils.getErrorResult(jsonResult.getString("msg"));
			}
			String resultSign = jsonResult.getString("sign");
			Map<String, Object> resultMap = jsonResult.getInnerMap();
			resultMap.remove("sign");
			String resultSignDataStr = ToolKit.mapToJsonObject(resultMap);
			String targetString = ToolKit.MD5(resultSignDataStr + Config.APPSECRET, ToolKit.CHARSET);
			if (targetString.equals(resultSign)) {
				log.info("验签成功");
			} else {
				log.error("[XinfaPay] 请求支付返回验签失败  result {}", resultJsonStr);
				return ResultUtils.getErrorResult(jsonResult.getString("msg"));
			}
			String payUrl = jsonResult.getString("qrcodeUrl");
			String outTradeNo = jsonResult.getString("orderNo");
			ResultData resultData = new ResultData(payUrl, param.getOrderNo(), outTradeNo);
			log.info("[XinfaPay]->HttpUtil.post requestParam {} result {}", reqParam, resultJsonStr);
			return ResultUtils.getSuccessObject(resultData);
		} catch (Exception e) {
			log.error("支付失败 Exception", e);
			return ResultUtils.getErrorResult(encodeError);
		}
	}

	public static void main(String[] args) {
		PayParam param = new PayParam().setAmount(20).setPayChannel("weixin").setOrderIp("192.168.0.166")
				.setPlatform(PayPlatform.H5).setOrderNo(System.currentTimeMillis() + "")
				.setOrderNotifyUrl("http://zxyy-hsk.qicp.io:11312/V1.0/pay/xinfapay/payCallback");
		log.info("[XinfaPay] PayParam {}", param);
		System.out.println(new XinfaPay().doPay(param));
		// String sign="87F6BB268F5CE2D9026A83CC5DD2196D";
		// Map<String, Object> map = Maps.newConcurrentMap();
		// map.put("msg", "提交成功");
		// map.put("orderNo", "20190430154222953zFyu");
		// map.put("merchNo", "XF201904262981");
		// map.put("stateCode", "00");
		// map.put("qrcodeUrl",
		// "http://47.107.125.165/wx/pay/20190430154231730ha5wqkXF");
		// String resultSignDataStr = ToolKit.mapToJsonObject(map);
		// String targetString = ToolKit.MD5(resultSignDataStr + Config.APPSECRET,
		// ToolKit.CHARSET);
		// System.out.println(targetString);
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
		PayConfigUtils.setPayConfig(commonConfig, xinfaPayConfig,callbackUrl);
		Config.APPID = xinfaPayConfig.getUid();
		Config.APPSECRET = xinfaPayConfig.getSecret();
		Config.PUBLIC_KEY = xinfaPayConfig.getPublicKey();
		Config.PRIVATE_KEY = xinfaPayConfig.getPrivateKey();
		Config.ORDER_URL = xinfaPayConfig.getOrderUrl();
		if (null != xinfaPayConfig.getProduct()) {
			Config.PAY_CHANNEL_H5 = xinfaPayConfig.getProduct().get(PayPlatform.H5);
			Config.PAY_CHANNEL_PC = xinfaPayConfig.getProduct().get(PayPlatform.PC);
		}
		Config.PRIVATE_KEY = xinfaPayConfig.getPrivateKey();
		Config.PUBLIC_KEY = xinfaPayConfig.getPublicKey();
		log.info("支付公司 {} 配置初始化 config {}", getName(), xinfaPayConfig);
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
