package com.xmsy.server.zxyy.manager.modules.manager.gamerecordcattlqiangzhuang.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xmsy.server.zxyy.manager.modules.manager.gamerecord.entity.GameRecordFindCardType;
import com.xmsy.server.zxyy.manager.modules.manager.gamerecordcattlqiangzhuang.entity.GameRecordCattlQiangzhuangEntity;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 游戏记录-抢庄牛牛
 * 
 * @author adu
 * @email xxxxx
 * @date 2019-03-16 14:36:32
 */
@Mapper
public interface GameRecordCattlQiangzhuangDao extends BaseMapper<GameRecordCattlQiangzhuangEntity> {

	/**
	 * 查找相应的牌型
	 * 
	 * @return
	 */
	List<GameRecordFindCardType> findCardType(@Param("gameRoundNo") String gameRoundNo);

}
