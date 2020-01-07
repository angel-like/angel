package com.xmsy.network.pay.zhaoxinpay.callback;

import com.alibaba.fastjson.JSONObject;
import com.xmsy.common.bean.message.CallbackMessage;
import com.xmsy.network.pay.zhaoxinpay.config.ZhaoXinPayConfig;
import com.xmsy.network.pay.zhaoxinpay.def.Config;
import com.xmsy.network.pay.zhaoxinpay.pay.ZhaoXinPay;
import com.xmsy.network.pay.paybase.callback.BaseCallbackHandler;
import com.xmsy.network.pay.paybase.define.CodeDef;
import com.xmsy.network.pay.paybase.result.CallbackResult;
import com.xmsy.network.pay.paybase.utils.Md5Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

/**
 * .回调处理
 *
 * @author aleng
 * @version 1.0
 * @date 2018年11月2日
 */
@Slf4j
@Component
public class ZhaoXinPayCallbackHandler extends BaseCallbackHandler {
    @Resource
    private ZhaoXinPayConfig zhaoXinPayConfig;

    public ZhaoXinPayCallbackHandler() {
        super.getCallbackMap().put(Config.name, this);
    }

    @Override
    protected boolean verfiy(Map<String, Object> parameters) {
        return true;
    }

    @Override
    protected CallbackResult getCallbackResult(HttpServletRequest request) {
        log.info("contentType {}", request.getContentType());
        int i = request.getContentType().indexOf("application/json");
        Map<String, Object> parameters = null;
        String parmstr = null;
        try {
            if (request.getContentType().indexOf("application/json") > -1) {
                parmstr = readAsString(request);
                log.info("[zhaoxinpay1->parmstr] data {}", parmstr);
                parameters = JSONObject.parseObject(parmstr);
                log.info("[json->parameters] data {}", parameters);

            } else {
                parameters = getRarameters(request);//里面有状态吗result
                log.info("[not json->parameters] data {}", parameters);
            }

            log.info("[zhaoxinpay2->parameters] data {}", parameters);
            if (parameters == null) {
                log.error("[zhaoxinpay3] parameters {}", parameters);
                return CallbackResult.err("回调参数为空");
            }
            CallbackMessage callbackParam = new CallbackMessage();
            String result = (String) parameters.get("status");
            log.info("[zhaoxinpay4->result-statuscode] data {}", result);
            /*Integer status =0;*/
            if (Config.CALLBACK_SUCCESS.equals(result)) {//支付状态码为1就是支付成功
                /*status=1;*/
                callbackParam.setStatus(CodeDef.SUCCESS);
            } else {
                log.error("[zhaoxinpay-pay-false] result {}", result);
                return CallbackResult.err("支付失败");
            }
            String sign = (String) parameters.get("sign");//平台自己产生
            log.info("[zhaoxinpay5-> sign] data {}", sign);
            //封装再次验签  加密前源字符串为： orderid={0}&result={1}&amount={2}&systemorderid={3}&completetime={4}&key={5}
            TreeMap<String, Object> map = new TreeMap<String, Object>();
         /*   log.info("[zhaoxinpay6-> Config.APPID] data {}", Config.APPID);*/
            map.put("userid", parameters.get("userid"));
            // 金额，单位元
            map.put("money", parameters.get("money"));
            map.put("status", parameters.get("status"));
            // 订单号
            map.put("innerorderid", parameters.get("innerorderid"));
            map.put("outorderid", parameters.get("outorderid"));
            // 支付产品号，银行类型
            /*map.put("type", zhaoXinPayConfig.getType());*/
            // 回调地址
            /*log.info("[zhaoxinpay7-> zxPayConfig.getCallbackUrl()] data {}", zhaoXinPayConfig.getCallbackUrl());*/
           /* map.put("notifyurl", zhaoXinPayConfig.getCallbackUrl());*/
            /*map.put("callback", "https://api.3456.im/v1/payment/hypay");*/
            /*	map.put("sign", Config.APPSECRET);*/
           /* log.info("[zhaoxinpay8->  Config.APPSECRET] data {}", Config.APPSECRET);*/
            log.info("[zhaoxinpay9->  map] data {}", map);
            String signDataStr = ZhaoXinPay.getSignDataStr(map, Config.APPSECRET);// 拼装签名串
            log.info("[zhaoxinpay10->  signDataStr] data {}", signDataStr);
            String targetSign = Md5Util.md5(signDataStr).toUpperCase(); // 加密字符串
            log.info("[zhaoxinpay11-> targetSign] data {}", targetSign);
            if (!sign.equals(targetSign)) {
                callbackParam.setStatus(CodeDef.FAIL);
                return CallbackResult.err("验签失败");
            }
            //设置回调参数
            callbackParam.setOrderNo((String) parameters.get("innerorderid"));
            /*callbackParam.setStatus(status);*/
            int amount = new BigDecimal(parameters.get("money").toString()).intValue();//变成.00格式
            callbackParam.setAmount(amount);
            callbackParam.setMerchantNo((String) parameters.get("outorderid"));//平台订单号
            callbackParam.setAppId((String) parameters.get("userid"));//平台订单号
            callbackParam.setSign(sign);
            log.info("[zhaoxinpay12] callbackParam {}", callbackParam);
            return CallbackResult.success(callbackParam);
        } catch (Exception e) {
            log.error("[zhaoxinpay13] parameters e{}", parameters, e);
            return CallbackResult.err("回调异常");
        }

    }

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
    protected void getResponse(boolean orderHandleResult, HttpServletResponse response) {
        if (orderHandleResult) {
            try {
                response.getOutputStream().write("ok".getBytes("UTF-8"));
            } catch (UnsupportedEncodingException e) {
                log.error("[hongniupay->getResponse] UnsupportedEncodingException ", e);
            } catch (IOException e) {
                log.error("[hongniupay->getResponse] IOException ", e);
            }
            return;
        } else {
            try {
                response.getOutputStream().write("fail".getBytes("UTF-8"));
            } catch (UnsupportedEncodingException e) {
                log.error("[hongniupay->getResponse] UnsupportedEncodingException ", e);
            } catch (IOException e) {
                log.error("[hongniupay->getResponse] IOException ", e);
            }
        }
        return;
    }
}
