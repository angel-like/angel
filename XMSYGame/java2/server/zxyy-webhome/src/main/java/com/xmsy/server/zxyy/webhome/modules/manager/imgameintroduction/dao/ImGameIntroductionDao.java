package com.xmsy.server.zxyy.webhome.modules.manager.imgameintroduction.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xmsy.server.zxyy.webhome.modules.manager.imgameintroduction.entity.ImGameIntroductionEntity;
import com.xmsy.server.zxyy.webhome.modules.webim.homepage.result.ImGameIntroductionResult;

/**
 * 33推荐游戏介绍
 * 
 * @author xiaoliu
 * @email xxxxx
 * @date 2019-06-22 19:50:09
 */
@Mapper
public interface ImGameIntroductionDao extends BaseMapper<ImGameIntroductionEntity> {

	/**
	 * 游戏介绍列表
	 * 
	 * @author xiaoliu
	 *
	 * @return
	 */
	List<ImGameIntroductionResult> selectListForWib();

}
