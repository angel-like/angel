package com.xmsy.network.pay.xinhuifupay.pay;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import com.xmsy.common.bean.message.SysConfigMessage;
import com.xmsy.common.bean.payment.ResultData;
import com.xmsy.common.define.result.GlobalResult;
import com.xmsy.common.define.result.ResultUtils;
import com.xmsy.network.pay.xinhuifupay.config.XinHuiFuPayConfig;
import com.xmsy.network.pay.xinhuifupay.def.Config;
import com.xmsy.network.pay.xinhuifupay.utils.ResultUtil;
import com.xmsy.network.pay.paybase.define.PayPlatform;
import com.xmsy.network.pay.paybase.param.PayParam;
import com.xmsy.network.pay.paybase.pay.impl.PayServiceBase;
import com.xmsy.network.pay.paybase.utils.PayConfigUtils;
import com.xmsy.network.pay.paybase.utils.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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
public class XinHuiFuPay extends PayServiceBase {

    private final static String title = "零用品";

    @Resource
    private XinHuiFuPayConfig xinHuiFuPayConfig;

    public XinHuiFuPay() {
        Map<String, PayServiceBase> baseMap = super.getPayServiceMap();
        super.getPayServiceMap().put(getName(), this);
    }
    public static String md5(String str) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(str.getBytes());
            byte[] byteDigest = md.digest();
            int i;
            // 字符数组转换成字符串
            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < byteDigest.length; offset++) {
                i = byteDigest[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            // 32位加密
            return buf.toString();
            // 16位的加密
            // return buf.toString().substring(8, 24).toUpperCase();
        } catch (NoSuchAlgorithmException e) {
            log.error("[Md5Util]->md5 NoSuchAlgorithmException", e);
            return null;
        }
    }
    @Override
    protected GlobalResult<ResultData> doPay(PayParam param) {
        log.info("[XinHuiFuPay1] PayParam {}", param);
        param.setOrderNotifyUrl(xinHuiFuPayConfig.getCallbackUrl());
        TreeMap<String, Object> map = getOrderContentMap(param);
        //加密源字符串
        String signDataStr = getSignDataStr(map, Config.APPSECRET);// 拼装签名串
        log.info("[xinhuifupay3] signDataStr {} ", signDataStr);
        String sign = null;
        try {
            sign = md5(signDataStr);
        } catch (Exception e) {
            log.error("[xinhuifupay4] result to json fail  result {}", sign);
            return ResultUtils.getErrorResult("生成签名失败");
        }
        log.info("[xinhuifupay5] sign {} ", sign);
        JSONObject requestParam = new JSONObject();//封装要请求支付的参数
        requestParam.put("fxid", Config.APPID);
        requestParam.put("fxfee", new BigDecimal(param.getAmount()).setScale(2));
        requestParam.put("fxddh", param.getOrderNo());
        if (PayPlatform.PC == param.getPlatform()) {
            requestParam.put("fxpay", Config.PAY_CHANNEL_PC.get(param.getPayChannel()));
            xinHuiFuPayConfig.setType(Config.PAY_CHANNEL_PC.get(param.getPayChannel()));
        } else {
            requestParam.put("fxpay", Config.PAY_CHANNEL_H5.get(param.getPayChannel()));
            xinHuiFuPayConfig.setType(Config.PAY_CHANNEL_H5.get(param.getPayChannel()));
        }
        requestParam.put("fxbackurl", param.getOrderNotifyUrl());
        requestParam.put("fxnotifyurl", param.getOrderNotifyUrl());
        requestParam.put("fxdesc", title);//param.getOrderIp()
        requestParam.put("fxsign", sign);
        requestParam.put("fxip", param.getOrderIp());
        log.info("[xinhuifupay6] param= {}", requestParam);
        String result = HttpUtil.post(Config.ORDER_URL, requestParam);
        log.info("[xinhuifupay7] reqparam {}  result {} ", requestParam, result);
        // 解析返回结果，并对返回结果进行key排序
        JSONObject jsonResult = null;
        try {
            jsonResult = JSON.parseObject(result, Feature.OrderedField);
        } catch (Exception e) {
            log.error("[xinhuifupay8] result to json fail  result {}", result);
            return ResultUtils.getErrorResult("订单建立失败");
        }
        if (jsonResult == null) {
            log.error("[xinhuifupay9] result is null  result {}", result);
            return ResultUtils.getErrorResult("订单建立失败");
        }
        if (!ResultUtil.success(jsonResult.getString("status"))) {
            log.error("[xinhuifupay10] code is error  result {}", result);
            String msg = jsonResult.getString("error");
            msg = msg == null ? "订单建立失败" : msg;
            return ResultUtils.getErrorResult(msg);
        }

        String payUrl = jsonResult.getString("payurl");
        ResultData resultData = new ResultData(payUrl, param.getOrderNo(), null);
        log.info("[xinhuifupay11]->HttpUtil.post requestParam {} result {}", requestParam, result);
        return ResultUtils.getSuccessObject(resultData);
    }
    /**
     * 测试下单数据组装
     *
     * @param
     * @return 测试下单数据
     */
    private static TreeMap<String, Object> getOrderContentMap(PayParam param) {
        TreeMap<String, Object> map = new TreeMap<String, Object>();
        map.put("fxid", Config.APPID);
        map.put("fxnotifyurl", param.getOrderNotifyUrl());//param.getOrderIp()
        // 金额，单位元
        map.put("fxfee", new BigDecimal(param.getAmount()).setScale(2));
        // 订单号
        map.put("fxddh", param.getOrderNo());
        return map;
    }

    public static String getSignDataStr(Map<String, Object> reqDataMap, String md5Key) {
        String fxnotifyurl = (String)reqDataMap.get("fxnotifyurl");
        String fxid = (String)reqDataMap.get("fxid");
        BigDecimal fxfee = (BigDecimal)reqDataMap.get("fxfee");
        String fxddh = (String)reqDataMap.get("fxddh");
       String s = fxid+fxddh+fxfee+fxnotifyurl+md5Key;
       return s;
    }

    public static void main(String[] args) {
        String orderNum = new SimpleDateFormat("yyyymmddHHmmss").format(new Date()); // 20位
        PayParam param = new PayParam().setAmount(20).setPayChannel("weixin").setOrderIp("127.0.0.1")
                .setPlatform(PayPlatform.H5).setOrderNo(orderNum)
                .setOrderNotifyUrl("http://zxyy-hsk.qicp.io:11312/V1.0/pay/haiYangPay/payCallback");
        System.out.println(new XinHuiFuPay().doPay(param));
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
        PayConfigUtils.setPayConfig(commonConfig, xinHuiFuPayConfig, callbackUrl);
        Config.APPID = StringUtil.replaceBlank(xinHuiFuPayConfig.getUid());
        Config.APPSECRET = xinHuiFuPayConfig.getSecret();
        Config.PUBLIC_KEY = xinHuiFuPayConfig.getPublicKey();
        Config.PRIVATE_KEY = xinHuiFuPayConfig.getPrivateKey();
        Config.ORDER_URL = xinHuiFuPayConfig.getOrderUrl();
        if (null != xinHuiFuPayConfig.getProduct()) {
            Config.PAY_CHANNEL_H5 = xinHuiFuPayConfig.getProduct().get(PayPlatform.H5);
            Config.PAY_CHANNEL_PC = xinHuiFuPayConfig.getProduct().get(PayPlatform.PC);
        }
        log.info("支付公司 {} 配置初始化 config {}", getName(), xinHuiFuPayConfig);
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
