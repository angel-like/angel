package com.xmsy.server.zxyy.manager.modules.manager.giftbagconfig.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.baomidou.mybatisplus.service.IService;
import com.xmsy.server.zxyy.manager.modules.manager.giftbagconfig.entity.GiftBagConfigEntity;


/**
 * 礼包配置表
 *
 * @author axiong
 * @email xxxxx
 * @date 2019-07-15 10:13:06
 */
public interface GiftBagConfigService extends IService<GiftBagConfigEntity> {
	List<GiftBagConfigEntity> findPageList(@Param("giftBagConfigEntity")GiftBagConfigEntity giftBagConfigEntity, Pagination page);



}

