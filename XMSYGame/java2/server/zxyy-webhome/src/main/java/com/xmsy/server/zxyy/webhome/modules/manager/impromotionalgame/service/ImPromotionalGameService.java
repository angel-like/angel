package com.xmsy.server.zxyy.webhome.modules.manager.impromotionalgame.service;

import java.util.List;

import com.baomidou.mybatisplus.service.IService;
import com.xmsy.server.zxyy.webhome.modules.manager.impromotionalgame.entity.ImPromotionalGameEntity;
import com.xmsy.server.zxyy.webhome.modules.webim.homepage.result.ImPromotionalGameResult;

/**
 * 33推荐游戏管理
 *
 * @author xiaoliu
 * @email xxxxx
 * @date 2019-06-21 15:37:14
 */
public interface ImPromotionalGameService extends IService<ImPromotionalGameEntity> {
	
	
	List<ImPromotionalGameResult> selectPromotionalGameList();

}
