package com.xmsy.server.zxyy.webhome.modules.manager.imgameranking.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xmsy.server.zxyy.webhome.modules.manager.imgameranking.dao.ImGameRankingDao;
import com.xmsy.server.zxyy.webhome.modules.manager.imgameranking.entity.ImGameRankingEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.imgameranking.service.ImGameRankingService;
import com.xmsy.server.zxyy.webhome.modules.webim.homepage.result.ImGameRankingResult;

@Service("imGameRankingService")
public class ImGameRankingServiceImpl extends ServiceImpl<ImGameRankingDao, ImGameRankingEntity>
		implements ImGameRankingService {

	@Override
	public List<ImGameRankingResult> selectListForWib() {
		// TODO Auto-generated method stub
		return baseMapper.selectListForWib();
	}

}
