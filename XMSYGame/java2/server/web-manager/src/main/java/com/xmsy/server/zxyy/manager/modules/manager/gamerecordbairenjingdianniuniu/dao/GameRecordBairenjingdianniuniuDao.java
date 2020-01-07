package com.xmsy.server.zxyy.manager.modules.manager.gamerecordbairenjingdianniuniu.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xmsy.server.zxyy.manager.modules.manager.gamerecord.entity.GameRecordFindCardType;
import com.xmsy.server.zxyy.manager.modules.manager.gamerecordbairenjingdianniuniu.entity.GameRecordBairenjingdianniuniuEntity;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 游戏记录-百人经典牛牛
 * 
 * @author xiaoling
 * @email xxxxx
 * @date 2019-05-29 17:19:22
 */
@Mapper
public interface GameRecordBairenjingdianniuniuDao extends BaseMapper<GameRecordBairenjingdianniuniuEntity> {
	/**
	 * 查找相应的牌型
	 * 
	 * @return
	 */
	List<GameRecordFindCardType> findCardType(@Param("gameRoundNo") String gameRoundNo);
	
}
