package com.xmsy.server.zxyy.webhome.modules.manager.gamerecordbaijia.dao;

import com.xmsy.server.zxyy.webhome.modules.manager.gamerecord.entity.GameRecordFindCardType;
import com.xmsy.server.zxyy.webhome.modules.manager.gamerecordbaijia.entity.GameRecordBaijiaEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 游戏记录-百家乐
 * 
 * @author aleng
 * @email xxxxx
 * @date 2019-03-09 15:37:22
 */
@Mapper
public interface GameRecordBaijiaDao extends BaseMapper<GameRecordBaijiaEntity> {
	/**
	 * 查找相应的牌型
	 * 
	 * @return
	 */
	List<GameRecordFindCardType> findCardType(@Param("gameRoundNo") String gameRoundNo);
	
}
