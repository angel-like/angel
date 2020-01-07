package com.xmsy.network.pay.hongniupay.pay;

import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import com.xmsy.common.bean.message.SysConfigMessage;
import com.xmsy.common.bean.payment.ResultData;
import com.xmsy.common.define.result.GlobalResult;
import com.xmsy.common.define.result.ResultUtils;
import com.xmsy.network.pay.hongniupay.config.HongNiuPayConfig;
import com.xmsy.network.pay.hongniupay.def.Config;
import com.xmsy.network.pay.hongniupay.utils.RSAUtils;
import com.xmsy.network.pay.hongniupay.utils.ResultUtil;
import com.xmsy.network.pay.paybase.define.PayPlatform;
import com.xmsy.network.pay.paybase.param.PayParam;
import com.xmsy.network.pay.paybase.pay.impl.PayServiceBase;
import com.xmsy.network.pay.paybase.utils.PayConfigUtils;
import com.xmsy.network.pay.paybase.utils.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * .红牛3支付
 *
 * @author adai
 * @version 1.0
 * @date 2018年11月6日
 */
@Slf4j
@Component
public class HongNiuPay extends PayServiceBase {

    private final static String title = "零用品";

    @Resource
    private HongNiuPayConfig hongNiuPayConfig;

    public HongNiuPay() {
        Map<String, PayServiceBase> baseMap = super.getPayServiceMap();
        super.getPayServiceMap().put(getName(), this);
    }

    @Override
    protected GlobalResult<ResultData> doPay(PayParam param) {
        log.info("[HongNiuPay1] PayParam {}", param);

        param.setOrderNotifyUrl(hongNiuPayConfig.getCallbackUrl());
       /* hongNiuPayConfig.setOrderIp(param.getOrderIp());
        hongNiuPayConfig.setUsername(param.getUserName());*/
        TreeMap<String, Object> map = getOrderContentMap(param);
        //加密源字符串
        String signDataStr = getSignDataStr(map, Config.APPSECRET);// 拼装签名串
        log.info("[hongniupay3] signDataStr {} ", signDataStr);
        String privateKey = Config.PRIVATE_KEY;
        byte[] bytes = signDataStr.getBytes();
        String sign = null;
        try {
            sign = RSAUtils.sign(bytes, privateKey);
        } catch (Exception e) {
            log.error("[hongniupay4] result to json fail  result {}", sign);
            return ResultUtils.getErrorResult("生成签名失败");
        }
        log.info("[hongniupay5] sign {} ", sign);
        JSONObject requestParam = new JSONObject();//封装要请求支付的参数
        requestParam.put("brandNo", Config.APPID);
        requestParam.put("price", new BigDecimal(param.getAmount()).setScale(2));
        requestParam.put("orderNo", param.getOrderNo());
        if (PayPlatform.PC == param.getPlatform()) {
            requestParam.put("serviceType", Config.PAY_CHANNEL_PC.get(param.getPayChannel()));
            hongNiuPayConfig.setType(Config.PAY_CHANNEL_PC.get(param.getPayChannel()));
        } else {
            requestParam.put("serviceType", Config.PAY_CHANNEL_H5.get(param.getPayChannel()));
            hongNiuPayConfig.setType(Config.PAY_CHANNEL_H5.get(param.getPayChannel()));
        }
        /*  requestParam.put("frontUrl", param.getOrderNotifyUrl());*/
        requestParam.put("signType", "RSA-S");
        requestParam.put("userName", param.getUserName());
        requestParam.put("callbackUrl", param.getOrderNotifyUrl());
        requestParam.put("clientIP", param.getOrderIp());//param.getOrderIp()
        requestParam.put("signature", sign);
        log.info("[hongniupay6] param= {}", requestParam);
        String result = HttpRequest.post(Config.ORDER_URL).body(JSON.toJSONString(requestParam)).execute().body();
        //String result = HttpUtil.post(Config.ORDER_URL, requestParam);//返回的是html元素
        log.info("[hongniupay7] reqparam {}  result {} ", requestParam, result);
        // 解析返回结果，并对返回结果进行key排序
        /*JSONObject jsonResult = */
        JSONObject jsonResult = null;
        try {
            jsonResult = JSON.parseObject(result, Feature.OrderedField);
        } catch (Exception e) {
            log.error("[hongniupay8] result to json fail  result {}", result);
            return ResultUtils.getErrorResult("订单建立失败");
        }
        if (jsonResult == null) {
            log.error("[hongniupay9] result is null  result {}", result);
            return ResultUtils.getErrorResult("订单建立失败");
        }
        if (!ResultUtil.success(jsonResult.getString("isSuccess"))) {
            log.error("[hongniupay10] code is error  result {}", result);
            String msg = jsonResult.getString("message");
            msg = msg == null ? "订单建立失败" : msg;
            return ResultUtils.getErrorResult(msg);
        }
        /* String type = jsonResult.getString("type");*/

        String data = jsonResult.getString("data");
        JSONObject parseObject = JSONObject.parseObject(data);
        String payUrl = parseObject.getString("payUrl");
        String tradeNo = parseObject.getString("tradeNo");
        String qrCode = parseObject.getString("qrCode");//扫码内容,非”空”时,可将值转换成扫码图

        ResultData resultData = new ResultData(payUrl, param.getOrderNo(), tradeNo);
        log.info("[hongniupay11]->HttpUtil.post requestParam {} result {}", requestParam, result);
        return ResultUtils.getSuccessObject(resultData);
    }

