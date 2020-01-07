package com.xmsy.server.zxyy.webhome.modules.app.gamerecord;

import java.util.List;

import javax.validation.Valid;

import com.xmsy.server.zxyy.webhome.modules.manager.gamerecord.controller.GameInfoComponent;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.xmsy.common.define.page.PageParam;
import com.xmsy.server.zxyy.webhome.common.exception.RRException;
import com.xmsy.server.zxyy.webhome.common.utils.ErrorCode;
import com.xmsy.server.zxyy.webhome.common.utils.R;
import com.xmsy.server.zxyy.webhome.common.utils.SpringContextUtils;
import com.xmsy.server.zxyy.webhome.constant.GameEnum;
import com.xmsy.server.zxyy.webhome.modules.app.gamerecord.param.AppFangkaGameRecordParams;
import com.xmsy.server.zxyy.webhome.modules.app.gamerecord.param.GameRecordParams;
import com.xmsy.server.zxyy.webhome.modules.app.gamerecord.param.baijia.BaijiaLeGameRecordParams;
import com.xmsy.server.zxyy.webhome.modules.app.gamerecord.param.fightlandlords.FightLandlordsGameRecordParams;
import com.xmsy.server.zxyy.webhome.modules.app.gamerecord.param.longhu.LongHuGameRecordParams;
import com.xmsy.server.zxyy.webhome.modules.app.gamerecord.param.niuniu.tongbi.TongBiNiuNiuGameRecordParams;
import com.xmsy.server.zxyy.webhome.modules.app.gamerecord.result.AppGameParams;
import com.xmsy.server.zxyy.webhome.modules.app.gamerecord.result.AppGameRecordParams;
import com.xmsy.server.zxyy.webhome.modules.manager.gamerecord.service.GameRecordService;
import com.xmsy.server.zxyy.webhome.modules.manager.gamerecordbaijia.service.GameRecordBaijiaService;
import com.xmsy.server.zxyy.webhome.modules.manager.gamerecordcattltongbi.service.GameRecordCattlTongbiService;
import com.xmsy.server.zxyy.webhome.modules.manager.gamerecordfightlandlords.service.GameRecordFightlandlordsService;
import com.xmsy.server.zxyy.webhome.modules.manager.gamerecordlonghu.service.GameRecordLonghuService;
import com.xmsy.server.zxyy.webhome.utils.JwtUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * 游戏记录
 *
 * @author adu
 * @email xxxxx
 * @date 2019-02-20 16:00:25
 */
@Slf4j
@RestController
@RequestMapping("V1.0/Game")
public class AppGameRecordController {
	@Autowired
	private GameInfoComponent gameInfo;
	@Autowired
	private GameRecordService gameRecordService;
	@Autowired
	private GameRecordFightlandlordsService gameRecordFightlandlordsService;
	@Autowired
	private GameRecordCattlTongbiService gameRecordCattlTongbiService;
	@Autowired
	private GameRecordBaijiaService gameRecordBaijiaService;
	@Autowired
	private GameRecordLonghuService gameRecordLonghuService;
	private IAppGameRecordService appGameRecordService;

	/**
	 * 总游戏记录
	 */
	@PostMapping("/GameRecord")
	public R gameRecord(@RequestBody @Valid GameRecordParams gameRecordParams) {
		//log.info("GameRecordParams 游戏记录请求参数 {}", gameRecordParams);
		gameRecordService.appSaveGameRecord(gameRecordParams);
		return R.ok();
	}

	@PostMapping("/GameRecordDetails")
	public R gameRecordDetails(@RequestBody String gameRecordDetailParams) throws Exception {
		//log.info("GameRecordDetails 请求参数 {}", gameRecordDetailParams);
		Long gameId = JSON.parseObject(gameRecordDetailParams).getLong("gameId");
		String name = GameEnum.getGamemap().get(gameId);
		if (StringUtils.isEmpty(name)) {
			log.error("[gameRecordDetails] name is null");
			log.error("GameRecordDetails 请求参数 {}", gameRecordDetailParams);
			throw new RRException(ErrorCode.GameErrorCode.GAMEID_IS_ERRO.getErrMsg(),
					ErrorCode.GameErrorCode.GAMEID_IS_ERRO.getCode());
		}
		appGameRecordService = (IAppGameRecordService) SpringContextUtils.getBean(name);
		if (null == appGameRecordService) {
			log.error("[gameRecordDetails] appGameRecordService is null");
			log.error("GameRecordDetails 请求参数 {}", gameRecordDetailParams);
			throw new RRException(ErrorCode.GameErrorCode.GAMEID_IS_ERRO.getErrMsg(),
					ErrorCode.GameErrorCode.GAMEID_IS_ERRO.getCode());
		}
		appGameRecordService.saveRecord(gameRecordDetailParams);
		return R.ok();
	}

	/**
	 * 斗地主 游戏记录
	 */
	@PostMapping("/FightLandlordsGameRecord")
	public R fightLandlordsGameRecord(@RequestBody @Valid FightLandlordsGameRecordParams gameRecordParams) {
		//log.info("FightLandlordsGameRecord 请求参数 {}", gameRecordParams);
		gameRecordFightlandlordsService.appFightLandlordsSaveGameRecord(gameRecordParams);
		return R.ok();
	}

