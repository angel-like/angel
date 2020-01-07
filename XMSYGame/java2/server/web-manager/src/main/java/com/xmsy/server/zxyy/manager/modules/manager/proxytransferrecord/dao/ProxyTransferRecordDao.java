package com.xmsy.server.zxyy.manager.modules.manager.proxytransferrecord.dao;

import com.xmsy.server.zxyy.manager.modules.manager.proxytransferrecord.entity.ProxyTransferRecordEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 划拨记录
 * 
 * @author axiong
 * @email xxxxx
 * @date 2019-08-07 15:51:01
 */
@Mapper
public interface ProxyTransferRecordDao extends BaseMapper<ProxyTransferRecordEntity> {
	/**
	 * 通过时间,代理商id  获取划拨总数量（stransferNum） ） 划拨总金币（transferCoin）
	 * 代理商 划拨记录
	 * @return
	 */
	Map<String, Object> sumTransferMoneyAmount(@Param("startDate") String startDate, @Param("endDate") String endDate,
			@Param("proxyId") Long proxyId);
}
