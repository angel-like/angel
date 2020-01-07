package com.xmsy.server.zxyy.webhome.modules.manager.gamerecordhaochehui.dao;

import com.xmsy.server.zxyy.webhome.modules.manager.gamerecord.entity.GameRecordFindCardType;
import com.xmsy.server.zxyy.webhome.modules.manager.gamerecordhaochehui.entity.GameRecordHaochehuiEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 游戏记录-豪车会
 * 
 * @author xiaoling
 * @email xxxxx
 * @date 2019-08-29 09:49:38
 */
@Mapper
public interface GameRecordHaochehuiDao extends BaseMapper<GameRecordHaochehuiEntity> {
	
	/**
	 * 查找相应的牌型
	 * 
	 * @return
	 */
	List<GameRecordFindCardType> findCardType(@Param("gameRoundNo") String gameRoundNo);
	
}
