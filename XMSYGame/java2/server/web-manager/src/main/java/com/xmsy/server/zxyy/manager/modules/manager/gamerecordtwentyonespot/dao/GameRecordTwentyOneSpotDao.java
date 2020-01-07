package com.xmsy.server.zxyy.manager.modules.manager.gamerecordtwentyonespot.dao;

import com.xmsy.server.zxyy.manager.modules.manager.gamerecord.entity.GameRecordFindCardType;
import com.xmsy.server.zxyy.manager.modules.manager.gamerecordtwentyonespot.entity.GameRecordTwentyOneSpotEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 游戏记录-21点
 * 
 * @author xiaoling
 * @email xxxxx
 * @date 2019-09-04 16:44:38
 */
@Mapper
public interface GameRecordTwentyOneSpotDao extends BaseMapper<GameRecordTwentyOneSpotEntity> {
	
	/**
	 * 查找相应的牌型
	 * @return
	 */
	List<GameRecordFindCardType> findCardType(@Param("gameRoundNo") String gameRoundNo);
	
}
