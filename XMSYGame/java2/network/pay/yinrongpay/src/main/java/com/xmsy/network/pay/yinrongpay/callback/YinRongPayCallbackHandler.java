package com.xmsy.network.pay.yinrongpay.callback;

import com.alibaba.fastjson.JSONObject;
import com.xmsy.common.bean.message.CallbackMessage;
import com.xmsy.network.pay.paybase.callback.BaseCallbackHandler;
import com.xmsy.network.pay.paybase.define.CodeDef;
import com.xmsy.network.pay.paybase.result.CallbackResult;
import com.xmsy.network.pay.paybase.utils.Md5Util;
import com.xmsy.network.pay.yinrongpay.def.Config;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.Enumeration;
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
public class YinRongPayCallbackHandler extends BaseCallbackHandler {

    public YinRongPayCallbackHandler() {
        super.getCallbackMap().put(Config.name, this);
    }

    @Override
    protected boolean verfiy(Map<String, Object> parameters) {
        return true;
    }

    @Override
    protected CallbackResult getCallbackResult(HttpServletRequest request) {
        log.info("[yinrongPay->getCallbackResult] data {}", request);
        JSONObject parameters = null;
        try {
            StringBuilder sg = new StringBuilder();
            Enumeration<String> parameterNames = request.getParameterNames();
            while (parameterNames.hasMoreElements()) {
                sg.append(parameterNames.nextElement());
            }
            /*Object json = JSON.toJSON(sg);*/
            log.info("[hypay2->sg001] data {}", sg);
            if (sg.toString().endsWith("=")) {
                log.info("[hypay2->sg end with =] sg {}", sg);
                sg = sg.deleteCharAt(sg.lastIndexOf("="));
            }
            parameters = JSONObject.parseObject(sg.toString());
            log.info("[yinrongPay->getCallbackResult] data {}", parameters);

            CallbackMessage callbackParam = new CallbackMessage();
            String result = (String) parameters.get("payState");
            if (Config.CALLBACK_SUCCESS.equals(result)) {//支付状态码为1就是支付成功
                callbackParam.setStatus(CodeDef.SUCCESS);
            } else {
                return CallbackResult.err();
            }
            String sign = (String) parameters.get("sign");//平台自己产生
            //封装再次验签  加密前源字符串为：
            StringBuffer sb = new StringBuffer();
            sb.append("code=" + parameters.get("code"));
            sb.append("&dzAmount=" + parameters.get("dzAmount"));
            sb.append("&goodsClauses=" + parameters.get("goodsClauses"));
            sb.append("&msg=" + parameters.get("msg"));
            sb.append("&outOrderNo=" + parameters.get("outOrderNo"));
            sb.append("&payAmount=" + parameters.get("payAmount"));
            sb.append("&payState=" + parameters.get("payState"));
            sb.append("&tradeAmount=" + parameters.get("tradeAmount"));
            sb.append("&key=" + Config.APPSECRET);
            String targetSign = Md5Util.md5(sb.toString()).toUpperCase();// 加密字符串
            if (!sign.equals(targetSign)) {
                callbackParam.setStatus(CodeDef.FAIL);
                return CallbackResult.err("验签失败");
            }

            //设置回调参数
            callbackParam.setOrderNo((String) parameters.get("outOrderNo"));
            callbackParam.setStatus(Integer.valueOf(result));
            int amount = new BigDecimal(parameters.get("tradeAmount").toString()).intValue();//变成.00格式
            callbackParam.setAmount(amount);
            callbackParam.setMerchantNo((String) parameters.get("outOrderNo"));//平台订单号
            callbackParam.setSign(sign);
            return CallbackResult.success(callbackParam);
        } catch (Exception e) {
            log.error("[htpay] parameters {}", parameters, e);
            return CallbackResult.err();
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
                response.getOutputStream().write("SUCCESS".getBytes("UTF-8"));
            } catch (UnsupportedEncodingException e) {
                log.error("[baifupay->getResponse] UnsupportedEncodingException ", e);
            } catch (IOException e) {
                log.error("[baifupay->getResponse] IOException ", e);
            }
            return;
        }
        return;
    }
}
