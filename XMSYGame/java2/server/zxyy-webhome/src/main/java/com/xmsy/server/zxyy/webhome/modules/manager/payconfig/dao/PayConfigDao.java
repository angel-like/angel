package com.xmsy.server.zxyy.webhome.modules.manager.payconfig.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xmsy.server.zxyy.webhome.modules.app.recharge.result.AppPayChannelResult;
import com.xmsy.server.zxyy.webhome.modules.manager.payconfig.entity.PayConfigEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.payconfig.entity.PayConfigResultEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.payconfig.entity.PayConfigSelectPayid;

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
	
	/**
	 * 查询从对应层级下的支付公司id
	 * @return
	 */
	List<PayConfigSelectPayid> selectPayId(@Param("hierarchyId") Long hierarchyId);
	
	List<AppPayChannelResult> selectPayListByChangeId(@Param("channelId") int channelId,@Param("hierarchyId") Long hierarchyId);
	
	List<PayConfigResultEntity> selectListForWeb();
	
}
