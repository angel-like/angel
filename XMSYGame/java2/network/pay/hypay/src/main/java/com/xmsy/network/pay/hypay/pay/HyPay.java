package com.xmsy.network.pay.hypay.pay;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import com.xmsy.common.bean.message.SysConfigMessage;
import com.xmsy.common.bean.payment.ResultData;
import com.xmsy.common.define.result.GlobalResult;
import com.xmsy.common.define.result.ResultUtils;
import com.xmsy.network.pay.hypay.config.HyPayConfig;
import com.xmsy.network.pay.hypay.def.Config;
import com.xmsy.network.pay.hypay.utils.ResultUtil;
import com.xmsy.network.pay.paybase.define.PayPlatform;
import com.xmsy.network.pay.paybase.param.PayParam;
import com.xmsy.network.pay.paybase.pay.impl.PayServiceBase;
import com.xmsy.network.pay.paybase.utils.Md5Util;
import com.xmsy.network.pay.paybase.utils.PayConfigUtils;
import com.xmsy.network.pay.paybase.utils.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * .海洋支付
 *
 * @author adai
 * @version 1.0
 * @date 2018年11月6日
 */
@Slf4j
@Component
public class HyPay extends PayServiceBase {

    private final static String title = "零用品";

    @Resource
    private HyPayConfig hyPayConfig;

    public HyPay() {
        Map<String, PayServiceBase> baseMap = super.getPayServiceMap();
        super.getPayServiceMap().put(getName(), this);
    }

