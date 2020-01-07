package com.xmsy.server.zxyy.game.modules.manager.gamestock.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xmsy.server.zxyy.game.modules.manager.gamestock.entity.GameStockEntity;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

/**
 * 游戏库存
 *
 * @author adu
 * @email xxxxx
 * @date 2019-10-28 17:03:37
 */
@Mapper
public interface GameStockDao extends BaseMapper<GameStockEntity> {
	GameStockEntity getGameStock(String roomId);
	Integer updateStock(GameStockEntity entity) ;
	
	/**
	 * 获取操作记录表的id和风控对象
	 * @return
	 */
	List<GameStockEntity> getInfo();
}
