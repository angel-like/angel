package com.xmsy.server.zxyy.webhome.modules.manager.playertasksreceiverecord.dao;

import com.xmsy.server.zxyy.webhome.modules.manager.playertasksreceiverecord.entity.PlayerTasksReceiveRecordEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.Date;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 玩家任务领取记录表
 * 
 * @author xiaoling
 * @email xxxxx
 * @date 2019-06-19 09:57:53
 */
@Mapper
public interface PlayerTasksReceiveRecordDao extends BaseMapper<PlayerTasksReceiveRecordEntity> {
	
	
	/**
	 * 查询会员任务是否领取
	 * @param taskId
	 * @param userId
	 * @return
	 */
	Integer queryCount (@Param("taskId") Long taskId,@Param("userId") Long userId,@Param("finishDate") Date finishDate);
	
}
