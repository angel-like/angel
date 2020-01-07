package com.xmsy.server.zxyy.game.modules.manager.gameconfig.service;



import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.service.IService;
import com.xmsy.server.zxyy.game.modules.manager.gameconfig.entity.GameConfigDetailParam;
import com.xmsy.server.zxyy.game.modules.manager.gameconfig.entity.GameConfigEntity;
import com.xmsy.server.zxyy.game.modules.manager.gameconfig.entity.IntervalGameRateParam;
import com.xmsy.server.zxyy.game.modules.web.gameconfig.param.GameCurrentStockResult;


/**
 * 
 *
 * @author aleng
 * @email xxxxx
 * @date 2019-01-26 11:40:23
 */
public interface GameConfigService extends IService<GameConfigEntity> {
	List<Map<String, Object>> getGameConfigList(long gameId);
	Map<String, Object> getGameConfig(long gameId);
	/**
	 * 增量更新当前库存
	 * @param gameCurrentStock
	 */
	void updateCurrentStock(GameCurrentStockResult gameCurrentStock);
	/**
	 * 物理删除
	 * @param id
	 */
	void physicsDeleteById(Long id);
	/**
	 * 根据gameId和name
	 * 更新val
	 * @param gameCurrentStock
	 */
	void updateGameConfigVal(GameConfigEntity entity);
	/**
	 * 通过当前库存，更新    累计库存
	 * @param gameCurrentStock
	 */
	void updateCumulativeStock(GameCurrentStockResult gameCurrentStock);
	/**
	 * 批量更新或插入当前游戏id的所有数据（过时不用）  前端有对数据做校验
	 * @param gameConfigList
	 */
	void updateAllGameConfig(GameConfigDetailParam GameConfigDetail,List<GameConfigEntity> gameConfigList);
	/**
	 * 保存游戏区间
	 * @param intervalGameRate
	 * @param gameConfigList
	 */
	void updateIntervalGameRate(IntervalGameRateParam intervalGameRate);
	/**
	 * 批量更新或插入当前游戏id的所有数据最终版    后端对数据做校验
	 * @param jsonObjectList
	 */
	Long updateAllGameConfigPerfect(List<JSONObject> jsonObjectList);
	/**
	 * 游戏信息表  对应的游戏配置的信息  -游戏id
	 * 
	 * 批量更新或插入当前游戏id的所有数据最终版    后端对数据做校验  ---新版页面
	 * @param sysDictionaryListJSONArray
	 */
	Long updateAllGameConfigPerfectNew(JSONArray sysDictionaryListJSONArray);
}

