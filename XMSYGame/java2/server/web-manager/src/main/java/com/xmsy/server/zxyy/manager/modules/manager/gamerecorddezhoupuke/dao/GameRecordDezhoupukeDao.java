package com.xmsy.server.zxyy.manager.modules.manager.gamerecorddezhoupuke.dao;

import com.xmsy.server.zxyy.manager.modules.manager.gamerecord.entity.GameRecordFindCardType;
import com.xmsy.server.zxyy.manager.modules.manager.gamerecorddezhoupuke.entity.GameRecordDezhoupukeEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 游戏记录-德州扑克
 * 
 * @author xiaoling
 * @email xxxxx
 * @date 2019-11-22 17:21:28
 */
@Mapper
public interface GameRecordDezhoupukeDao extends BaseMapper<GameRecordDezhoupukeEntity> {
	
	/**
	 * 查找相应的牌型
	 * 
	 * @return
	 */
	List<GameRecordFindCardType> findCardType(@Param("gameRoundNo") String gameRoundNo);
	
	/**
	 * 查找同个局号所有玩家以及公共的牌型
	 * @param gameRoundNo
	 * @return
	 */
	List<GameRecordDezhoupukeEntity> findAllCardType(@Param("gameRoundNo") String gameRoundNo);
	
}
