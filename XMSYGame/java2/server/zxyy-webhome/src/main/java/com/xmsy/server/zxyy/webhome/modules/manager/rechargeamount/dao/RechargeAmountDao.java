package com.xmsy.server.zxyy.webhome.modules.manager.rechargeamount.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.xmsy.server.zxyy.webhome.modules.manager.rechargeamount.entity.RechargeAmountEntity;

/**
 * 充值内容（金额）
 * 
 * @author xiaoliu
 * @email xxxxx
 * @date 2019-03-27 10:16:53
 */
@Mapper
public interface RechargeAmountDao extends BaseMapper<RechargeAmountEntity> {

	List<RechargeAmountEntity> selectNewPage(Page<RechargeAmountEntity> page,
			@Param("entity")RechargeAmountEntity entity);
	
}
