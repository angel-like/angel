package com.xmsy.server.zxyy.game.modules.manager.game.service;


public interface SqlGeneratorService {
	/**
	 * 升成游戏配置信息的sql代码
	 * @param gameInfo
	 * @param gameConfigList
	 */
	public String generatorCode(Long[] ids);
}
