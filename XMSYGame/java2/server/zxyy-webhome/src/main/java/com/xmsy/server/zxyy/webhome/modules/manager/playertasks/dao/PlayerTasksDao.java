package com.xmsy.server.zxyy.webhome.modules.manager.playertasks.dao;

import com.xmsy.server.zxyy.webhome.modules.manager.playertasks.entity.PlayerTasksEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 玩家任务表
 * 
 * @author xiaoling
 * @email xxxxx
 * @date 2019-06-17 14:53:30
 */
@Mapper
public interface PlayerTasksDao extends BaseMapper<PlayerTasksEntity> {
	
	/**
	 * 通过任务类型查询集合
	 * @param type
	 * @return
	 */
	List<PlayerTasksEntity> queryByType(@Param("type") String type);
	
	
	/**
	 * 查询道具数量
	 * @param taskId
	 * @return
	 */
	Integer queryPropNum(@Param("taskId") Long taskId);
	
}
