package com.itheima.springboot;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class testJsonArray {
	public static void main(String[] args) {
		Integer[] ob = {11,22,33};
		JSONObject jsonObject=new JSONObject();
		jsonObject.put("gameId", ob);
		jsonObject.put("listId", ob);
		JSONArray jsonArray = jsonObject.getJSONArray("gameId");
		//JSONArray gameId=JSON.parseArray(json.getString("gameId"));//这个无法解析
		for(int i=0;i<jsonArray.size();i++) {
			System.out.println(jsonArray.getInteger(i));
		}
		
		testAPI testAPI=new testAPI();
		Class<? extends com.itheima.springboot.testAPI> class1 = testAPI.getClass();
		System.out.println(class1);
		System.out.println(class1.getName());
		
		
		int num=(10-3)/3;
		int num2=(10-3)%3;
		System.out.println(num+":"+num2);
		
		
		Map<String,String> map=new HashMap<>();
		map.put("s1", "s11");
		map.put("s2", "s22");
		Set<Entry<String, String>> entrySet = map.entrySet();
		for(Entry<String, String> s:entrySet) {
			System.out.println(s.getKey()+"："+s.getValue());
		}
	}
}
