package com.xmsy.server.zxyy.manager.modules.manager.proxytransferrecord.service;

import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.service.IService;
import com.xmsy.server.zxyy.manager.modules.manager.proxytransferrecord.entity.ProxyTransferRecordEntity;


/**
 * 划拨记录
 *
 * @author axiong
 * @email xxxxx
 * @date 2019-08-07 15:51:01
 */
public interface ProxyTransferRecordService extends IService<ProxyTransferRecordEntity> {
	/**
	 * 	通过时间,代理商id  获取划拨总数量（stransferNum） ） 划拨总金币（transferCoin）
	 * 代理商 今日划拨记录
	 * @return
	 */
	Map<String, Object> sumTransferMoneyAmount(@Param("startDate") String startDate, @Param("endDate") String endDate,
			@Param("proxyId") Long proxyId);
}

