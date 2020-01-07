package com.xmsy.server.zxyy.manager.modules.manager.giftcoinconfig.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xmsy.server.zxyy.manager.modules.manager.giftcoinconfig.entity.GiftCoinConfigEntity;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

/**
 * 金币奖励配置
 * 
 * @author xiaoling
 * @email xxxxx
 * @date 2019-05-21 11:00:51
 */
@Mapper
public interface GiftCoinConfigDao extends BaseMapper<GiftCoinConfigEntity> {
	/**
	 * 查询分享app信息
	 */
	List<GiftCoinConfigEntity> queryShareApp();
}
