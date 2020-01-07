package com.xmsy.server.zxyy.webhome.constant;

import java.util.Map;

import com.google.common.collect.Maps;

public enum GameEnum {
	/**
	 * 斗地主
	 */
	GAME_1(1L, "gameRecordFightlandlordsService"),
	/**
	 * 抢庄牛牛
	 */
	GAME_2(2L, "gameRecordCattlQiangzhuangService"),
	/**
	 * 通比牛牛
	 */
	GAME_3(3L, "gameRecordCattlTongbiService"),
	/**
	 * 百人牛牛
	 */
	GAME_4(4L, "gameRecordBairenniuniuService"),
	/**
	 * 百人牌九
	 */
	GAME_5(5L, "gameRecordPaijiuService"),
	/**
	 * 百人炸金花
	 */
	GAME_6(6L, "gameRecordZhajinhuaService"),
	/**
	 * 十三水
	 */
	GAME_7(7L, "gameRecordShisanshuiService"),
	/**
	 * 匹配炸金花
	 */
	GAME_8(8L, "gameRecordPipeizhajinhuaService"),
	/**
	 * 龙虎斗
	 */
	GAME_9(9L, "gameRecordLonghuService"),
	/**
	 * 百家乐
	 */
	GAME_10(10L, "gameRecordBaijiaService"),
	/**
	 * 二八杠
	 */
	GAME_11(11L, "gameRecordTwoeightbarService"),
	/**
	 * 百人三公
	 */
	GAME_12(12L, "gameRecordBairensangongService"),
	/**
	 * 房卡牛牛
	 */
	GAME_13(13L, "gameRecordCattlFangkaService"),
	/**
	 * 房卡十三水
	 */
	GAME_14(14L, "gameRecordShisanshuiFangkaService"),
	
	/**
	 * 百人經典牛牛
	 */
	GAME_15(15L, "gameRecordBairenjingdianniuniuService"),
	
	/**
	 * 德州扑克
	 */
	GAME_16(16L, "gameRecordDezhoupukeService"),
	/**
	 * 房卡炸金花
	 */
	GAME_18(18L, "gameRecordZhajinhuaFangkaService"),
	/**
	 * 房卡斗地主
	 */
	GAME_19(19L, "gameRecordFightlandlordsFangkaService"),
	
	/**
	 * 红黑大战
	 */
	GAME_20(20L, "gameRecordHongheidazhanService"),
	
	/**
	 * 跑得快
	 */
	GAME_21(21L, "gameRecordPaodekuaiService"),
	
	/**
	 * 房卡跑得快
	 */
	GAME_22(22L, "gameRecordPaodekuaiFangkaService"),
	
	/**
	 * 21点
	 */
	GAME_23(23L, "gameRecordTwentyOneSpotService"),
	
	/**
	 * 抢庄三公
	 */
	GAME_24(24L, "gameRecordSangongQiangzhuangService"),
	
	/**
	 * 血战麻将
	 */
	GAME_25(25L, "gameRecordXuezhanmajiangService"),
	
	/**
	 * 房卡血战麻将
	 */
	GAME_26(26L, "gameRecordXuezhanmajiangFakaService"),
	
	/**
	 * 2D捕鱼
	 */
	GAME_27(27L, "gameRecord2dbuyuService"),
	
	/**
	 * 海底宝藏
	 */
	GAME_201(201L, "gameRecordUnderseaTreasureService"),
	
	/**
	 * 豪车会
	 */
	GAME_202(202L, "gameRecordHaochehuiService"),
	
	/**
	 * 飞禽走兽
	 */
	GAME_203(203L, "gameRecordFeiqinzoushouService"),
	/**
	 * 欢乐水果机
	 */
	GAME_204(204L, "gameRecordUnderseaTreasureService"),
	/**
	 * 女武神
	 */
	GAME_205(205L, "gameRecordUnderseaTreasureService"),
	/**
	 * 甜心物语
	 */
	GAME_206(206L, "gameRecordUnderseaTreasureService"),
	/**
	 * 成龙历险记
	 */
	GAME_207(207L, "gameRecordUnderseaTreasureService"),
	/**
	 * 老司机
	 */
	GAME_208(208L, "gameRecordUnderseaTreasureService");
	

	private Long id;
	private String name;

	private static final Map<Long, String> gameMap = Maps.newConcurrentMap();

	static {
		for (GameEnum gameEnum : GameEnum.values()) {
			gameMap.put(gameEnum.getId(), gameEnum.getName());
		}
	}

	private GameEnum(Long id, String name) {
		this.id = id;
		this.name = name;
	};

	public Long getId() {
		return this.id;
	}

	public String getName() {
		return name;
	}

	public static Map<Long, String> getGamemap() {
		return gameMap;
	}
}
