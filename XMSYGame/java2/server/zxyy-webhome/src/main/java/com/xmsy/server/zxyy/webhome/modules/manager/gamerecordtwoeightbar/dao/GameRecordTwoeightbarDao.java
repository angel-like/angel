package com.xmsy.server.zxyy.webhome.modules.manager.gamerecordtwoeightbar.dao;

import com.xmsy.server.zxyy.webhome.modules.manager.gamerecord.entity.GameRecordFindCardType;
import com.xmsy.server.zxyy.webhome.modules.manager.gamerecordtwoeightbar.entity.GameRecordTwoeightbarEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 游戏记录-二八杠
 * 
 * @author adu
 * @email xxxxx
 * @date 2019-03-16 14:36:32
 */
@Mapper
public interface GameRecordTwoeightbarDao extends BaseMapper<GameRecordTwoeightbarEntity> {

	/**
	 * 查找相应的牌型
	 * 
	 * @return
	 */
	List<GameRecordFindCardType> findCardType(@Param("gameRoundNo") String gameRoundNo, @Param("round") Integer round);

}
