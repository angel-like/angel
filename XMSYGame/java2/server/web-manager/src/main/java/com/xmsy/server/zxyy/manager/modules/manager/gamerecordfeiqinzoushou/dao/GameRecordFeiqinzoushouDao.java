package com.xmsy.server.zxyy.manager.modules.manager.gamerecordfeiqinzoushou.dao;

import com.xmsy.server.zxyy.manager.modules.manager.gamerecord.entity.GameRecordFindCardType;
import com.xmsy.server.zxyy.manager.modules.manager.gamerecordfeiqinzoushou.entity.GameRecordFeiqinzoushouEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 游戏记录-飞禽走兽
 * 
 * @author xiaoling
 * @email xxxxx
 * @date 2019-09-06 10:47:28
 */
@Mapper
public interface GameRecordFeiqinzoushouDao extends BaseMapper<GameRecordFeiqinzoushouEntity> {
	
	/**
	 * 查找相应的牌型
	 * 
	 * @return
	 */
	List<GameRecordFindCardType> findCardType(@Param("gameRoundNo") String gameRoundNo);
	
}
