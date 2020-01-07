package com.xmsy.server.zxyy.game.modules.manager.gameconfig.dao;



import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xmsy.server.zxyy.game.modules.manager.gameconfig.entity.GameConfigEntity;

/**
 * 
 * 
 * @author aleng
 * @email xxxxx
 * @date 2019-01-26 11:40:23
 */
@Mapper
public interface GameConfigDao extends BaseMapper<GameConfigEntity> {
	/**
	 * 根据gameId和name
	 * 增量更新val
	 * @param id
	 */
	Integer updateGameConfigValForIncrement(GameConfigEntity gameConfigEntity);
	/**
	 * 根据gameId和name
	 * 更新val
	 * @param id
	 */
	Integer updateGameConfigVal(GameConfigEntity gameConfigEntity);
	/**
	 * 物理删除
	 * @param id
	 */
	Integer physicsDeleteById(Long id);
	
	/**
	 * 
	 */
	Integer saveGameConfig(GameConfigEntity gameConfig);
}
