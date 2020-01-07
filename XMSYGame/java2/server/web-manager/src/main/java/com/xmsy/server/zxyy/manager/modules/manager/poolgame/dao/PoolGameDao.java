package com.xmsy.server.zxyy.manager.modules.manager.poolgame.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xmsy.server.zxyy.manager.modules.manager.poolgame.entity.PoolGameEntity;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 游戏奖池表
 * 
 * @author lpp
 * @email xxxxx
 * @date 2019-01-21 09:46:00
 */
@Mapper
public interface PoolGameDao extends BaseMapper<PoolGameEntity> {
	/**
	 * 查询奖池列表用于下拉框
	 * @return
	 */
	List<Map<String, Object>> findPoolList();
	/**
	 * 更新奖池根据id
	 * @param id
	 * @param pool
	 * @return
	 */
	Integer updatePool(@Param("id") Long id,@Param("pool") BigDecimal pool);
	/**
	 * 查询奖池的总金额
	 * @return
	 */
	BigDecimal sumAllPool();
}
