package com.xmsy.server.zxyy.manager.modules.manager.gamerecordbairenniuniu.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xmsy.server.zxyy.manager.modules.manager.gamerecord.entity.GameRecordFindCardType;
import com.xmsy.server.zxyy.manager.modules.manager.gamerecordbairenniuniu.entity.GameRecordBairenniuniuEntity;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 游戏记录-百人牛牛
 * 
 * @author aleng
 * @email xxxxx
 * @date 2019-03-28 16:28:26
 */
@Mapper
public interface GameRecordBairenniuniuDao extends BaseMapper<GameRecordBairenniuniuEntity> {
	
	/**
	 * 查找相应的牌型
	 * 
	 * @return
	 */
	List<GameRecordFindCardType> findCardType(@Param("gameRoundNo") String gameRoundNo);
	
}
