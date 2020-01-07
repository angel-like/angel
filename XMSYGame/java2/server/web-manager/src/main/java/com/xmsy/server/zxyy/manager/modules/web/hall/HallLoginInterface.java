package com.xmsy.server.zxyy.manager.modules.web.hall;

/**
 * 大厅接口
 * 
 * @author Administrator
 *
 */
public class HallLoginInterface {
	
//	/**
//	 * 大厅注册
//	 */
//	public static JSONObject register(String Ip,String account,String recommendationCode ){
//		String zxyyToken=HallUrlConstant.ZXYY_TOKEN;
//		String url=HallUrlConstant.ZXYY_REGISTER;
//		JSONObject obj = new JSONObject();
//		obj.put("ip", Ip);
//		obj.put("account", account);
//		obj.put("token", zxyyToken);
//		obj.put("recommendationCode", recommendationCode);
//		//加密内容
//		String content=AESUtil.encrypt(obj.toString());
//		JSONObject jsonObj=new JSONObject();
//		jsonObj.put("content", content);
//		String result = HttpUtil.post(url, jsonObj.toString());
//		return JSON.parseObject(result);
//	 }
//	/**
//	 * 大厅预登陆
//	 */
//	public static JSONObject Login(String Ip,String account,String recommendationCode ){
//		String zxyyToken=HallUrlConstant.ZXYY_TOKEN;
//		String url=HallUrlConstant.ZXYY_USER_URL;
//		JSONObject obj = new JSONObject();
//		obj.put("ip", Ip);
//		obj.put("account", account);
//		obj.put("token", zxyyToken);
//		obj.put("recommendationCode", recommendationCode);
//		String result = HttpUtil.post(url, obj);
//		return JSON.parseObject(result);
//	 }
//	
//	
	
}