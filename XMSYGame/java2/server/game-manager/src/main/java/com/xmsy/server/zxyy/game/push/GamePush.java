package com.xmsy.server.zxyy.game.push;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xmsy.server.zxyy.game.modules.manager.game.entity.GameInfoEntity;

import cn.hutool.http.HttpRequest;
import lombok.extern.slf4j.Slf4j;

/**
 * 游戏添加修改的时候通知大厅和游戏中心
 * 
 * @author Administrator
 *
 */
@Slf4j
@Component
public class GamePush {

	@Value("${spring.gamecenter.url}")
	private String modifygameattr;

	@Value("${spring.hallsupport.url}")
	private String addhallsupport;

	/**
	 * 修改游戏属性推送
	 * 
	 * @param gameInfoEntity
	 */
	public void pushModifyGameattr(GameInfoEntity gameInfoEntity) {
		String param = JSON.toJSONString(gameInfoEntity);
		String result = null;
		try {
			result = HttpRequest.post(modifygameattr).body(param).timeout(5000).execute().body();
		} catch (Exception e) {
			log.error("[GamePush->pushModifyGameattr] result {} param {}", result, param, e);
		}
		log.info("[GamePush->pushModifyGameattr] result {} param {}", result, param);

	}

	/**
	 * 添加游戏推送
	 * 
	 * @param gameInfoEntity
	 */
	public void pushAddGame(GameInfoEntity gameInfoEntity, Long hallId) {
		Long gameType = gameInfoEntity.getGameId();
		Long roomType = gameInfoEntity.getRoomId();
		Long gradeType = gameInfoEntity.getGradeId();
		JSONObject game = new JSONObject();
		game.put("HallId", hallId);
		game.put("GameType", gameType);
		game.put("RoomType", roomType);
		game.put("GradeType", gradeType);
		String result = null;
		try {
			result = HttpRequest.post(addhallsupport).body(game.toString()).timeout(5000).execute().body();
		} catch (Exception e) {
			log.error("[GamePush->pushAddGame] result {} param {}", result, game, e);
		}
		log.info("[GamePush->pushAddGame] param {} result {}", game, result);
	}

}
