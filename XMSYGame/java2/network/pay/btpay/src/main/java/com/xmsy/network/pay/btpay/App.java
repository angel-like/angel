package com.xmsy.network.pay.btpay;

import java.util.Date;

import org.apache.commons.codec.digest.DigestUtils;

import com.alibaba.fastjson.JSONObject;
import com.xmsy.network.pay.btpay.utils.AES128Util;

import cn.hutool.http.HttpUtil;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	JSONObject req = new JSONObject();
        JSONObject data = new JSONObject();
        data.put("status","1");
        data.put("orderId","od123456");
        data.put("outerOrderId","1568794834000abc002");
        data.put("submitAmount","100.000");
        String dataEn = AES128Util.encryptBase64(data.toJSONString(), "17917de2de014cd4");
        System.out.println("dataEn:"+dataEn);
        StringBuilder sign = new StringBuilder();
        //userId+timestamp+data
        Long time = new Date().getTime();
        sign.append(data.getString("orderId")).append(data.getString("outerOrderId"))
        .append(data.getString("submitAmount")).append(time).append("17917de2de014cd4");
        System.out.println("sign:"+sign.toString());
        String sign_str=DigestUtils.md5Hex(sign.toString());
        System.out.println("sign_str:"+sign_str);
        req.put("sign", sign_str);
        req.put("timestamp",time);
        req.put("data",dataEn);
        req.put("username","lf8013");
        System.out.println(req.toJSONString());
    	String result = HttpUtil.post("http://127.0.0.1:8084/v1/payment/btpay", req.toJSONString());//返回的是html元素
		System.out.println("result:"+result);
    }
    
    public static void create () {
    	JSONObject req = new JSONObject();
        JSONObject data = new JSONObject();
        data.put("outerOrderId","1568794834000abc002");
        data.put("username","user_test");
        data.put("submitAmount","100");
        data.put("payType","ali_bank");
        data.put("callbackUrl","http://www.jcokpay.com/jeecg-boot/api/testCallBack");
        String dataEn = AES128Util.encryptBase64(data.toJSONString(), "0a0dd1db3913460c");
        System.out.println("dataEn:"+dataEn);
        StringBuilder sign = new StringBuilder();
        //userId+timestamp+data
        Long time = new Date().getTime();
        sign.append("user_test").append(time).append(dataEn).append("0a0dd1db3913460c");
        System.out.println("sign:"+sign.toString());
        String sign_str=DigestUtils.md5Hex(sign.toString());
        System.out.println("sign_str:"+sign_str);
        req.put("sign", sign_str);
        req.put("timestamp",time);
        req.put("data",dataEn);
        req.put("username","user_test");
        System.out.println(req.toJSONString());
    	String result = HttpUtil.post("http://www.btcode123.com/pay/api/create", req.toJSONString());//返回的是html元素
		System.out.println("result:"+result);
    }
}