	/**
	 * 通比牛牛 游戏记录
	 */
	@PostMapping("/TongBiNiuNiuGameRecord")
	public R tongBiNiuNiuGameRecord(@RequestBody @Valid TongBiNiuNiuGameRecordParams gameRecordParams) {
		//log.info("FightLandlordsGameRecord 请求参数 {}", gameRecordParams);
		gameRecordCattlTongbiService.appSaveTongBiNiuNiuGameRecord(gameRecordParams);
		return R.ok();
	}

	/**
	 * 龙虎斗 游戏记录
	 */
	@PostMapping("/LongHuGameRecord")
	public R longHuGameRecord(@RequestBody @Valid LongHuGameRecordParams longHuGameRecordParams) {
		//log.info("LongHuGameRecordParams 请求参数 {}", longHuGameRecordParams);
		gameRecordLonghuService.appSaveGameRecord(longHuGameRecordParams);
		return R.ok();
	}

	/**
	 * 百家乐 游戏记录
	 */
	@PostMapping("/BaijiaGameRecord")
	public R baijiaGameRecord(@RequestBody @Valid BaijiaLeGameRecordParams baijiaLeGameRecordParams) {
		//log.info("BaijiaGameRecord 请求参数 {}", baijiaLeGameRecordParams);
		gameRecordBaijiaService.appSaveGameRecord(baijiaLeGameRecordParams);
		return R.ok();
	}
	

	/**
	 * 通过房间ID获取用户玩过的游戏
	 */
	@GetMapping("/userGameForRoomId")
	public R userGameForRoomId(Long roomId, @RequestHeader("token") String token) {
		//log.info("userGameForRoomId 通过房间ID获取用户玩过的游戏  token {} roomId {}", token, roomId);
		Long userId = Long.valueOf(JwtUtil.getUserId(token));
		List<AppGameParams> list = gameRecordService.userGameForRoomId(roomId, userId);
		for (AppGameParams appGameParams : list) {
			log.info("[开元游戏记录]  appGameParams.getGameName()= {}", appGameParams.getGameName());
			if(StringUtils.isBlank(appGameParams.getGameName())){
				log.info("[开元游戏记录]  gameInfo.getGameName(appGameParams.getGameId())= {}", gameInfo.getGameName(appGameParams.getGameId()));
				appGameParams.setGameName( gameInfo.getGameName(appGameParams.getGameId()));
			}
		}
		log.info(list.toString());
		return R.ok().put("data", list);
	}

	/**
	 * 通过游戏ID获取用户玩过的游戏
	 */
	@GetMapping("/userGameRecordForGameId")
	public R userGameRecordForGameId(Long gameId, @RequestHeader("token") String token) {
		//log.info("userGameRecordForGameId 通过房间ID获取用户玩过的游戏 token {} gameId {}", token, gameId);
		Long userId = Long.valueOf(JwtUtil.getUserId(token));
		List<AppGameRecordParams> list = gameRecordService.userGameRecordForGameId(gameId, userId);
		for (AppGameRecordParams appGameRecordParams : list) {
			log.info("[开元游戏记录]  appGameParams.getGameName()= {}", appGameRecordParams.getGameName());
           if(StringUtils.isBlank(appGameRecordParams.getGameName())) {
	         appGameRecordParams.setGameName(gameInfo.getGameName(appGameRecordParams.getGameId()));
	       log.info(gameInfo.getGameName(appGameRecordParams.getGameId()));
			   log.info("[开元游戏记录]  gameInfo.getGameName(appGameRecordParams.getGameId())= {}", gameInfo.getGameName(appGameRecordParams.getGameId()));
}
			if(StringUtils.isBlank(appGameRecordParams.getGradeName())){
				log.info(gameInfo.getGradeName(appGameRecordParams.getGradeId()));
				log.info("[开元游戏记录]  appGameRecordParams.getGradeId()= {}", appGameRecordParams.getGradeId());
				appGameRecordParams.setGradeName(gameInfo.getGradeName(appGameRecordParams.getGradeId()));
			}
		}
		log.info(list.toString());
		return R.ok().put("data", list);
	}
	
	/**
	 * 获取用户玩过的游戏
	 * 房卡个人战绩接口
	 */
	@GetMapping("/userGameRecordForFangka")
	public R userGameRecordForGameId(@RequestHeader("token") String token ,@RequestParam(value = "gameId") Long gameId) {
		//log.info("userGameRecordForFangka 房卡个人战绩接口 token {} ", token);
		Long userId = Long.valueOf(JwtUtil.getUserId(token));
		List<AppGameRecordParams> list = gameRecordService.userGameRecordForFangka(userId,gameId);

		return R.ok().put("data", list);

	}
	/**
	 * 获取用户玩过的游戏
	 * 房卡个人战绩详情接口
	 */
	@GetMapping("/userDetailGameRecordForFangka")
	public R userDetailGameRecordForFangka(PageParam pageParam,@Valid AppFangkaGameRecordParams param) {
		//log.info("userDetailGameRecordForFangka 房卡个人战绩详情接口 token {} param {}", param);
		return R.ok().put("data", gameRecordService.getGameRecordsByRoomNoForFangka(pageParam, param));
	}

}
