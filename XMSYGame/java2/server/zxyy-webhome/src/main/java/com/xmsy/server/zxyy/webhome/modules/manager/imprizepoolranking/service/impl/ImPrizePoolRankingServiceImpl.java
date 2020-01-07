package com.xmsy.server.zxyy.webhome.modules.manager.imprizepoolranking.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xmsy.server.zxyy.webhome.modules.manager.imprizepoolranking.dao.ImPrizePoolRankingDao;
import com.xmsy.server.zxyy.webhome.modules.manager.imprizepoolranking.entity.ImPrizePoolRankingEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.imprizepoolranking.service.ImPrizePoolRankingService;
import com.xmsy.server.zxyy.webhome.modules.webim.homepage.result.ImGamePrizeRankingResult;

@Service("imPrizePoolRankingService")
public class ImPrizePoolRankingServiceImpl extends ServiceImpl<ImPrizePoolRankingDao, ImPrizePoolRankingEntity>
		implements ImPrizePoolRankingService {

	@Override
	public List<ImGamePrizeRankingResult> selectListForWib() {
		// TODO Auto-generated method stub
		return baseMapper.selectListForWib();
	}

}
