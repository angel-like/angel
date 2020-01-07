package com.xmsy.server.zxyy.webhome.modules.manager.playertasks.service;

import java.util.List;

import com.baomidou.mybatisplus.service.IService;
import com.xmsy.server.zxyy.webhome.modules.manager.playertasks.entity.PlayerTasksEntity;


/**
 * 玩家任务表
 *
 * @author xiaoling
 * @email xxxxx
 * @date 2019-06-17 14:53:30
 */
public interface PlayerTasksService extends IService<PlayerTasksEntity> {
	
	/**
	 * 通过任务类型查询集合
	 * @param type
	 * @return
	 */
	List<PlayerTasksEntity> queryByType(String type);
	
	/**
	 * 查询道具数量
	 * @param taskId
	 * @return
	 */
	Integer queryPropNum(Long taskId);

}

