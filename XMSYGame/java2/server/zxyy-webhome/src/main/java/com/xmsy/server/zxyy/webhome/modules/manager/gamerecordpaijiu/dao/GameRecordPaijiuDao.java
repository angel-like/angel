package com.xmsy.server.zxyy.webhome.modules.manager.gamerecordpaijiu.dao;

import com.xmsy.server.zxyy.webhome.modules.manager.gamerecord.entity.GameRecordFindCardType;
import com.xmsy.server.zxyy.webhome.modules.manager.gamerecordpaijiu.entity.GameRecordPaijiuEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 游戏记录-百人牌九
 * 
 * @author aleng
 * @email xxxxx
 * @date 2019-03-10 11:32:57
 */
@Mapper
public interface GameRecordPaijiuDao extends BaseMapper<GameRecordPaijiuEntity> {
	
	/**
	 * 查找相应的牌型
	 * 
	 * @return
	 */
	List<GameRecordFindCardType> findCardType(@Param("gameRoundNo") String gameRoundNo);
	
}
