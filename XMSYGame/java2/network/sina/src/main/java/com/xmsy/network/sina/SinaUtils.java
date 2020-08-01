package com.xmsy.network.sina;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;

/**
 * 长链接转短链接
 * @author axiong
 *
 */
public class SinaUtils {
	/**
	 * 长链接转短链接
	 * 
	 * @param requestUrl 新浪接口调用地址
	 * @param appkey	调用接口的秘钥
	 * @param longUrl	要转换的长链接
	 * @return
	 */
	public static String conversionLinks(String requestUrl, String appkey,String longUrl) {
		Map<String,Object> map=new HashMap<>();
		map.put("long_url", longUrl);
		map.put("appkey", appkey);
		HttpResponse result = HttpRequest.get(requestUrl).form(map).execute();
		try {
			//字符串转JSON对象
			JSONObject JSONObject = com.alibaba.fastjson.JSONObject.parseObject(result.body());
			String shortUrl = (String)JSONObject.get("short_url");
			return shortUrl;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static void main(String[] args) {
		String requestUrl="http://www.mynb8.com/api2/sina";
		String appkey="f9e4166015dbe671170b6cce35865154";
		String longUrl="http://218.19.252.140:8334/qqxt/template/download/各区主要指标排名导入模板.xlsx";
		String links = conversionLinks(requestUrl,appkey,longUrl);
		System.out.println(links);
	}
}
