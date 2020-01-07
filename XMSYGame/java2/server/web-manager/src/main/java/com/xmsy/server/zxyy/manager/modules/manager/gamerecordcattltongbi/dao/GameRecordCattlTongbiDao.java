package com.xmsy.server.zxyy.manager.modules.manager.gamerecordcattltongbi.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xmsy.server.zxyy.manager.modules.manager.gamerecord.entity.GameRecordFindCardType;
import com.xmsy.server.zxyy.manager.modules.manager.gamerecordcattltongbi.entity.GameRecordCattlTongbiEntity;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 游戏记录-通比牛牛
 * 
 * @author adu
 * @email xxxxx
 * @date 2019-02-28 15:32:44
 */
@Mapper
public interface GameRecordCattlTongbiDao extends BaseMapper<GameRecordCattlTongbiEntity> {
	
	/**
	 * 查找相应的牌型
	 * @return
	 */
	List<GameRecordFindCardType> findCardType(@Param("gameRoundNo") String gameRoundNo);
}
