package com.xmsy.server.zxyy.manager.modules.manager.gamerecordpipeizhajinhua.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xmsy.server.zxyy.manager.modules.manager.gamerecord.entity.GameRecordFindCardType;
import com.xmsy.server.zxyy.manager.modules.manager.gamerecordpipeizhajinhua.entity.GameRecordPipeizhajinhuaEntity;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 游戏记录-匹配炸金花
 * 
 * @author xiaoling
 * @email xxxxx
 * @date 2019-03-29 09:33:49
 */
@Mapper
public interface GameRecordPipeizhajinhuaDao extends BaseMapper<GameRecordPipeizhajinhuaEntity> {

	/**
	 * 查找相应的牌型
	 * 
	 * @return
	 */
	List<GameRecordFindCardType> findCardType(@Param("gameRoundNo") String gameRoundNo);

}
