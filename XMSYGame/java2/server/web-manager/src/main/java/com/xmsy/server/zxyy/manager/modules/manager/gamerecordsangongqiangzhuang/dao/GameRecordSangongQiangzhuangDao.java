package com.xmsy.server.zxyy.manager.modules.manager.gamerecordsangongqiangzhuang.dao;

import com.xmsy.server.zxyy.manager.modules.manager.gamerecord.entity.GameRecordFindCardType;
import com.xmsy.server.zxyy.manager.modules.manager.gamerecordsangongqiangzhuang.entity.GameRecordSangongQiangzhuangEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 游戏记录-抢庄三公
 * 
 * @author xiaoling
 * @email xxxxx
 * @date 2019-09-04 15:06:19
 */
@Mapper
public interface GameRecordSangongQiangzhuangDao extends BaseMapper<GameRecordSangongQiangzhuangEntity> {
	
	/**
	 * 查找相应的牌型
	 * @return
	 */
	List<GameRecordFindCardType> findCardType(@Param("gameRoundNo") String gameRoundNo);
	
}
