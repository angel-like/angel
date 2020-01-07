package com.xmsy.server.zxyy.webhome.modules.manager.impromotionalgame.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xmsy.server.zxyy.webhome.modules.manager.impromotionalgame.entity.ImPromotionalGameEntity;
import com.xmsy.server.zxyy.webhome.modules.webim.homepage.result.ImPromotionalGameResult;

/**
 * 33推荐游戏管理
 * 
 * @author xiaoliu
 * @email xxxxx
 * @date 2019-06-21 15:37:14
 */
@Mapper
public interface ImPromotionalGameDao extends BaseMapper<ImPromotionalGameEntity> {

	/**
	 * 获取状态为启用的推荐游戏，并通过orderNo排序
	 * 
	 * @author xiaoliu
	 *
	 * @return
	 */
	List<ImPromotionalGameResult> selectPromotionalGameList();

}
