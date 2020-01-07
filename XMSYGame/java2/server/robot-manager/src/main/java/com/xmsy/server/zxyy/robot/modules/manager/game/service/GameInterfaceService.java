package com.xmsy.server.zxyy.robot.modules.manager.game.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xmsy.server.zxyy.robot.constant.HallUrlConstant;
import com.xmsy.server.zxyy.robot.constant.SysConstant;

import cn.hutool.http.HttpRequest;

/**
 * 请求机器人服务的接口
 * @author xiaoliu
 * @date 2019-03-20 
 */
public class GameInterfaceService {
	
	/**
	 * 游戏列表
	 */
	public static JSONObject robotGameList(JSONObject obj){
		String url = HallUrlConstant.getROBOTMANAGER_GAMELIST();
		String gameResult=null;
		if(null!=obj) {
			 gameResult = HttpRequest.post(url).body(obj.toJSONString()).execute().body();
		}else {
			 gameResult = HttpRequest.post(url).execute().body();
		}
		JSONObject returnObj = JSON.parseObject(gameResult);
		return returnObj;
	 }
	
	/**
	 * 游戏机器人配置信息
	 */
	public static JSONObject robotGameConfig(String url){
		String gameResult = HttpRequest.get(url).execute().body();
		JSONObject returnObj = JSON.parseObject(gameResult);
		return returnObj;
	 }
	/**
	 * 回收机器人
	 */
	public static JSONObject takeRobot(String url,String tokens){
		String robotResult = HttpRequest.put(url).header("token",SysConstant.TOKEN).body(tokens).execute().body();
		JSONObject returnObj = JSON.parseObject(robotResult);
		return returnObj;
	 }
	
	/**
	 * 提交配置信息
	 */
	public static JSONObject saveConfig(String url,JSONArray arry){
		String gameResult = HttpRequest.put(url).header("token",SysConstant.TOKEN).body(arry.toString()).execute().body();
		JSONObject returnObj = JSON.parseObject(gameResult);
		return returnObj;
	 }
	/**
	 * 通用get请求
	 */
	public static JSONObject robotGameInterface(String url){
		String gameResult = HttpRequest.get(url).header("token",SysConstant.TOKEN).execute().body();
		JSONObject returnObj = JSON.parseObject(gameResult);
		return returnObj;
	 }
	
}
