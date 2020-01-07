package com.xmsy.server.zxyy.webhome.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import cn.hutool.http.HttpRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class IPQueryUtil {
	
	private static final String URL="http://api.ip138.com/query/";
	private static final String TOKEN="ee7f8124de15f4b7e46d94774d118544";
	
	public static JSONObject get(String ip) {
		String url=URL+"?ip="+ip+"&datatype=jsonp";
		JSONObject resultObject=null;
		try {
			log.debug("请求IP解析url: {}",url);
			String  result = HttpRequest.get(url).header("token",TOKEN).timeout(3000).execute().body();
			log.debug("请求IP解析返回结果: {}",result);
			resultObject=JSON.parseObject(result);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("请求IP解析出错error {}",e);
		}
//		{"ret":"ok","data":["中国","香港","","","",""],"ip":"85.208.56.226"}
		return resultObject;
	} 
	public static JSONObject getStatus() {
		String url="http://api.ip138.com/status/?token="+TOKEN;
		JSONObject resultObject=null;
		try {
			log.debug("请求IP解析接口状态url: {}",url);
			String  result = HttpRequest.get(url).timeout(3000).execute().body();
			log.debug("请求IP解析接口状态返回结果: {}",result);
			resultObject=JSON.parseObject(result);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("请求IP解析出错error {}",e);
		}
//		{"ret":"ok","status":"正常","data":{"reqs":0,"hour":0,"package":983}}
		return resultObject;
	} 
	public static String[] queryIp(String ip) {
		if(ip==null || "".equals(ip.trim())) {
			return null;
		}
		String returnData[]=null;
		JSONObject resultObject=get(ip.trim());
		if(resultObject!=null && resultObject.get("data")!=null) {
			JSONArray data = resultObject.getJSONArray("data");
			if(data != null) {
				returnData=new String[data.size()];
				for(int i=0;i<data.size();i++) {
					returnData[i]=data.getString(i);
				}
			}
		}
		return returnData;
		
	}
	public static JSONObject queryStatus() {
		JSONObject returnData=null;
		JSONObject resultObject=getStatus();
		if(resultObject!=null && resultObject.get("data")!=null) {
			returnData = resultObject.getJSONObject("data");
			returnData.put("status", resultObject.get("status"));
		}
		return returnData;
		
	}
	
	public static void main(String[] args) {
//		System.out.println(IPQueryUtil.get("85.208.56.226"));
//		String data[]=IPQueryUtil.queryIp("85.208.56.226");
//		System.out.println(data[0]);
//		System.out.println(data[1]);
//		System.out.println(data[2]);
		System.out.println(IPQueryUtil.queryStatus());
	}

}
