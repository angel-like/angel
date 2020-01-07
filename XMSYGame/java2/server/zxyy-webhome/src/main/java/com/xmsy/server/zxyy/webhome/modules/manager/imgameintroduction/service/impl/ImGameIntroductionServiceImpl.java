package com.xmsy.server.zxyy.webhome.modules.manager.imgameintroduction.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xmsy.server.zxyy.webhome.modules.manager.imgameintroduction.dao.ImGameIntroductionDao;
import com.xmsy.server.zxyy.webhome.modules.manager.imgameintroduction.entity.ImGameIntroductionEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.imgameintroduction.service.ImGameIntroductionService;
import com.xmsy.server.zxyy.webhome.modules.webim.homepage.result.ImGameIntroductionResult;

@Service("imGameIntroductionService")
public class ImGameIntroductionServiceImpl extends ServiceImpl<ImGameIntroductionDao, ImGameIntroductionEntity>
		implements ImGameIntroductionService {

	@Override
	public List<ImGameIntroductionResult> selectListForWib() {
		// TODO Auto-generated method stub
		return baseMapper.selectListForWib();
	}

}
