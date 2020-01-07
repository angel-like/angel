package com.xmsy.server.zxyy.manager.modules.manager.giftbagconfig.dao;

import com.xmsy.server.zxyy.manager.modules.manager.giftbagconfig.entity.GiftBagConfigEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 礼包配置表
 * 
 * @author axiong
 * @email xxxxx
 * @date 2019-07-15 10:13:06
 */
@Mapper
public interface GiftBagConfigDao extends BaseMapper<GiftBagConfigEntity> {
	/**
	 * 兑换码配置表 分页查询   id倒叙
	 * @param giftBagConfigEntity
	 * @param page
	 * @return
	 */
	List<GiftBagConfigEntity> findPageList(@Param("giftBagConfigEntity")GiftBagConfigEntity giftBagConfigEntity, Pagination page);
}
