package com.xmsy.network.pay.wantongpay;

import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;

import com.alibaba.fastjson.JSONObject;
import com.xmsy.network.pay.paybase.utils.Md5Util;
import com.xmsy.network.pay.paybase.utils.SignUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.http.HttpUtil;

/**
 * Hello world!
 *
 */
public class App
{
    public static void main( String[] args )
    {
    	create ();
    }

    public static void create () {
    	Map<String, Object> data = new HashMap<String, Object>();
        data.put("merchant_code","1574910934");
        data.put("appno_no","Zgzj1574910934980");
        data.put("order_amount","100");
        data.put("order_time",DateUtil.format(new Date(), "YYYYMMDDHHmmss"));
        data.put("order_no","xx"+data.get("order_time"));
        data.put("product_name","日用品");
        data.put("product_code","soi1021");
        data.put("pay_type","weixin-h5");
        data.put("user_no","zx500000");
        data.put("merchant_ip","192.1.20.12");
        data.put("notify_url","http://www.jcokpay.com/jeecg-boot/api/testCallBack");
        data.put("return_url","http://www.jcokpay.com/jeecg-boot/api/testCallBack");

        String signParam =  SignUtil.getSign(data)+ "key=" + "BB5A8B3EF74A0547A8F5CDF5801FAF89";
        System.out.println("signParam:"+signParam);
		String sign = Md5Util.md5(signParam).toUpperCase();
		  System.out.println("sign:"+sign);

//		  {
//			    "transdata": urlencode($data, "utf8"),
//			    "sign": urlencode($sign, 'utf8')
//			    "signtype": "signtype"
//			}
		  JSONObject req=new JSONObject();
		  try {
			  req.put("transdata",URLEncoder.encode(JSONObject.toJSONString(data), "UTF-8"));
			  req.put("sign",URLEncoder.encode(sign, "UTF-8") );
		  } catch (Exception e) {
			  e.printStackTrace();
		  }
		  req.put("signtype","MD5" );
    	String result = HttpUtil.post("https://bbin-wt.com/WTPay", req.toJSONString());//返回的是html元素
		System.out.println("result:"+result);
    }
    public static void callback () {
    	JSONObject req = new JSONObject();
        JSONObject data = new JSONObject();
        data.put("status","1");
        data.put("orderId","od123456");
        data.put("outerOrderId","1568794834000abc002");
        data.put("submitAmount","100.000");
        System.out.println("dataEn:"+data);
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
        req.put("username","lf8013");
        System.out.println(req.toJSONString());
    	String result = HttpUtil.post("http://127.0.0.1:8084/v1/payment/btpay", req.toJSONString());//返回的是html元素
		System.out.println("result:"+result);
    }
}
