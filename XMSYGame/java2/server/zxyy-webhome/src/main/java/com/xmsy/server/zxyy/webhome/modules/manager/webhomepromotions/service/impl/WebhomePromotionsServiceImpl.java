package com.xmsy.server.zxyy.webhome.modules.manager.webhomepromotions.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xmsy.common.define.page.PageParam;
import com.xmsy.server.zxyy.webhome.modules.manager.webhomepromotions.dao.WebhomePromotionsDao;
import com.xmsy.server.zxyy.webhome.modules.manager.webhomepromotions.entity.WebhomePromotionsEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.webhomepromotions.entity.WebhomePromotionsListEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.webhomepromotions.service.WebhomePromotionsService;


@Service("webhomePromotionsService")
public class WebhomePromotionsServiceImpl extends ServiceImpl<WebhomePromotionsDao, WebhomePromotionsEntity> implements WebhomePromotionsService {

	@Override
	public Page<WebhomePromotionsListEntity> getPromotions(Long typeId, PageParam pageParam) {
		Page<WebhomePromotionsListEntity> page = new Page<>(pageParam.getPage(), pageParam.getLimit());// 当前页，总条数 构造 page 对象
		return page.setRecords(this.baseMapper.selectPromotions(page,typeId));
	}

	@Override
	public Page<WebhomePromotionsListEntity> getPromotionsList(Long typeId,PageParam pageParam) {
		Page<WebhomePromotionsListEntity> page = new Page<>(pageParam.getPage(), pageParam.getLimit());// 当前页，总条数 构造 page 对象
		return page.setRecords(this.baseMapper.selectListForWeb(page,typeId));
	}


}
