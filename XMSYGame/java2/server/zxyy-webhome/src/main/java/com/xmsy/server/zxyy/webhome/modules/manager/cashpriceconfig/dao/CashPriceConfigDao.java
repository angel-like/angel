package com.xmsy.server.zxyy.webhome.modules.manager.cashpriceconfig.dao;

import com.xmsy.server.zxyy.webhome.modules.manager.cashpriceconfig.entity.CashPriceConfigEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 现金价格预设配置表（提供给用户充值、提现的预设金额）
 * 
 * @author adu
 * @email xxxxx
 * @date 2019-02-18 10:27:10
 */
@Mapper
public interface CashPriceConfigDao extends BaseMapper<CashPriceConfigEntity> {
	
}
