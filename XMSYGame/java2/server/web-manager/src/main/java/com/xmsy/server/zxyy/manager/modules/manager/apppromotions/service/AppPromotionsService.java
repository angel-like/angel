package com.xmsy.server.zxyy.manager.modules.manager.apppromotions.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.xmsy.common.define.page.PageParam;
import com.xmsy.server.zxyy.manager.modules.manager.apppromotions.entity.AppPromotionsEntity;
import com.xmsy.server.zxyy.manager.modules.manager.apppromotions.entity.AppPromotionsListEntity;


/**
 * 官网优惠活动
 *
 * @author aleng
 * @email xxxxx
 * @date 2019-01-28 16:22:11
 */
public interface AppPromotionsService extends IService<AppPromotionsEntity> {

	Page<AppPromotionsListEntity> getPromotionsList(Long typeId,PageParam pageParam);

}

