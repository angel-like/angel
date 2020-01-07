package com.xmsy.server.zxyy.webhome.modules.manager.gamerecordhongheidazhan.dao;

import com.xmsy.server.zxyy.webhome.modules.manager.gamerecord.entity.GameRecordFindCardType;
import com.xmsy.server.zxyy.webhome.modules.manager.gamerecordhongheidazhan.entity.GameRecordHongheidazhanEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 游戏记录-红黑大战
 * 
 * @author xiaoling
 * @email xxxxx
 * @date 2019-08-29 09:49:39
 */
@Mapper
public interface GameRecordHongheidazhanDao extends BaseMapper<GameRecordHongheidazhanEntity> {
	
	/**
	 * 查找相应的牌型
	 * 
	 * @return
	 */
	List<GameRecordFindCardType> findCardType(@Param("gameRoundNo") String gameRoundNo);
	
}
