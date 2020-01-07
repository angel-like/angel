package com.xmsy.server.zxyy.game.modules.manager.hall.service;

import java.util.Map;

import com.baomidou.mybatisplus.service.IService;
import com.xmsy.server.zxyy.game.modules.manager.hall.entity.HallEntity;
import com.xmsy.server.zxyy.game.modules.web.hall.result.HallResult;

/**
 * 
 *
 * @author aleng
 * @email xxxxx
 * @date 2019-01-31 14:03:04
 */
public interface HallService extends IService<HallEntity> {

	Map<Long, HallResult> selectAll() throws Exception;
	
	//修改大厅
	Integer updateHallById(HallEntity entity);

}
