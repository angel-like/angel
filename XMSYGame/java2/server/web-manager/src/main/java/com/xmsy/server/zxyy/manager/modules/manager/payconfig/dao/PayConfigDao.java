package com.xmsy.server.zxyy.manager.modules.manager.payconfig.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xmsy.server.zxyy.manager.modules.manager.payconfig.entity.PayConfigEntity;
import com.xmsy.server.zxyy.manager.modules.manager.payconfig.entity.PayConfigResultEntity;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

/**
 * 支付渠道配置
 * 
 * @author xiaoliu
 * @email xxxxx
 * @date 2019-03-25 10:09:06
 */
@Mapper
public interface PayConfigDao extends BaseMapper<PayConfigEntity> {

	List<PayConfigResultEntity> selectListForApp();
	
	List<PayConfigResultEntity> selectListForWeb();
	
}