    public static String getSignDataStr(Map<String, Object> reqDataMap, String md5Key) {
        if (reqDataMap != null && reqDataMap.size() > 0) {
            // 键值排序
            Object[] keys = reqDataMap.keySet().toArray();
            Arrays.sort(keys);
            // 拼装字符串
            StringBuffer params = new StringBuffer();
            for (Map.Entry<String, Object> map : reqDataMap.entrySet()) {
                if (null == map.getValue()) {
                    continue;
                }
                params.append(map.getKey());
                params.append("=");
                params.append(map.getValue().toString());
                params.append("&");
            }
            int i = params.lastIndexOf("&");
            if (i > 0) {
                params.deleteCharAt(i);
            }
          /*  params.append("&key=");
            params.append(md5Key);*/
            return params.toString();
        }
        return null;
    }

    public static void main(String[] args) {
        String orderNum = new SimpleDateFormat("yyyymmddHHmmss").format(new Date()); // 20位
        PayParam param = new PayParam().setAmount(20).setPayChannel("weixin").setOrderIp("127.0.0.1")
                .setPlatform(PayPlatform.H5).setOrderNo(orderNum)
                .setOrderNotifyUrl("http://zxyy-hsk.qicp.io:11312/V1.0/pay/haiYangPay/payCallback");
        System.out.println(new HongNiuPay().doPay(param));
    }

    /**
     * 测试下单数据组装
     *
     * @param
     * @return 测试下单数据
     */
    private static TreeMap<String, Object> getOrderContentMap(PayParam param) {
        TreeMap<String, Object> map = new TreeMap<String, Object>();
        map.put("brandNo", Config.APPID);
        map.put("clientIP", param.getOrderIp());//param.getOrderIp()
        map.put("userName", param.getUserName());
        // 金额，单位元
        map.put("price", new BigDecimal(param.getAmount()).setScale(2));
        // 订单号
        map.put("orderNo", param.getOrderNo());
        // 支付产品号，银行类型
        if (PayPlatform.PC == param.getPlatform()) {
            map.put("serviceType", Config.PAY_CHANNEL_PC.get(param.getPayChannel()));
        }
        if (PayPlatform.H5 == param.getPlatform()) {
            map.put("serviceType", Config.PAY_CHANNEL_H5.get(param.getPayChannel()));
        }


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
    protected void init(SysConfigMessage commonConfig, String callbackUrl) {
        if (null == commonConfig) {
            return;
        }
        if (!Config.name.equals(commonConfig.getValue())) {
            log.info("支付公司 {} 配置初始化失败 commonConfig {}", getName(), commonConfig);
        }
        PayConfigUtils.setPayConfig(commonConfig, hongNiuPayConfig, callbackUrl);
        Config.APPID = StringUtil.replaceBlank(hongNiuPayConfig.getUid());
        Config.APPSECRET = hongNiuPayConfig.getSecret();
        Config.PUBLIC_KEY = hongNiuPayConfig.getPublicKey();
        Config.PRIVATE_KEY = hongNiuPayConfig.getPrivateKey();
        Config.ORDER_URL = hongNiuPayConfig.getOrderUrl();
        if (null != hongNiuPayConfig.getProduct()) {
            Config.PAY_CHANNEL_H5 = hongNiuPayConfig.getProduct().get(PayPlatform.H5);
            Config.PAY_CHANNEL_PC = hongNiuPayConfig.getProduct().get(PayPlatform.PC);
        }
        log.info("支付公司 {} 配置初始化 config {}", getName(), hongNiuPayConfig);
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
