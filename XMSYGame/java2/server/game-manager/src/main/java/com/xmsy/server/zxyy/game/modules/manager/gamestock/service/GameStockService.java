package com.xmsy.server.zxyy.game.modules.manager.gamestock.service;

import java.util.List;

import com.baomidou.mybatisplus.service.IService;
import com.xmsy.server.zxyy.game.modules.manager.gamestock.entity.GameStockEntity;


/**
 * 游戏库存
 *
 * @author adu
 * @email xxxxx
 * @date 2019-10-28 17:03:37
 */
public interface GameStockService extends IService<GameStockEntity> {
	GameStockEntity getGameStock(String roomId);
	void updateStock(GameStockEntity entity);
	
	/**
	 * 获取操作记录表的id和风控对象
	 * @return
	 */
	List<GameStockEntity> getInfo();
}

