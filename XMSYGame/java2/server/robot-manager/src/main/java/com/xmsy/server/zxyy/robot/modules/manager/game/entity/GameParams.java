package com.xmsy.server.zxyy.robot.modules.manager.game.entity;

import lombok.Data;

/**
 * 游戏列表
 * 
 * @author xiaoliu
 * @email xxxxx
 * @date 2019-04-11 11:15:25
 */
@Data
public class GameParams  {

	/**
	* id
	*/
	private Long id;
							/**
	 * 名称
	 */
    private String name;
			/**
	 * 机器人数量
	 */
    private Integer num;
    /**
	 * 总盈利
	 */
    private Long profitCoin;

}
