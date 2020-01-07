package com.xmsy.server.zxyy.game.modules.manager.game.entity.param;

import lombok.Data;

/**
 * 查询游戏参数
 * @author Administrator
 *
 */
@Data
public class GameParam {
	/**
	 * 查询名称
	 */
    private String name; 
    
    private Long gameId;
    
    private Long gradeId;
    
    private Long roomId;
}
