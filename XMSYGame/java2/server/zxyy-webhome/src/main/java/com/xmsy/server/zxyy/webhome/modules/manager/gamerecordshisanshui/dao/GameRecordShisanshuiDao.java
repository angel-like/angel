package com.xmsy.server.zxyy.webhome.modules.manager.gamerecordshisanshui.dao;

import com.xmsy.server.zxyy.webhome.modules.manager.gamerecord.entity.GameRecordFindCardType;
import com.xmsy.server.zxyy.webhome.modules.manager.gamerecordshisanshui.entity.GameRecordShisanshuiEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 游戏记录-十三水
 * 
 * @author adu
 * @email xxxxx
 * @date 2019-03-16 14:36:32
 */
@Mapper
public interface GameRecordShisanshuiDao extends BaseMapper<GameRecordShisanshuiEntity> {
	/**
	 * 查找相应的牌型
	 * @return
	 */
	List<GameRecordFindCardType> findCardType(@Param("gameRoundNo") String gameRoundNo);
	
}
