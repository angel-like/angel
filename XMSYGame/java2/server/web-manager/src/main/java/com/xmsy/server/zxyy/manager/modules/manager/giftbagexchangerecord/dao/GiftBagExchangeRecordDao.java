package com.xmsy.server.zxyy.manager.modules.manager.giftbagexchangerecord.dao;

import com.xmsy.server.zxyy.manager.modules.manager.giftbagexchangerecord.entity.GiftBagExchangeRecordEntity;
import com.xmsy.server.zxyy.manager.modules.manager.giftbagexchangerecord.entity.GiftBagExchangeRecordParam;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 礼包兑换记录表
 * 
 * @author axiong
 * @email xxxxx
 * @date 2019-07-18 15:14:51
 */
@Mapper
public interface GiftBagExchangeRecordDao extends BaseMapper<GiftBagExchangeRecordEntity> {
	
	//查询时间范围内的兑换总额
	List<GiftBagExchangeRecordParam> getTotalExchangeAmount(@Param("startTime") String startTime,@Param("endTime") String endTime);
	
}
