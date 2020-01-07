package com.xmsy.server.zxyy.webhome.modules.manager.gameinfo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xmsy.common.define.constant.ResultDef;
import com.xmsy.server.zxyy.webhome.common.utils.R;
import com.xmsy.server.zxyy.webhome.constant.HallUrlConstant;
import com.xmsy.server.zxyy.webhome.modules.gamemanage.GameSelectParam;
import com.xmsy.server.zxyy.webhome.modules.gamemanage.gameinfo.server.GameInfoInterface;

import cn.hutool.http.HttpRequest;
import lombok.extern.slf4j.Slf4j;

/**
 * 游戏信息
 *
 * @author lzy
 * @email xxxxx
 * @date 2019-01-16 16:45:35
 */
@Slf4j
@RestController
@RequestMapping("gameinfo/gameinfo")
public class GameInfoController {

	/**
	 * 游戏下拉
	 */
	@RequestMapping("/gameSelect")
	public R gameSelect() {
		List<GameSelectParam> gameList2 = new ArrayList<>();
		List<GameSelectParam> gameList = GameInfoInterface.gameSelect();
		for (GameSelectParam gsp:gameList){
			if (!gsp.getRoom().equals(2L)){
				gameList2.add(gsp);
			}

		}
		return R.ok().put("data", gameList2);
	}

	/**
	 * 游戏菜单
	 */
	@RequestMapping("/gameMenu")
	public R gameMenu() {
		String zxyyToken = HallUrlConstant.getGAME_MANAGER_TOKEN();
		String url = HallUrlConstant.getGAME_MANAGER_GAME_MENU();
		String result = HttpRequest.get(url).header("token", zxyyToken).execute().body();
		JSONObject returnObj = JSON.parseObject(result);
		log.info("returnObj 游戏下拉返回结果 {}", returnObj);
		if (returnObj.getInteger("code") != null && returnObj.getInteger("code") == ResultDef.SUCCESS) {
			return R.ok().put("data", returnObj.get("data"));
		}
		return R.ok().put("data", result);
	}
}
