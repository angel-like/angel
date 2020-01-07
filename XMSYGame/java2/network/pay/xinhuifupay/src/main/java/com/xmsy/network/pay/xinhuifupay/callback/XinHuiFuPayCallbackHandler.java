package com.xmsy.network.pay.xinhuifupay.callback;

import com.alibaba.fastjson.JSONObject;
import com.xmsy.common.bean.message.CallbackMessage;
import com.xmsy.network.pay.xinhuifupay.def.Config;
import com.xmsy.network.pay.paybase.callback.BaseCallbackHandler;
import com.xmsy.network.pay.paybase.define.CodeDef;
import com.xmsy.network.pay.paybase.result.CallbackResult;
import com.xmsy.network.pay.xinhuifupay.pay.XinHuiFuPay;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.Map;

/**
 * .回调处理
 *
 * @author aleng
 * @version 1.0
 * @date 2018年11月2日
 */
@Slf4j
@Component
public class XinHuiFuPayCallbackHandler extends BaseCallbackHandler {


    public XinHuiFuPayCallbackHandler() {
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
                log.info("[xinhuifupay1->parmstr] data {}", parmstr);
                parameters = JSONObject.parseObject(parmstr);
                log.info("[json->parameters] data {}", parameters);

            } else {
                parameters = getRarameters(request);//里面有状态吗result
                log.info("[not json->parameters] data {}", parameters);
            }
            log.info("[xinhuifupay2->parameters] data {}", parameters);
            if (parameters == null) {
                log.error("[xinhuifupay3] parameters {}", parameters);
                return CallbackResult.err("回调参数为空");
            }
            CallbackMessage callbackParam = new CallbackMessage();
            String result = parameters.get("fxstatus").toString();
            log.info("[xinhuifupay4->result-fxstatus] data {}", result);
            if (Config.CALLBACK_SUCCESS.equals(result.toString())) {//支付状态码为1就是支付成功
                callbackParam.setStatus(CodeDef.SUCCESS);
            } else {
                log.error("[xinhuifupay-pay-false] result {}", result);
                String msg = (String) parameters.get("error");
                msg = msg == null ? "支付失败" : msg;
                return CallbackResult.err(msg);
            }
            String fxsign = (String) parameters.get("fxsign");//平台自己产生
            log.info("[xinhuifupay5-> fxsign] data {}", fxsign);
            String fxid = (String) parameters.get("fxid");//
            String fxddh = (String) parameters.get("fxddh");
            String fxorder = (String) parameters.get("fxorder");
            String fxdesc = (String) parameters.get("fxdesc");//
            String fxfee = (String) parameters.get("fxfee");
            String fxattch = (String) parameters.get("fxattch");//
            String fxstatus =  parameters.get("fxstatus").toString();
            String fxtime = (String) parameters.get("fxtime");//
            //封装再次验签  加密前源字符串为：
            String signDataStr =fxstatus+fxid+fxddh+fxfee+Config.APPSECRET;
            log.info("[xinhuifupay10->  signDataStr] data {}", signDataStr);
            String targetSign = XinHuiFuPay.md5(signDataStr); // 加密字符串
            log.info("[xinhuifupay14-> targetSign] data {}", targetSign);
            if (!fxsign.equals(targetSign)) {
                callbackParam.setStatus(CodeDef.FAIL);
                return CallbackResult.err("验签失败");
            }
            //设置回调参数
            callbackParam.setOrderNo(fxddh);
            int amount = new BigDecimal(fxfee).intValue();//变成.00格式
            callbackParam.setAmount(amount);
            callbackParam.setMerchantNo(fxorder);//平台订单号
            callbackParam.setAppId(fxid);//
            callbackParam.setSign(fxsign);
            log.info("[xinhuifupay15] callbackParam {}", callbackParam);
            return CallbackResult.success(callbackParam);
        } catch (Exception e) {
            log.error("[xinhuifupay16] parameters{} e{}", parameters, e);
            return CallbackResult.err("回调异常");
        }

    }

    @Override
    protected void getResponse(boolean orderHandleResult, HttpServletResponse response) {
        if (orderHandleResult) {
            try {
                response.getOutputStream().write("success".getBytes("UTF-8"));
            } catch (UnsupportedEncodingException e) {
                log.error("[xinhuifupay->getResponse] UnsupportedEncodingException ", e);
            } catch (IOException e) {
                log.error("[xinhuifupay->getResponse] IOException ", e);
            }
            return;
        }else{
            try {
                response.getOutputStream().write("fail".getBytes("UTF-8"));
            } catch (UnsupportedEncodingException e) {
                log.error("[xinhuifupay->getResponse] UnsupportedEncodingException ", e);
            } catch (IOException e) {
                log.error("[xinhuifupay->getResponse] IOException ", e);
            }
        }
        return;
    }
}
