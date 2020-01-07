package com.xmsy.server.zxyy.robot.modules.gamemanage;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xmsy.server.zxyy.robot.constant.HallUrlConstant;

import cn.hutool.http.HttpRequest;

/**
 * 请求游戏后台的接口
 * 
 * @author adu
 * @date 2019-02-26
 */
// @Slf4j
public class GameInfoInterface {
	/**
	 * 游戏列表(id,name.snace)
	 */
	public static JSONObject gameSelect() {
		String zxyyToken = HallUrlConstant.getGAME_MANAGER_TOKEN();
		String url = HallUrlConstant.getGAME_MANAGER_GAME_SELECT();
		// log.info("[gameSelect] url {}, zxyyToken {}", url,zxyyToken);
		String gameResult = HttpRequest.get(url).header("token", zxyyToken).execute().body();
		JSONObject returnObj = JSON.parseObject(gameResult);
		// log.info("[gameSelect] returnObj {}", returnObj);
		return returnObj;
	}

	/**
	 * 游戏列表
	 */
	public static JSONObject gameSelectForRobot() {
		String zxyyToken = HallUrlConstant.getGAME_MANAGER_TOKEN();
		String url = HallUrlConstant.getGAME_MANAGER_GAME_SELECT_FOR_ROBOT();
		// log.info("[gameSelectForRobot] url {}, zxyyToken {}", url,zxyyToken);
		String gameResult = HttpRequest.get(url).header("token", zxyyToken).execute().body();
		JSONObject returnObj = JSON.parseObject(gameResult);
		// log.info("[gameSelectForRobot] returnObj {}", returnObj);
		return returnObj;
	}

	/**
	 * 游戏列表 通过房间Id获取
	 */
	public static JSONObject gameSelectForRobot(Long roomId) {
		String zxyyToken = HallUrlConstant.getGAME_MANAGER_TOKEN();
		String url = HallUrlConstant.getGAME_MANAGER_GAME_SELECT_FOR_ROBOT();
		// log.info("[gameSelectForRobot] url {}, zxyyToken {}", url,zxyyToken);
		String gameResult = HttpRequest.get(url + "?roomId=" + roomId).header("token", zxyyToken).execute().body();
		JSONObject returnObj = JSON.parseObject(gameResult);
		// log.info("[gameSelectForRobot] returnObj {}", returnObj);
		return returnObj;
	}

	public static JSONObject roomSelect() {
		String zxyyToken = HallUrlConstant.getGAME_MANAGER_TOKEN();
		String url = HallUrlConstant.getGAME_MANAGER_ROOM_SELECT();
		// log.info("[roomSelect] url {}, zxyyToken {}", url,zxyyToken);
		String gameResult = HttpRequest.get(url).header("token", zxyyToken).execute().body();
		JSONObject returnObj = JSON.parseObject(gameResult);
		// log.info("[roomSelect] returnObj {}", returnObj);
		return returnObj;
	}
}
