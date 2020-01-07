package com.xmsy.server.zxyy.manager.modules.manager.gamerecordxuezhanmajiangfaka.dao;

import com.xmsy.server.zxyy.manager.modules.manager.gamerecord.entity.GameRecordFindCardType;
import com.xmsy.server.zxyy.manager.modules.manager.gamerecordxuezhanmajiangfaka.entity.GameRecordXuezhanmajiangFakaEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 游戏记录-房卡血战麻将
 * 
 * @author xiaoling
 * @email xxxxx
 * @date 2019-12-06 14:47:56
 */
@Mapper
public interface GameRecordXuezhanmajiangFakaDao extends BaseMapper<GameRecordXuezhanmajiangFakaEntity> {
	
	/**
	 * 查找相应的牌型
	 * 
	 * @return
	 */
	List<GameRecordFindCardType> findCardType(@Param("gameRoundNo") String gameRoundNo);
	
}
