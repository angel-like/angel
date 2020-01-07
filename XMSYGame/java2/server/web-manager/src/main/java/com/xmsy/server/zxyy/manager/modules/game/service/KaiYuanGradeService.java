package com.xmsy.server.zxyy.manager.modules.game.service;

import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONArray;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xmsy.server.zxyy.manager.constant.HallUrlConstant;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class KaiYuanGradeService {


	/**
	 * 列表
	 */
	public JSONObject getList(JSONObject info) {
		String url = HallUrlConstant.getGAME_SERVICE_URL() + "/gameexperience/kaiyuangrade/list";
		@SuppressWarnings("unchecked")
		String result = HttpRequest.post(url).form(JSONObject.parseObject(info.toJSONString(), Map.class))
				.timeout(5000).execute().body();
		return JSON.parseObject(result);
	}


	/**
	 *  信息
	 */
	public JSONObject getInfo(Long id) {
		String url = HallUrlConstant.getGAME_SERVICE_URL()+"/gameexperience/kaiyuangrade/info/"+id;
		String result = HttpRequest.post(url).timeout(5000).execute().body();
		return JSON.parseObject(result);
	}

	/**
	 * 保存
	 */
	public JSONObject saveInfo(JSONObject json) {
		String url = HallUrlConstant.getGAME_SERVICE_URL() + "/gameexperience/kaiyuangrade/save";
		String result = HttpRequest.post(url).body(json.toJSONString()).timeout(5000).execute().body();
		return JSON.parseObject(result);
	}

	/**
	 * 修改
	 */
	public JSONObject updateInfo(JSONObject json) {
		String url = HallUrlConstant.getGAME_SERVICE_URL() + "/gameexperience/kaiyuangrade/update";
		String result = HttpRequest.post(url).body(json.toJSONString()).timeout(5000).execute().body();
		return JSON.parseObject(result);
	}

	/**
	 *  删除
	 */
	public JSONObject deleteInfo(Long[] ids) {
		JSONArray jsonArray=new JSONArray(ids);
		String url = HallUrlConstant.getGAME_SERVICE_URL() + "/gameexperience/kaiyuangrade/delete";
		String result = HttpRequest.post(url).body(jsonArray)
				.timeout(5000).execute().body();
		return JSON.parseObject(result);
	}

	public String generatorCode(Long[] ids) {
		JSONArray jsonArray=new JSONArray(ids);
		String url = HallUrlConstant.getGAME_SERVICE_URL() + "/gameexperience/kaiyuangrade/exportSql";
		String string = HttpRequest.post(url).body(jsonArray)
				.timeout(5000).execute().body();
		//接收到的数据
		String s=string.substring(1, string.length()-1);
		String s1=s.replaceAll("\\\\n", "\\\r\\\n");
		return s1;
	}
}
