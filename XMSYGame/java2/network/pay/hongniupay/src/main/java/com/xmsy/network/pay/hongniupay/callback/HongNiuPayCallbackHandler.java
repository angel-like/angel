package com.xmsy.network.pay.hongniupay.callback;

import com.alibaba.fastjson.JSONObject;
import com.xmsy.common.bean.message.CallbackMessage;
import com.xmsy.network.pay.hongniupay.config.HongNiuPayConfig;
import com.xmsy.network.pay.hongniupay.def.Config;
import com.xmsy.network.pay.hongniupay.pay.HongNiuPay;
import com.xmsy.network.pay.hongniupay.utils.RSAUtils;
import com.xmsy.network.pay.paybase.callback.BaseCallbackHandler;
import com.xmsy.network.pay.paybase.define.CodeDef;
import com.xmsy.network.pay.paybase.result.CallbackResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Base64;
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
public class HongNiuPayCallbackHandler extends BaseCallbackHandler {
    @Resource
    private HongNiuPayConfig hongNiuPayConfig;

    public HongNiuPayCallbackHandler() {
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
                log.info("[hongniupay1->parmstr] data {}", parmstr);
                parameters = JSONObject.parseObject(parmstr);
                log.info("[json->parameters] data {}", parameters);

            } else {
                parameters = getRarameters(request);//里面有状态吗result
                log.info("[not json->parameters] data {}", parameters);
            }
            log.info("[hongniupay2->parameters] data {}", parameters);
            if (parameters == null) {
                log.error("[hongniupay3] parameters {}", parameters);
                return CallbackResult.err("回调参数为空");
            }
            log.info("[hongniupay2->parameters] data {}", parameters);
            log.info("[hongniupay2->parameters.size()] data {}", parameters.size());
            log.info("[hongniupay2->parameters.values()] data {}", parameters.entrySet());
            log.info("[hongniupay2->parameters.keySet()] data {}", parameters.keySet());
            log.info("[hongniupay2->parameters.values()] data {}", parameters.values());
            CallbackMessage callbackParam = new CallbackMessage();
            Integer result = (Integer) parameters.get("orderStatus");
            log.info("[hongniupay4->result-statuscode] data {}", result);
            /*Integer status =0;*/
            if (Config.CALLBACK_SUCCESS.equals(result.toString())) {//支付状态码为1就是支付成功
                /*status=1;*/
                callbackParam.setStatus(CodeDef.SUCCESS);
            } else {
                log.error("[hongniupay-pay-false] result {}", result);
                String msg = (String) parameters.get("message");
                msg = msg == null ? "支付失败" : msg;
                return CallbackResult.err(msg);
            }
            String sign = (String) parameters.get("signature");//平台自己产生
            log.info("[hongniupay5-> sign] data {}", sign);
            String actualPrice = (String) parameters.get("actualPrice");//string	是	#.00	扣除手续费后的实际金额（元）
            String dealTime = (String) parameters.get("dealTime");//string	是	yyyyMMddHHmmss	交易完成时间 (2018-07-17 07:19:26.333)参与签名格式 与 回调格式 不同
            String orderNo = (String) parameters.get("orderNo");//	string	是		订单编号
//integer	是	订单状态（请看 参考资料-订单状态代码表） 当未收到明确的 成功(1) or 失败(3) 时，皆列入待处理人工判定
            Integer orderStatus = (Integer) parameters.get("orderStatus");
            //是	yyyyMMddHHmmss	订单时间 (2018-07-17 07:19:25.910)
            //参与签名格式 与 回调格式 不同
            String orderTime = (String) parameters.get("orderTime");//
            //是	#.00	交易金额，以此值上分（元）
            String price = (String) parameters.get("price");//
            //是		Red Bull Pay 交易订单编号
            String tradeNo = (String) parameters.get("tradeNo");//
            Integer code = (Integer) parameters.get("code");//
            String message = (String) parameters.get("message");//

            //封装再次验签  加密前源字符串为：
            TreeMap<String, Object> map = new TreeMap<String, Object>();
            log.info("[hongniupay6-> Config.APPID] data {}", Config.APPID);
            // 金额，单位元
            String deTime = null, ordTime = null;
            try {
                deTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").parse(dealTime));
                ordTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").parse(orderTime));
            } catch (ParseException e) {
                log.error("[hongniupay7-time-format] 日期格式化错误");
                return CallbackResult.err("日期格式化错误");
            }
            map.put("actualPrice", actualPrice);
            map.put("orderTime", ordTime);
            map.put("dealTime", deTime);
            // 订单号
            map.put("orderNo", orderNo);
            // 支付产品号，银行类型
            map.put("orderStatus", orderStatus);
            map.put("price", price);
            map.put("tradeNo", tradeNo);
            log.info("[hongniupay8->  Config.APPSECRET] data {}", Config.APPSECRET);
            log.info("[hongniupay9->  map] data {}", map);
            String signDataStr = HongNiuPay.getSignDataStr(map, Config.APPSECRET);// 拼装签名串
            log.info("[hongniupay10->  signDataStr] data {}", signDataStr);

            String publicKey = Config.PUBLIC_KEY;
            log.info("[hongniupay11->  publicKey] data {}", publicKey);
            try {
                boolean isValid = RSAUtils.checkSignByPubkey( signDataStr,Base64.getDecoder().decode( URLDecoder.decode(sign, "UTF-8")),publicKey);
                log.info("[hongniupay12->ddd  isValid] data {}", isValid);
                if( isValid == false) {

                    callbackParam.setStatus(CodeDef.FAIL);
                    return CallbackResult.err("验签失败");
                }
            } catch (Exception e) {
                log.error("[hongniupay13] publicKey{} e{}", publicKey, e);
                return CallbackResult.err("回调验签异常");
            }


            //String targetSign = Md5Util.md5(signDataStr).toUpperCase(); // 加密字符串
           // log.info("[hongniupay14-> targetSign] data {}", targetSign);
           /* if (!sign.equals(targetSign)) {
                callbackParam.setStatus(CodeDef.FAIL);
                return CallbackResult.err("验签失败");
            }*/
            //设置回调参数
            callbackParam.setOrderNo((String) parameters.get("orderNo"));
            /*callbackParam.setStatus(status);*/
            int amount = new BigDecimal(parameters.get("price").toString()).intValue();//变成.00格式
            callbackParam.setAmount(amount);
            callbackParam.setMerchantNo((String) parameters.get("tradeNo"));//平台订单号
            callbackParam.setAppId(Config.APPID);//
            callbackParam.setSign(sign);
            log.info("[hongniupay15] callbackParam {}", callbackParam);
            return CallbackResult.success(callbackParam);
        } catch (Exception e) {
            log.error("[hongniupay16] parameters{} e{}", parameters, e);
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
                response.getOutputStream().write("OK".getBytes("UTF-8"));
            } catch (UnsupportedEncodingException e) {
                log.error("[hongniupay->getResponse] UnsupportedEncodingException ", e);
            } catch (IOException e) {
                log.error("[hongniupay->getResponse] IOException ", e);
            }
            return;
        }else{
            try {
                response.getOutputStream().write("FAIL".getBytes("UTF-8"));
            } catch (UnsupportedEncodingException e) {
                log.error("[hongniupay->getResponse] UnsupportedEncodingException ", e);
            } catch (IOException e) {
                log.error("[hongniupay->getResponse] IOException ", e);
            }
        }
        return;
    }
}
