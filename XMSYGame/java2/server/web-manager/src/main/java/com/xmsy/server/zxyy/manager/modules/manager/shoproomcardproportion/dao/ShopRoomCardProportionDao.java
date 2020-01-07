package com.xmsy.server.zxyy.manager.modules.manager.shoproomcardproportion.dao;

import com.xmsy.server.zxyy.manager.modules.manager.shoproomcardproportion.entity.ShopRoomCardProportionEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 金币兑换房卡比例表
 * 
 * @author axiong
 * @email xxxxx
 * @date 2019-07-05 17:00:34
 */
@Mapper
public interface ShopRoomCardProportionDao extends BaseMapper<ShopRoomCardProportionEntity> {
	/**
	 * 通过时间范围   查询所有的金币兑换房卡数据
	 * 降序
	 * @param ShopRoomCardProportion
	 * @return
	 */
	public List<ShopRoomCardProportionEntity> findShopRoomCardProportion(Pagination page,@Param("shopRoomCardProportion") ShopRoomCardProportionEntity shopRoomCardProportion);
}
