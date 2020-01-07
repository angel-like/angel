package com.xmsy.server.zxyy.webhome.modules.manager.gamerecordzhajinhua.dao;

import com.xmsy.server.zxyy.webhome.modules.manager.gamerecord.entity.GameRecordFindCardType;
import com.xmsy.server.zxyy.webhome.modules.manager.gamerecordzhajinhua.entity.GameRecordZhajinhuaEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 游戏记录-诈金花
 * 
 * @author aleng
 * @email xxxxx
 * @date 2019-03-10 11:13:28
 */
@Mapper
public interface GameRecordZhajinhuaDao extends BaseMapper<GameRecordZhajinhuaEntity> {
	/**
	 * 查找相应的牌型
	 * 
	 * @return
	 */
	List<GameRecordFindCardType> findCardType(@Param("gameRoundNo") String gameRoundNo);
	
}
