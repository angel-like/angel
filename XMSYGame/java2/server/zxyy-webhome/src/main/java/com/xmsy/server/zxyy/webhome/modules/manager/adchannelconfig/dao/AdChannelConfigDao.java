package com.xmsy.server.zxyy.webhome.modules.manager.adchannelconfig.dao;

import com.xmsy.server.zxyy.webhome.modules.manager.adchannelconfig.entity.AdChannelConfigEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

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
	 * 通过渠道码查询对应的渠道配置
	 * @param channelCode
	 * @return
	 */
	AdChannelConfigEntity findAdChannelByChannelCode(String channelCode);
}
