package com.xmsy.server.zxyy.manager.modules.manager.gamerecordkaiyuan.dao;

import com.xmsy.server.zxyy.manager.modules.manager.gamerecord.entity.GameRecordFindCardType;
import com.xmsy.server.zxyy.manager.modules.manager.gamerecordkaiyuan.entity.GameRecordKaiyuanEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 开源游戏
 * 
 * @author sanqian
 * @email xxxxx
 * @date 2019-12-02 14:21:31
 */
@Mapper
public interface GameRecordKaiyuanDao extends BaseMapper<GameRecordKaiyuanEntity> {
	
	/**
	 * 查找相应的牌型
	 * 
	 * @return
	 */
	List<GameRecordFindCardType> findCardType(@Param("gameRoundNo") String gameRoundNo);
	
}
