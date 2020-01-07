package com.xmsy.server.zxyy.webhome.modules.app.configuration.param;

import lombok.Data;


/**
 * APP热门游戏
 * 
 * @author aleng
 * @email xxxxx
 * @date 2019-05-04 11:37:38
 */
@Data
public class AppPopularGamesParam  {
							/**
	 * 游戏ID
	 */
    private Long id;
    /**
	 * 在线人数
	 */
    private int onlineCount;
	}
