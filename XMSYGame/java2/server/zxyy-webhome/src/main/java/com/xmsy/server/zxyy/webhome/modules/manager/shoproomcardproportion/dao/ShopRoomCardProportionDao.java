package com.xmsy.server.zxyy.webhome.modules.manager.shoproomcardproportion.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xmsy.server.zxyy.webhome.modules.manager.shoproomcardproportion.entity.ShopRoomCardProportionEntity;

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
	 * 查找出日期以及对应的 金币兑换房卡比例
	 * @return
	 */
	List<Map<String,Object>> findProportion();
	/**
	 * 查询出距离当前时间最近的一条比例
	 * @return
	 */
	BigDecimal findOneProportion(@Param("shopRoomCardProportion") ShopRoomCardProportionEntity shopRoomCardProportion);
}
