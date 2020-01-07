package com.xmsy.server.zxyy.webhome.listener;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

@Component
public class ApplicationInitBean implements InitializingBean {

	@Override
	public void afterPropertiesSet() throws Exception {
//		// 获取游戏列表
//		JSONObject returnObj = null;
//		try {
//			returnObj = GameInfoInterface.gameInfo();
//		} catch (Exception e) {
//			log.error("error {}", e);
//			throw new Error("游戏信息获取失败");
//		}
//		log.info("returnObj 游戏信息返回结果 {}", returnObj);
//		if (returnObj != null && returnObj.getInteger("code") == 200) {
//			JSONArray gamaInfoList = (JSONArray) returnObj.get("gameinfo");
//			for (int i = 0; i < gamaInfoList.size(); i++) {
//				JSONObject obj = gamaInfoList.getJSONObject(i);
//				SysConstant.gameMap.put(obj.getLongValue("gameId"), obj.getString("gameName"));
//				SysConstant.gradeMap.put(obj.getLongValue("gradeId"), obj.getString("gradeName"));
//				SysConstant.roomMap.put(obj.getLongValue("roomId"), obj.getString("roomName"));
//				SysConstant.gameRoomMap.put(obj.getLongValue("gameId"), obj.getString("roomName"));
//			}
//			log.info("gameMap 游戏信息map {}", SysConstant.gameMap);
//			log.info("gradeMap 游戏场次map {}", SysConstant.gradeMap);
//			log.info("roomMap 房间map {}", SysConstant.roomMap);
//		}
//		// 获取大厅列表
//		List<Integer> hallIdList = new ArrayList<>();
//		JSONObject hallReturnObj = GameInfoInterface.hallList();
//		log.info("hallReturnObj 大厅列表返回结果 {}", hallReturnObj);
//		if (null == hallReturnObj || null == hallReturnObj.getInteger("code")
//				|| hallReturnObj.getInteger("code") != ResultDef.SUCCESS) {
//			throw new RRException("游戏大厅列表获取失败！");
//		}
//		JSONArray hallArry = hallReturnObj.getJSONObject("data").getJSONArray("hall");
//		if (CollectionUtils.isEmpty(hallArry)) {
//			log.error("游戏大厅列表获取失败 hallArry {}", hallArry);
//			throw new RRException("游戏大厅列表获取失败！");
//		}
//		for (int i = 0; i < hallArry.size(); i++) {
//			JSONObject hallobj = hallArry.getJSONObject(i);
//			hallIdList.add(hallobj.getInteger("id"));
//			SysConstant.hallMap.put(hallobj.getInteger("id"), hallobj.toJSONString());
//
//		}
//		// 获取大厅ip列表
//		try {
//			for (Integer hallid : hallIdList) {
//				String url = HallUrlConstant.getHALL_URL() + hallid;
//				String result = HttpRequest.get(url).timeout(5000).execute().body();
//				JSONObject robj = JSON.parseObject(result);
//				if (robj == null || robj.getInteger("Result") != 0 || StringUtils.isEmpty(robj.getString("HallIp"))) {
//					log.error("获取大厅ip失败  result {}", result);
//					continue;
//				}
//				SysConstant.hallAddressMap.put(hallid, robj.getString("HallIp"));
//			}
//		} catch (Exception e) {
//			log.error("获取大厅ip失败 error {}", e);
//		}
	}

}