    @Override
    protected GlobalResult<ResultData> doPay(PayParam param) {
        log.info("[HaiYangPay] PayParam {}", param);
        param.setOrderNotifyUrl(hyPayConfig.getCallbackUrl());
       /* param.setReturnUrl(hyPayConfig.getCallbackUrl());*/
        TreeMap<String, Object> map = getOrderContentMap(param);
        //加密源字符串
        String signDataStr = getSignDataStr(map, Config.APPSECRET);// 拼装签名串
        log.info("[hypay] signDataStr {} ", signDataStr);
        String sign = Md5Util.md5(signDataStr).toUpperCase(); // 加密字符串
        log.info("[hypay] sign {} ", sign);
        JSONObject requestParam = new JSONObject();//封装要请求支付的参数
        requestParam.put("merchant", Config.APPID);
        requestParam.put("money", new BigDecimal(param.getAmount()).setScale(2));
        requestParam.put("tradeId", param.getOrderNo());
        if (PayPlatform.PC == param.getPlatform()) {
            requestParam.put("payWay", Config.PAY_CHANNEL_PC.get(param.getPayChannel()));
        } else {
            requestParam.put("payWay", Config.PAY_CHANNEL_H5.get(param.getPayChannel()));
        }
        requestParam.put("callback", param.getOrderNotifyUrl());
        /*requestParam.put("return_url", param.getOrderNotifyUrl());*/
        requestParam.put("sign", sign);
        log.info("[hypay] param= {}", requestParam);
        String result = HttpUtil.post(Config.ORDER_URL, requestParam);//返回的是html元素
        log.info("[hypay] reqparam {}  result {} ", requestParam, result);
        // 解析返回结果，并对返回结果进行key排序
        /*JSONObject jsonResult = */
        JSONObject jsonResult = null;
        try {
            jsonResult = JSON.parseObject(result, Feature.OrderedField);
        } catch (Exception e) {
            log.error("[hypay] 请求支付失败  result {}", result);
            return ResultUtils.getErrorResult("下单失败");
        }
        if (jsonResult == null) {
            log.error("[hypay] 请求支付失败  result {}", result);
            return ResultUtils.getErrorResult("下单失败");
        }
        if (!ResultUtil.success(jsonResult.getString("respSts"))) {
            log.error("[hypay] 请求支付失败  result {}", result);
            String msg = jsonResult.getString("msg");
            msg = msg == null ? "下单失败" : msg;
            return ResultUtils.getErrorResult(msg);
        }
        // 验签
        String qrUrl = (String) jsonResult.remove("qrUrl");
        Map<String, Object> resultMap = jsonResult.getInnerMap();
        resultMap.remove("respSts");
        TreeMap<String, Object> map1 = new TreeMap<>();
        map1.put("merchant", resultMap.get("merchant"));
        // 金额，单位元
        map1.put("money", resultMap.get("money"));
        // 订单号
        map1.put("tradeId", resultMap.get("tradeId"));
        // 支付产品号，银行类型
        map1.put("payWay", resultMap.get("payWay"));
        // 回调地址
        map1.put("callback", hyPayConfig.getCallbackUrl());
        /*map1.put("return_url", hyPayConfig.getCallbackUrl());*/
        String resultSignDataStr = getSignDataStr(map1, Config.APPSECRET);// 拼装签名串
        String targetSign = Md5Util.md5(resultSignDataStr).toUpperCase();
        log.info("[hypay] sign  {}",sign);
        log.info("[hypay] targetSign  {}",targetSign);
        if (sign.equals(targetSign)) {
            log.info("[hypay] success  result");
        } else {
            log.error("[hypay] false  result {}", result);
            return ResultUtils.getErrorResult(jsonResult.getString("msg"));
        }
        String outTradeNo = jsonResult.getString("tradeId");
        ResultData resultData = new ResultData(qrUrl, param.getOrderNo(), outTradeNo);
        log.info("[hypay]->HttpUtil.post requestParam {} result {}", requestParam, result);
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

            params.append(md5Key);
            return params.toString();
        }
        return null;
    }

    public static void main(String[] args) {
        String orderNum = new SimpleDateFormat("yyyymmddHHmmss").format(new Date()); // 20位
        PayParam param = new PayParam().setAmount(20).setPayChannel("weixin").setOrderIp("127.0.0.1")
                .setPlatform(PayPlatform.H5).setOrderNo(orderNum)
                .setOrderNotifyUrl("http://zxyy-hsk.qicp.io:11312/V1.0/pay/haiYangPay/payCallback");
        System.out.println(new HyPay().doPay(param));
    }

    /**
     * 测试下单数据组装
     *
     * @param
     * @return 测试下单数据
     */
    private static TreeMap<String, Object> getOrderContentMap(PayParam param) {
        TreeMap<String, Object> map = new TreeMap<String, Object>();
        map.put("merchant", Config.APPID);
        // 金额，单位元
        map.put("money", new BigDecimal(param.getAmount()).setScale(2));
        // 订单号
        map.put("tradeId", param.getOrderNo());
        // 支付产品号，银行类型
        if (PayPlatform.PC == param.getPlatform()) {
            map.put("payWay", Config.PAY_CHANNEL_PC.get(param.getPayChannel()));
        }
        if (PayPlatform.H5 == param.getPlatform()) {
            map.put("payWay", Config.PAY_CHANNEL_H5.get(param.getPayChannel()));
        }
        // 回调地址
        map.put("callback", param.getOrderNotifyUrl());
        /*map.put("return_url", param.getOrderNotifyUrl());*/
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
        PayConfigUtils.setPayConfig(commonConfig, hyPayConfig, callbackUrl);
        Config.APPID = StringUtil.replaceBlank(hyPayConfig.getUid());
        Config.APPSECRET = hyPayConfig.getSecret();
        Config.ORDER_URL = hyPayConfig.getOrderUrl();
        if (null != hyPayConfig.getProduct()) {
            Config.PAY_CHANNEL_H5 = hyPayConfig.getProduct().get(PayPlatform.H5);
            Config.PAY_CHANNEL_PC = hyPayConfig.getProduct().get(PayPlatform.PC);
        }
        log.info("支付公司 {} 配置初始化 config {}", getName(), hyPayConfig);
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
