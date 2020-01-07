package com.xmsy.server.zxyy.webhome.modules.manager.imprizepoolranking.service;

import java.util.List;

import com.baomidou.mybatisplus.service.IService;
import com.xmsy.server.zxyy.webhome.modules.manager.imprizepoolranking.entity.ImPrizePoolRankingEntity;
import com.xmsy.server.zxyy.webhome.modules.webim.homepage.result.ImGamePrizeRankingResult;

/**
 * 33推荐热门游戏排行
 *
 * @author xiaoliu
 * @email xxxxx
 * @date 2019-06-24 15:25:30
 */
public interface ImPrizePoolRankingService extends IService<ImPrizePoolRankingEntity> {

	List<ImGamePrizeRankingResult> selectListForWib();

}
