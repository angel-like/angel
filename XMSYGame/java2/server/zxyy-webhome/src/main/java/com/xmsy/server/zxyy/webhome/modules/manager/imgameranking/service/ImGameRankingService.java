package com.xmsy.server.zxyy.webhome.modules.manager.imgameranking.service;

import java.util.List;

import com.baomidou.mybatisplus.service.IService;
import com.xmsy.server.zxyy.webhome.modules.manager.imgameranking.entity.ImGameRankingEntity;
import com.xmsy.server.zxyy.webhome.modules.webim.homepage.result.ImGameRankingResult;

/**
 * 33推荐热门游戏排行
 *
 * @author xiaoliu
 * @email xxxxx
 * @date 2019-06-24 11:10:56
 */
public interface ImGameRankingService extends IService<ImGameRankingEntity> {

	List<ImGameRankingResult> selectListForWib();

}
