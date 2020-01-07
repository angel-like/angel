package com.xmsy.server.zxyy.manager.modules.manager.gamerecordfightlandlords.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xmsy.server.zxyy.manager.modules.manager.gamerecordfightlandlords.entity.GameRecordFightlandlordsEntity;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 游戏记录-斗地主
 * 
 * @author adu
 * @email xxxxx
 * @date 2019-02-21 15:08:39
 */
@Mapper
public interface GameRecordFightlandlordsDao extends BaseMapper<GameRecordFightlandlordsEntity> {
	
	/**
	 * 获取同一局数的玩家信息
	 * @param gameRoundNo
	 * @return
	 */
	List<GameRecordFightlandlordsEntity> getGameList(@Param("gameRoundNo") String gameRoundNo);
	
}
