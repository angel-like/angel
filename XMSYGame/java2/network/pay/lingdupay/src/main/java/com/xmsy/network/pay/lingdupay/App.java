package com.xmsy.network.pay.lingdupay;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.xmsy.network.pay.lingdupay.utils.SHA1Utils;

import cn.hutool.http.HttpUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * Hello world!
 *
 */
@Slf4j
public class App 
{
    public static void main( String[] args )
    {
    	create ();
    }
    
    public static void create () {
    	JSONObject req = new JSONObject();
        JSONObject data = new JSONObject();
        data.put("amount","29700");
        data.put("charset","utf-8");
        data.put("merchant","50052");
        data.put("notify_url","http://127.0.0.1:8088/notify_url");
        data.put("order_no","cv"+System.currentTimeMillis()/1000);
        data.put("order_time",System.currentTimeMillis()/1000);
        data.put("pay_type","ZFBPDD");
        data.put("subject","ali_bank");
		log.info("[LingDuPay] data {}",data);
	    StringBuilder sign = new StringBuilder();
	    sign.append("amount=").append(data.get("amount")).append("&");
	    sign.append("charset=").append(data.get("charset")).append("&");
	    sign.append("merchant=").append(data.get("merchant")).append("&");
	    sign.append("notify_url=").append(data.get("notify_url")).append("&");
	    sign.append("order_no=").append(data.get("order_no")).append("&");
	    sign.append("order_time=").append(data.get("order_time")).append("&");
	    sign.append("pay_type=").append(data.get("pay_type")).append("&");
	    sign.append("subject=").append(data.get("subject")).append("&");
	    sign.append("key=").append("7A12611B9EFA407095F0BCE9198B04B2");
        //
        log.info("[LingDuPay] sign_str:{}",sign.toString());
        data.put("sign", SHA1Utils.getSha1(sign.toString(), data.getString("charset")).toUpperCase());
		log.info("[LingDuPay] sign:{}",data.getString("sign"));
//		String result = HttpUtil.post("http://52.128.241.218:80/api/addOrder", data.toJSONString());//返回的
		Map<String,Object> map =new HashMap<>();
		map.put("amount", data.get("amount"));
		map.put("charset", data.get("charset"));
		map.put("merchant", data.get("merchant"));
		map.put("notify_url", data.get("notify_url"));
		map.put("order_time", data.get("order_time"));
		map.put("order_no", data.get("order_no"));
		map.put("pay_type", data.get("pay_type"));
		map.put("subject", data.get("subject"));
		map.put("sign", data.get("sign"));
		String result = HttpUtil.post("http://52.128.241.218:80/api/addOrder", map);//返回的
		log.info("[LingDuPay] reqparam {}  result {} ",data, result);
		JSONObject jsonResult = JSONObject.parseObject(result);
		String payUrl =null;
		if(jsonResult== null || !"0000".equals(jsonResult.get("code").toString())){
			log.error("[LingDuPay] 请求支付失败  result {}", result);
        }
		JSONObject payResult = jsonResult.getJSONObject("result");
		payUrl=payResult.getString("qrCode");
        System.out.println(data.toJSONString());
		System.out.println("result:"+result);
    }
}
