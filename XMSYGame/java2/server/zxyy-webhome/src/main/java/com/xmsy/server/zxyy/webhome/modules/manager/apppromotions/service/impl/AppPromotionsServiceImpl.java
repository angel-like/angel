package com.xmsy.server.zxyy.webhome.modules.manager.apppromotions.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xmsy.common.define.page.PageParam;
import com.xmsy.server.zxyy.webhome.modules.manager.apppromotions.dao.AppPromotionsDao;
import com.xmsy.server.zxyy.webhome.modules.manager.apppromotions.entity.AppPromotionsEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.apppromotions.entity.AppPromotionsListEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.apppromotions.service.AppPromotionsService;


@Service("appPromotionsService")
public class AppPromotionsServiceImpl extends ServiceImpl<AppPromotionsDao, AppPromotionsEntity> implements AppPromotionsService {

	@Override
	public Page<AppPromotionsListEntity> getPromotionsList(Long typeId,PageParam pageParam) {
		Page<AppPromotionsListEntity> page = new Page<>(pageParam.getPage(), pageParam.getLimit());// 当前页，总条数 构造 page 对象
		return page.setRecords(this.baseMapper.selectListForWeb(page,typeId));
	}


}
