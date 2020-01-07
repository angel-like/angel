package com.xmsy.server.zxyy.webhome.modules.manager.gamerecordkaiyuan.dao;

import com.xmsy.server.zxyy.webhome.modules.manager.gamerecordkaiyuan.entity.GameRecordKaiyuanEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 开源游戏
 * 
 * @author sanqian
 * @email xxxxx
 * @date 2019-12-02 14:25:01
 */
@Mapper
public interface GameRecordKaiyuanDao extends BaseMapper<GameRecordKaiyuanEntity> {

    List<GameRecordKaiyuanEntity> findRecordListByGameId(@Param("gameId") String gameId);
	
}
