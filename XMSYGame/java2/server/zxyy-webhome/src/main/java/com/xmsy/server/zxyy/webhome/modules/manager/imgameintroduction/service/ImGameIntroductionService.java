package com.xmsy.server.zxyy.webhome.modules.manager.imgameintroduction.service;

import java.util.List;

import com.baomidou.mybatisplus.service.IService;
import com.xmsy.server.zxyy.webhome.modules.manager.imgameintroduction.entity.ImGameIntroductionEntity;
import com.xmsy.server.zxyy.webhome.modules.webim.homepage.result.ImGameIntroductionResult;

/**
 * 33推荐游戏介绍
 *
 * @author xiaoliu
 * @email xxxxx
 * @date 2019-06-22 19:50:09
 */
public interface ImGameIntroductionService extends IService<ImGameIntroductionEntity> {

	List<ImGameIntroductionResult> selectListForWib();

}
