package com.xmsy.server.zxyy.webhome.modules.manager.impromotionalgame.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;


import com.xmsy.server.zxyy.webhome.modules.manager.impromotionalgame.dao.ImPromotionalGameDao;
import com.xmsy.server.zxyy.webhome.modules.manager.impromotionalgame.entity.ImPromotionalGameEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.impromotionalgame.service.ImPromotionalGameService;
import com.xmsy.server.zxyy.webhome.modules.webim.homepage.result.ImPromotionalGameResult;


@Service("imPromotionalGameService")
public class ImPromotionalGameServiceImpl extends ServiceImpl<ImPromotionalGameDao, ImPromotionalGameEntity> implements ImPromotionalGameService {

	@Override
	public List<ImPromotionalGameResult> selectPromotionalGameList() {
		return baseMapper.selectPromotionalGameList();
	}


}
