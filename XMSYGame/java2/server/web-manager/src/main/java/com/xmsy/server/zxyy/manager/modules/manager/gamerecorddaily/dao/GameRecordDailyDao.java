package com.xmsy.server.zxyy.manager.modules.manager.gamerecorddaily.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.xmsy.server.zxyy.manager.modules.manager.gamerecorddaily.entity.GameRecordDailyEntity;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 每日会员游戏总下注记录
 * 
 * @author adu
 * @email xxxxx
 * @date 2019-04-11 20:30:48
 */
@Mapper
public interface GameRecordDailyDao extends BaseMapper<GameRecordDailyEntity> {
	
	/**
	 * 获取会员抽水数值总数
	 * @param gamerecorddaily
	 * @return
	 */
	List<Map<String, Object>> selectuserPumpTotal(@Param("gameRecordDaily") GameRecordDailyEntity gamerecorddaily, Pagination page);
	
	/**
	 * 获取会员抽水数值总数
	 * @param gamerecorddaily
	 * @return
	 */
	List<GameRecordDailyEntity> selectuserPumpList(@Param("gameRecordDaily") GameRecordDailyEntity gamerecorddaily);
}
