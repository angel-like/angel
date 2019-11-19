package com.itheima.springboot;

import java.util.Map;

import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.itheima.springboot.param.RequestParam;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;

public class test {
	public static void main(String[] args) {
		RequestParam requestParam=new RequestParam();
		requestParam.setAppkey("f9e4166015dbe671170b6cce35865154");
		requestParam.setLong_url("https://www.baidu.com/");
		//调用的第三方方法
		String shortUrl= conversionLinks(requestParam);	
		System.out.println(shortUrl);	
	}

	public static String conversionLinks(RequestParam requestParam) {
		JSONObject requestJson = (JSONObject)JSON.toJSON(requestParam);
		Map<String, Object> requestMap = requestJson.getInnerMap();
		String requestUrl="http://www.mynb8.com/api2/sina";
		//调用的第三方方法
		HttpResponse result = HttpRequest.get(requestUrl).form(requestMap).execute();
		System.out.println("测试请求是否成功：状态："+result.getStatus()+"\n返回值："+result.body());
		try {
			//字符串转换JSON数组
			/*JSONArray jsonarry = JSON.parseArray("["++"]");
			JSONObject JSONObject = (JSONObject)jsonarry.get(0);*/
			JSONObject JSONObject=com.alibaba.fastjson.JSONObject.parseObject(result.body());
			String shortUrl = (String)JSONObject.get("short_url");
			return shortUrl;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
