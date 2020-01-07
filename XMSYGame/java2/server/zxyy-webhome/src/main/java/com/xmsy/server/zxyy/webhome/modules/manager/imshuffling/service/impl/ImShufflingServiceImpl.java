package com.xmsy.server.zxyy.webhome.modules.manager.imshuffling.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;


import com.xmsy.server.zxyy.webhome.modules.manager.imshuffling.dao.ImShufflingDao;
import com.xmsy.server.zxyy.webhome.modules.manager.imshuffling.entity.ImShufflingEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.imshuffling.service.ImShufflingService;
import com.xmsy.server.zxyy.webhome.modules.webim.homepage.result.ImShufflingResult;


@Service("imShufflingService")
public class ImShufflingServiceImpl extends ServiceImpl<ImShufflingDao, ImShufflingEntity> implements ImShufflingService {

	@Override
	public List<ImShufflingResult> selectShufflingList() {
		return baseMapper.selectShufflingList();
	}


}
