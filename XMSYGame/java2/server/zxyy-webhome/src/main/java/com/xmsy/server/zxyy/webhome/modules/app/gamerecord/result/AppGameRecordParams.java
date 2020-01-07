package com.xmsy.server.zxyy.webhome.modules.app.gamerecord.result;

import lombok.Data;

/**
 * app查询游戏记录的参数
 * 
 * @author xiaoliu
 * @email xxxxx
 * @date 2019-03-09 10:00:25
 */
@Data
public class AppGameRecordParams {

	/**
	 * 会员账号
	 */
	private String userAccount;
	/**
	 * 游戏局号
	 */
	private String gameRoundNo;
	/**
	 * 游戏id
	 */
	private Long gameId;
	/**
	 * 游戏名
	 */
	private String gameName;
	/**
	 * 场次名
	 */
	private Long gradeId;
	private String gradeName;
	/**
	 * 下注金额
	 */
	private Long betCoins;
	/**
	 * 盈利金额
	 */
	private Long prizeCoins;
	/**
	 * 游戏时间
	 */
	private String createTime;
	/**
	 * 庄家牌型
	 */
	private String bankerCardStr;
	/**
	 * 闲家牌型
	 */
	private String idleCardStr;
	/**
	 * 游戏房间号
	 */
	private String roomNo;
	/**
	 *局数
	 */
	private Integer round;
	
//	/**
//	 * 是否小游戏
//	 */
//	private Boolean mini;

}
