package com.xmsy.network.util;

import java.util.Collection;

import com.alibaba.fastjson.JSONObject;
import com.xmsy.network.handler.SocketHandler;

import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

/**
 * websocket 消息返回处理类
 * 
 * @author Administrator
 *
 */
@Slf4j
public class OptionUtils {
	/**
	 * 添加json数据
	 * 
	 * @param obj
	 *            待添加数据对象
	 * @param key
	 *            添加数据Key
	 * @param objAdd
	 *            添加数据Value
	 * @return 添加后的对象
	 */
	public static Object result(Object obj, String key, Object objAdd) {
		JSONObject json = (JSONObject) JSONObject.toJSON(obj);
		if (null == json.get(key)) {
			json.put(key, objAdd);
		}
		return json;
	}

	/**
	 * json数据添加错误码：errcode
	 * 
	 * @param obj
	 *            待添加数据对象
	 * @param errCode
	 *            错误码
	 * @return 添加后的对象
	 */
	public static Object result(Collection<?> coll) {
		JSONObject result = new JSONObject();
		if (null == coll) {
			result.put("errcode", CodeDef.SUCCESS);
			return result;
		}
		result.put("list", coll);
		result.put("errcode", CodeDef.SUCCESS);
		return result;
	}

	/**
	 * json数据添加错误码：errcode
	 * 
	 * @param obj
	 *            待添加数据对象
	 * @param errCode
	 *            错误码
	 * @return 添加后的对象
	 */
	public static Object result(Object obj) {
		if (null == obj) {
			JSONObject result = new JSONObject();
			result.put("errcode", CodeDef.SUCCESS);
			return result;
		}
		JSONObject json = (JSONObject) JSONObject.toJSON(obj);
		if (null == json.get("errcode")) {
			json.put("errcode", CodeDef.SUCCESS);
		}
		return json;
	}

	/**
	 * json数据添加错误码：errcode
	 * 
	 * @param obj
	 *            待添加数据对象
	 * @param errCode
	 *            错误码
	 * @return 添加后的对象
	 */
	public static Object result(int errCode, String errMsg) {
		JSONObject result = new JSONObject();
		result.put("errcode", errCode);
		result.put("errMsg", errMsg);
		return result;
	}

	/**
	 * json数据添加错误码：errcode
	 * 
	 * @param obj
	 *            待添加数据对象
	 * @param errCode
	 *            错误码
	 * @return 添加后的对象
	 */
	public static Object success() {
		JSONObject result = new JSONObject();
		result.put("errcode", CodeDef.SUCCESS);
		result.put("errMsg", CodeDef.SUCCESS_MSG);
		return result;
	}

	/**
	 * 包装推送对象
	 * @param obj
	 * @return
	 */
	public static Object getPushEntity(Object obj) {
		if (null == obj) {
			return null;
		}
		JSONObject result = new JSONObject();
		result.put(obj.getClass().getSimpleName().toLowerCase(), obj);
		return result;
	}

	/**
	 * 消息推送
	 * 
	 * @param ctx
	 * @param option
	 * @param obj
	 */
	public static void sendMsgToCtx(ChannelHandlerContext ctx, Object obj) {
		try {
			SocketHandler.sentWebSocketFrame(ctx, result(obj));
		} catch (Exception e) {
			log.error("[OptionUtils]->sendMsgToCtx", e);
		}
	}
}
