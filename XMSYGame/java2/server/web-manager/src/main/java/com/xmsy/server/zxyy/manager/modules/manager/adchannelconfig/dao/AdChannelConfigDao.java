package com.xmsy.server.zxyy.manager.modules.manager.adchannelconfig.dao;

import com.xmsy.server.zxyy.manager.modules.manager.adchannelconfig.entity.AdChannelConfigEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 渠道配置表
 * 
 * @author axiong
 * @email xxxxx
 * @date 2019-07-02 17:05:09
 */
@Mapper
public interface AdChannelConfigDao extends BaseMapper<AdChannelConfigEntity> {
	/**
	 * 通过id物理删除 渠道配置
	 * @return
	 */
	boolean delPhysicalAdChannelConfig(Long id);
	/**
	 * 通过渠道码查询对应的渠道配置
	 * @param channelCode
	 * @return
	 */
	AdChannelConfigEntity findAdChannelByChannelCode(String channelCode);
	/**
	 * 空渠道码需要自己写sql插入
	 * @param adChannelConfig
	 * @return
	 */
	boolean addNullChannelCode(@Param("adChannelConfig") AdChannelConfigEntity adChannelConfig);
}
